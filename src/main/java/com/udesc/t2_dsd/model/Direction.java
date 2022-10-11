package com.udesc.t2_dsd.model;

public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public void move(Position pos) {
        switch (this) {
            case NORTH -> pos.setRow(pos.getRow() - 1);
            case SOUTH -> pos.setRow(pos.getRow() + 1);
            case WEST -> pos.setColumn(pos.getColumn() - 1);
            case EAST -> pos.setColumn(pos.getColumn() + 1);
        }
    }

    public Position moved(Position pos) {
        var moved = (Position) pos.clone();
        move(moved);
        return moved;
    }

    public Direction forwards() {
        return this;
    }

    public Direction backwards() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
        };
    }

    public Direction turnRight() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    public Direction turnLeft() {
        return switch (this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
        };
    }
}
