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
                ex.printStackTrace();
            }
        }
    }

    public void tryMove() {
        var cell = db.getWorld().get(position).getEcell();

        if (cell.isRoad()) {
            var positionAhead = cell.roadDirection().moved(position);
            if (!validMove(positionAhead)) {
                handleRemove();
                return;
            }

            handleMove(positionAhead);

            var cellAhead = db.getWorld().get(positionAhead).getEcell();
            if (cellAhead.isCrossing()) {
                var chosenCrossingExit = DirChange.random();
                var path = crossingPaths.get(chosenCrossingExit);

                var currDir = cell.roadDirection();
                var currPos = positionAhead;

                for (var dirChange : path) {
                    currDir = dirChange.changed(currDir);
                    currPos = currDir.moved(currPos);
                    remainingCrossingPositions.add(currPos);
                }
            }
        }
        else if (cell.isCrossing()) {
            var positionAhead = remainingCrossingPositions.remove();
            handleMove(positionAhead);
        }
    }
    
    private void handleMove(Position nextMove) {
        db.getCars().put(nextMove, this);
        db.getCars().remove(position);
        position.update(nextMove);
    }
    
    private boolean canMove(Position movePosition) {
        Car car = db.getCars().get(movePosition);
        return car == null;
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
