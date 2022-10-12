package com.udesc.t2_dsd.model;

import com.udesc.t2_dsd.util.Constants;
import com.udesc.t2_dsd.infra.Database;

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

    private long sleep;
    private Position position;
    private Database db;

    // fila das posições que o carro vai tomar a partir de quando estiver dentro do cruzamento
    private Queue<Position> remainingCrossingPositions;
    
    public Car(Position position, long sleep) {
        this.sleep = sleep;
        this.position = position;
        this.db = Database.getInstance();
        remainingCrossingPositions = new ArrayDeque<>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(sleep);
                tryMove();
            } catch (InterruptedException ex) {
                System.out.println("Interrupted (ok)");
            }
        }
    }

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
                while (db.getCars().get(nextPosition) != null);
            } else {
                assert nextCell.isCrossing();

                // TODO mutual exclusion in acquiring those positions

                var chosenCrossingExit = DirChange.random();
                var path = crossingPaths.get(chosenCrossingExit);

                var currDir = cell.roadDirection();
                var currPos = nextPosition;

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

        // invariant: at this point nextPosition is available and the car can just move onto it
        handleMove(nextPosition);
    }


    private void handleMove(Position nextMove) {
        db.getCars().put(nextMove, this);
        db.getCars().remove(position);
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
        Car remove = db.getCars().remove(position);
        System.out.println(remove);
        stop();
    }
}
