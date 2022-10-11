package com.udesc.t2_dsd.model;

import com.udesc.t2_dsd.util.Constants;
import java.util.HashMap;

public class World {
    private final Cell[][] cells;
    private final int rows;
    private final int cols;
    private HashMap<Position, Cell> entryPoints;

    private World(int rows, int cols) {
        this.cells = new Cell[rows][cols];
        this.rows = rows;
        this.cols = cols;
        this.entryPoints = new HashMap<>();
        Constants.ROWS = rows;
        Constants.COLUMNS = cols;
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    public Cell get(int i, int j) {
        return cells[i][j];
    }

    public Cell get(Position pos) {
        return cells[pos.getRow()][pos.getColumn()];
    }

    public HashMap<Position, Cell> getEntryPoints() {
        return entryPoints;
    }

    public static World from(String str) {
        var lines = str.split("\n");
        for (var l = 0; l < lines.length; l++) {
            lines[l] = lines[l].trim();
        }

        var rows = Integer.parseInt(lines[0]);
        var cols = Integer.parseInt(lines[1]);

        var world = new World(rows, cols);

        for (var l = 2; l < lines.length; l++) {
            var cells = lines[l].split("\t");
            var i = l - 2;
            for (var j = 0; j < cols; j++) {
                var id = Integer.parseInt(cells[j]);
                var cell = Cell.from(id, i, j);
                
                if (cell == null) {
                    throw new RuntimeException("Invalid cell id" + id);
                }
                world.cells[i][j] = cell;
            }
        }
        return world;
    }
    
    public void verifyEntryPoints() {
        entryPoints.clear();
        // TOP
        for (int i = 0; i < this.cols; i++) {
            Cell cell = this.cells[0][i];
            if (cell.getEcell() == ICell.ROAD_SOUTH) {
                entryPoints.put(cell.getPosition(), cell);
            }
        }
        
        // LEFT
        for (int i = 0; i < this.rows; i++) {
            Cell cell = this.cells[i][0];
            if (cell.getEcell() == ICell.ROAD_EAST) {
                entryPoints.put(cell.getPosition(), cell);
            }
        }
        
        // BOTTOM
        for (int i = 0; i < this.cols; i++) {
            Cell cell = this.cells[this.rows-1][i];
            if (cell.getEcell() == ICell.ROAD_NORTH) {
                entryPoints.put(cell.getPosition(), cell);
            }
        }
        
        // RIGHT
        for (int i = 0; i < this.rows; i++) {
            Cell cell = this.cells[i][this.cols-1];
            if (cell.getEcell() == ICell.ROAD_WEST) {
                entryPoints.put(cell.getPosition(), cell);
            }
        }
    }

    public String toString() {
        var builder = new StringBuilder();
        for (var i = 0; i < rows; i++) {
            for (var j = 0; j < cols; j++) {
                builder.append(cells[i][j].toString());
                if (j < cols - 1) {
                    builder.append('\t');
                }
            }
            if (i < rows - 1) {
                builder.append('\n');
            }
        }
        return builder.toString();
    }
}
