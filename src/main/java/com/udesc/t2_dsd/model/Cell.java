package com.udesc.t2_dsd.model;

import java.awt.Color;

public class Cell {
    private int id;
    private String str;
    private int row;
    private int column;
    private ICell ecell;
    private Position position;

    public Cell(int id, String str, int row, int column) {
        this.id = id;
        this.str = str;
        this.row = row;
        this.column = column;
        this.ecell = ICell.from(this.id);
        this.position = new Position(row, column);
    }
    
    public static Cell from(int id, int row, int column) {
        ICell ecell = ICell.from(id);
        String str = ecell.toString();
        
        return new Cell(id, str, row, column);
    }
    
    public int getId() {
        return id;
    }

    public String getStr() {
        return str;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public ICell getEcell() {
        return ecell;
    }

    public Position getPosition() {
        return position;
    }
    
    public String toString() {
        return this.str;
    }
    
    public Color getColor() {
        Color color = null;
        switch (this.ecell) {
            case NOTHING:
                color = Color.WHITE;
                break;
            case ROAD_NORTH:
                color = Color.decode("#FCD5B4");
                break;
            case ROAD_EAST:
                color = Color.decode("#B8CCE4");
                break;
            case ROAD_SOUTH:
                color = Color.decode("#E6B8B7");
                break;
            case ROAD_WEST:
                color = Color.decode("#D8E4BC");
                break;
            default:
                color = Color.decode("#A7A6A8");
                break;
        }
        return color;
    }
}
