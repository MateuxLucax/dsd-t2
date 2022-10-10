package com.udesc.t2_dsd.model;

public class Position implements Cloneable {
    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    
    public void update(Position position) {
        this.row = position.row;
        this.column = position.column;
    }

    @Override
    public boolean equals(Object obj) {
        Position other = (Position) obj;
        return this.row == other.row && this.column == other.column;
    }

    @Override
    public int hashCode() {
        String aux = String.valueOf(row) + String.valueOf(column);
        return Integer.parseInt(aux);
    }

    @Override
    public String toString() {
        return String.valueOf(row) + "-" + String.valueOf(column);
    }

    @Override
    public Object clone() {
        return new Position(row, column);
    }
}
