package domain.model;

import domain.util.Constants;
import data.datasource.Database;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.EnumMap;
import java.util.Queue;
import java.util.Random;

public class Car extends Thread {

    private static final EnumMap<DirChange, DirChange[]> crossingPaths;

    static {
        crossingPaths = new EnumMap<>(DirChange.class);

        crossingPaths.put(DirChange.RIGHT_TURN, new DirChange[]{
            DirChange.RIGHT_TURN
        });

        crossingPaths.put(DirChange.FORWARDS, new DirChange[]{
            DirChange.FORWARDS,
            DirChange.FORWARDS
        });

        crossingPaths.put(DirChange.LEFT_TURN, new DirChange[]{
            DirChange.FORWARDS,
            DirChange.LEFT_TURN,
            DirChange.FORWARDS
        });
    }

    private final int id;
    private final int sleep;
    private final Color color;
    private final Position position;
    private final Database db;

    // fila das posições que o carro vai tomar pelo cruzamento
    private final Queue<Position> remainingCrossingPositions;

    // fila paralela à de cima, com os semáforos correspondentes a cada posição
    private final Queue<Lockable> acquiredCrossingSemaphores;

    public Car(Position position, int id, int sleep, Color color) {
        this.id = id;
        this.sleep = sleep;
        this.color = color;
        this.position = position;
        this.db = Database.getInstance();
        remainingCrossingPositions = new ArrayDeque<>();
        acquiredCrossingSemaphores = new ArrayDeque<>();
    }

    public Color getColor() {
        return this.color;
    }

    public int id() { return this.id; }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                Thread.sleep(sleep);
                shouldStop();
                tryMove();
            }
        } catch (InterruptedException ex) {
            acquiredCrossingSemaphores.forEach(item -> item.release());
            acquiredCrossingSemaphores.clear();
            
            release(position);
        }
    }

    @SuppressWarnings("empty-statement")
    public void tryMove() throws InterruptedException {
        Random random = new Random();
        Position nextPosition;

        var cell = db.getWorld().get(position);

        if (cell.isRoad()) {
            nextPosition = cell.roadDirection().moved(position);

            if (!validMove(nextPosition)) {
                handleRemove();
                return;
            }

            var nextCell = db.getWorld().get(nextPosition);

            // TODO nas roads as vezes dois carros se sobrepõem, então talvez toda cell teria
            //   que ter um semaphore mesmo (adquirido ao entrar, soltado logo após sair da posição)
            //   (mas p/ crossing funciona que nem agora)

            if (nextCell.isRoad()) {
                // wait for next position to become available
                db.getWorld().getSemaphore(nextPosition).acquire();
            } else {
                assert nextCell.isCrossing();

                DirChange chosenCrossingExit = DirChange.random();
                DirChange[] path = crossingPaths.get(chosenCrossingExit);

                Direction currDir = cell.roadDirection();
                Position currPos = nextPosition;
                
                Direction originalCurrDir = Direction.valueOf(currDir.name());
                Position originalCurrPos = (Position) currPos.clone();
                
                boolean acquiredNeededCrossing = false;
                do {      
                    boolean tryAcquire = true;
                    for (var dirChange : path) {
                        var semaphore = db.getWorld().getSemaphore(currPos);

                        // 500, TimeUnit.MILLISECONDS
                        tryAcquire = semaphore.tryAcquire(50);
                        if (!tryAcquire) {
                           break; 
                        }
                        acquiredCrossingSemaphores.add(semaphore);
                        // importante vir antes porque o último currPos é fora do cruzamento

                        currDir = dirChange.changed(currDir);
                        currPos = currDir.moved(currPos);

                        remainingCrossingPositions.add(currPos);
                    }
                    
                    if (tryAcquire) {
                        acquiredNeededCrossing = true;
                        continue;
                    } else {
                        acquiredCrossingSemaphores.forEach(item -> item.release());
                        acquiredCrossingSemaphores.clear();
                        remainingCrossingPositions.clear();
                        currDir = originalCurrDir;
                        currPos = originalCurrPos;
                    }
                    sleep(random.nextInt(500));
                } while (!acquiredNeededCrossing);
            }
        } else {
            assert cell.isCrossing();
            assert !remainingCrossingPositions.isEmpty();

            nextPosition = remainingCrossingPositions.remove();
            acquiredCrossingSemaphores.remove();
        }

        // invariant: at this point nextPosition is available and the car can just move onto it
        // either because the next cell is a road and we waited for it to become available
        // or because it's a crossing and we acquired its semaphores
        var lastPosition = (Position) position.clone();
        handleMove(nextPosition);
        release(lastPosition);
    }

    private void release(Position pos) {
        db.getWorld().getSemaphore(pos).release();
    }

    private void handleMove(Position nextMove) {
        if (isInterrupted()) return;
        db.setCar(nextMove, this);
        db.removeCar(position);
        position.update(nextMove);
    }
    
    private boolean validMove(Position movePosition) {
        if (!(movePosition.getRow() >= 0 && movePosition.getRow() <= Constants.ROWS-1)) {
            return false;
        }
        
        if (!(movePosition.getColumn() >= 0 && movePosition.getColumn() <= Constants.COLUMNS-1)) {
            return false;
        }
            
        return true;
    }
    
    private void handleRemove() {
        db.removeCar(position);
        release(position);
        interrupt();
    }
    
    private void shouldStop() {
        Status status = db.getStatus();
        if (status != Status.RUNNING) {
            interrupt();
        }
    }
}