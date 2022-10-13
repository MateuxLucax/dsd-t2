package domain.model;

import domain.util.Constants;
import data.datasource.Database;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.EnumMap;
import java.util.Queue;

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

    private final long sleep;
    private final Color color;
    private final Position position;
    private final Database db;

    // fila das posições que o carro vai tomar pelo cruzamento
    private final Queue<Position> remainingCrossingPositions;
    
    public Car(Position position, long sleep, Color color) {
        this.sleep = sleep;
        this.color = color;
        this.position = position;
        this.db = Database.getInstance();
        remainingCrossingPositions = new ArrayDeque<>();
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(sleep);
                shouldStop();
                tryMove();
            } catch (InterruptedException ex) {
                System.out.println("Interrupted (ok)");
            }
        }
    }

    @SuppressWarnings("empty-statement")
    public void tryMove() {
        Position nextPosition;

        var cell = db.getWorld().get(position);

        if (cell.isRoad()) {
            nextPosition = cell.roadDirection().moved(position);

            if (!validMove(nextPosition)) {
                handleRemove();
                return;
            }

            var nextCell = db.getWorld().get(nextPosition);

            if (nextCell.isRoad()) {
                // wait for next position to become available
            } else {
                assert nextCell.isCrossing();

                // TODO mutual exclusion in acquiring those positions

                DirChange chosenCrossingExit = DirChange.random();
                DirChange[] path = crossingPaths.get(chosenCrossingExit);

                Direction currDir = cell.roadDirection();
                Position currPos = nextPosition;

                for (var dirChange : path) {
                    currDir = dirChange.changed(currDir);
                    currPos = currDir.moved(currPos);
                    remainingCrossingPositions.add(currPos);
                }
            }
        } else {
            assert cell.isCrossing();
            assert !remainingCrossingPositions.isEmpty();

            nextPosition = remainingCrossingPositions.remove();
        }

        // invariant: confirm that there is no car on nextPosition
        while (db.getCar(nextPosition) != null);
        
        // invariant: at this point nextPosition is available and the car can just move onto it
        handleMove(nextPosition);
    }


    private void handleMove(Position nextMove) {
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
        Car remove = db.removeCar(position);
        stop();
    }
    
    private void shouldStop() {
        Status status = db.getStatus();
        if (status != Status.RUNNING) {
            stop();
        }
    }
}
