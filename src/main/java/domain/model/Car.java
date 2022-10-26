package domain.model;

import data.datasource.Database;
import domain.model.enums.DirChange;
import domain.model.enums.Direction;
import domain.model.enums.Status;
import domain.model.parallel.Lockable;
import domain.util.Constants;

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
    private final Random rng;

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
        this.rng = new Random();
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
            handleRemove(false);
        }
    }

    private void releaseData() {
        acquiredCrossingSemaphores.forEach(Lockable::release);
        acquiredCrossingSemaphores.clear();
        release(position);
    }

    public void tryMove() throws InterruptedException {
        Random random = new Random();
        Position nextPosition;

        var cell = db.getWorld().get(position);

        if (cell.isRoad()) {
            nextPosition = cell.roadDirection().moved(position);

            if (!validMove(nextPosition)) {
                handleRemove(true);
                return;
            }

            var nextCell = db.getWorld().get(nextPosition);

            if (nextCell.isRoad()) {
                // wait for next position to become available
                acquire(nextPosition);
            } else {
                assert nextCell.isCrossing();


                Direction currDir = cell.roadDirection();
                Position currPos = nextPosition;

                DirChange[] path = getValidCrossingPath(currPos, currDir);

                Direction originalCurrDir = Direction.valueOf(currDir.name());
                Position originalCurrPos = (Position) currPos.clone();

                boolean acquiredNeededCrossing = false;
                do {      
                    boolean tryAcquire = true;
                    for (var dirChange : path) {
                        var semaphore = db.getWorld().getSemaphore(currPos);

                        tryAcquire = semaphore.tryAcquire(50);
                        if (!tryAcquire) {
                           break; 
                        }

                        acquiredCrossingSemaphores.add(semaphore);
                        currDir = dirChange.changed(currDir);
                        currPos = currDir.moved(currPos);
                        remainingCrossingPositions.add(currPos);
                    }
                    
                    if (tryAcquire) {
                        acquiredNeededCrossing = true;
                        continue;
                    } else { // release and reset state if some was not acquired
                        acquiredCrossingSemaphores.forEach(Lockable::release);
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
            
            // acquire position from first road after crossing
            if (remainingCrossingPositions.isEmpty()) {
                acquire(nextPosition);
            }
        }

        // invariant: at this point nextPosition is available and the car can just move onto it
        // either because the next cell is a road, and we waited for it to become available
        // or because it's a crossing, and we acquired its semaphores
        var lastPosition = (Position) position.clone();
        handleMove(nextPosition);
        release(lastPosition);
    }

    private DirChange[] getValidCrossingPath(Position currPos, Direction currDir) {
        Direction missingExit = db.getWorld().missingExit(currPos);
        DirChange[] availableChanges;
        if (missingExit != null) {
            var changeForMissingExit = DirChange.fromDirectionChange(currDir, missingExit);
            availableChanges = DirChange.except(changeForMissingExit);
        } else {
            availableChanges = DirChange.values();
        }

        DirChange chosenCrossingExit = availableChanges[rng.nextInt(0, availableChanges.length)];
        DirChange[] path = crossingPaths.get(chosenCrossingExit);

        return path;
    }

    private void release(Position pos) {
        db.getWorld().getSemaphore(pos).release();
    }
    
    private void acquire(Position pos) {
        db.getWorld().getSemaphore(pos).acquire();
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
    
    private void handleRemove(boolean shouldStop) {
        acquiredCrossingSemaphores.forEach(Lockable::release);
        acquiredCrossingSemaphores.clear();
        
        db.removeCar(position);
        release(position);
        if (shouldStop)
            stop();
    }
    
    private void shouldStop() {
        Status status = db.getStatus();
        if (status != Status.RUNNING) {
            handleRemove(true);
        }
    }
}