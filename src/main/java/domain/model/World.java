package domain.model;

import data.datasource.Database;
import domain.model.enums.Cell;
import domain.model.enums.Direction;
import domain.model.parallel.Lockable;

import java.util.ArrayList;
import java.util.List;

import static domain.util.Constants.COLUMNS;
import static domain.util.Constants.ROWS;

public class World {
    private final Cell[][] cells;
    private final int rows;
    private final int cols;
    private final List<Position> entryPoints;

    // um semáforo por posição do cruzamento
    private final Lockable[][] semaphores;

    // saídas disponíveis pra cada cruzamento (pode ser um T com uma saída a menos)
    private final Direction[][] missingExit;

    private World(int rows, int cols) {
        this.cells = new Cell[rows][cols];
        this.rows = rows;
        this.cols = cols;

        entryPoints = new ArrayList<>();
        semaphores = new Lockable[rows][cols];
        missingExit = new Direction[rows][cols];

        ROWS = rows;
        COLUMNS = cols;
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

    public List<Position> getEntryPoints() {
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
                var cell = Cell.from(id);

                if (cell == null) {
                    throw new RuntimeException("Invalid cell id" + id);
                }
                world.cells[i][j] = cell;

                if (!cell.isNothing()) {
                    try {
                        world.semaphores[i][j] = Database.getInstance().getLockable().getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Error while calling new instances of Lockable.");
                    }
                }
            }
        }

        world.generateEntryPoints();

        // find the missing exits for T-shaped crossings
        for (var i = 0; i < ROWS; i++) {
            for (var j = 0; j < COLUMNS; j++) {
                var isCrossingTopLeftCell
                    =  world.cells[i][j].isCrossing()
                    && world.cells[i][j+1].isCrossing()
                    && world.cells[i+1][j].isCrossing()
                    && world.cells[i+1][j+1].isCrossing();

                if (isCrossingTopLeftCell) {
                    Direction missing = null;

                    // there should be only one, otherwise the world is invalid
                    if (world.cells[i-1][j].isNothing()) missing = Direction.NORTH;
                    if (world.cells[i+2][j].isNothing()) missing = Direction.SOUTH;
                    if (world.cells[i][j-1].isNothing()) missing = Direction.WEST;
                    if (world.cells[i][j+2].isNothing()) missing = Direction.EAST;

                    world.missingExit[i][j]     = missing;
                    world.missingExit[i][j+1]   = missing;
                    world.missingExit[i+1][j]   = missing;
                    world.missingExit[i+1][j+1] = missing;
                }
            }
        }

        return world;
    }

    public Lockable getSemaphore(int row, int col) {
        return semaphores[row][col];
    }

    public Lockable getSemaphore(Position pos) {
        return getSemaphore(pos.getRow(), pos.getColumn());
    }


    public Direction missingExit(int row, int col) {
        return missingExit[row][col];
    }

    public Direction missingExit(Position p) {
        return missingExit(p.getRow(), p.getColumn());
    }

    private void generateEntryPoints() {
        entryPoints.clear();

        // TOP
        for (int i = 0; i < this.cols; i++) {
            Cell cell = this.cells[0][i];
            if (cell == Cell.ROAD_SOUTH) {
                entryPoints.add(new Position(0, i));
            }
        }
        
        // LEFT
        for (int i = 0; i < this.rows; i++) {
            Cell cell = this.cells[i][0];
            if (cell == Cell.ROAD_EAST) {
                entryPoints.add(new Position(i, 0));
            }
        }
        
        // BOTTOM
        for (int i = 0; i < this.cols; i++) {
            Cell cell = this.cells[this.rows-1][i];
            if (cell == Cell.ROAD_NORTH) {
                entryPoints.add(new Position(rows-1, i));
            }
        }
        
        // RIGHT
        for (int i = 0; i < this.rows; i++) {
            Cell cell = this.cells[i][this.cols-1];
            if (cell == Cell.ROAD_WEST) {
                entryPoints.add(new Position(i, cols-1));
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
