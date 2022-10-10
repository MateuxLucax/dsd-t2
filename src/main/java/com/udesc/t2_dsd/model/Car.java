package com.udesc.t2_dsd.model;

import com.udesc.t2_dsd.util.Constants;
import com.udesc.t2_dsd.infra.Database;

public class Car extends Thread {
    private long sleep;
    private Position position;
    private Database db;
    
    public Car(Position position, long sleep) {
        this.sleep = sleep;
        this.position = position;
        this.db = Database.getInstance();
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
        Position nextMove = getMove(position);
        
        if (validMove(nextMove)) {
            if (canMove(nextMove)) {
                nextMove = verifyCrossing(nextMove);
                handleMove(nextMove);
            }
        } else {
            handleRemove();
        }
    }

    private void handleMove(Position nextMove) {
        db.getCars().put(nextMove, this);
        db.getCars().remove(position);
        position.update(nextMove);
    }
    
    private boolean canMove(Position movePosition) {
        Car car = db.getCars().get(movePosition);
        if (car != null) {
            return false;
        }
        return true;
    }
    
    private Position verifyCrossing(Position movePosition) {
        Cell cell = db.getWorld().get(movePosition.getRow(), movePosition.getColumn());
        
        if (!Constants.normalCells.contains(cell.getEcell())) {
            movePosition = (Position) position.clone();
            int row = movePosition.getRow();
            int column = movePosition.getColumn();
            
            switch (cell.getEcell()) {
                case CROSSING_DOWN:
                    movePosition.setRow(row + 2);
                    break;
                case CROSSING_DOWN_LEFT:
                    movePosition.setRow(row + 1);
                    movePosition.setColumn(column -1);
                    break;
                case CROSSING_LEFT:
                    movePosition.setColumn(column -2);
                    break;
                case CROSSING_LEFT_UP:
                    movePosition.setColumn(column -1);
                    movePosition.setRow(row-1);
                    break;
                case CROSSING_RIGHT:
                    movePosition.setColumn(column +2);
                    break;
                case CROSSING_RIGHT_DOWN:
                    movePosition.setColumn(column +1);
                    movePosition.setRow(row+1);
                    break;
                case CROSSING_UP:
                    movePosition.setRow(row-2);
                    break;
                case CROSSING_UP_RIGHT:
                    movePosition.setRow(row-1);
                    movePosition.setColumn(column+1);
                    break;
                default:
                    throw new AssertionError();
            }
        }
        return movePosition;
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
    
    private Position getMove(Position currentPosition) {
        Cell cell = db.getWorld().get(currentPosition.getRow(), currentPosition.getColumn());
        Position position = (Position) currentPosition.clone();
        int row = currentPosition.getRow();
        int column = currentPosition.getColumn();
        
        switch (cell.getEcell()) {
            case ROAD_UP:
                position.setRow(row-1);
                break;
            case ROAD_DOWN:
                position.setRow(row+1);
                break;
            case ROAD_LEFT:
                position.setColumn(column-1);
                break;
            case ROAD_RIGHT:
                position.setColumn(column+1);
                break;
            default:
                position.setColumn(column+1);
                break;
        }
        
        return position;
    }
}
