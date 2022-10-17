package domain.model;

import domain.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class World {
    private final Cell[][] cells;
    private final int rows;
    private final int cols;
    private final List<Position> entryPoints;

    // um semáforo por posição do cruzamento
    private final Semaphore[][] semaphores;

    // um monitor para cada cruzamento inteiro (todas as 4 posições)
    // evitamos deadlocks garantindo que só um carro adquire os semáforos do cruzamento inteiro por vez
    private final Object[][] crossingMonitor;

    private World(int rows, int cols) {
        this.cells = new Cell[rows][cols];
        this.rows = rows;
        this.cols = cols;
        entryPoints = new ArrayList<>();
        semaphores = new Semaphore[rows][cols];
        crossingMonitor = new Object[rows][cols];
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
                    world.semaphores[i][j] = new Semaphore(1);
                }
            }
        }

        for (var i = 0; i < rows; i++) {
            for (var j = 0; j < rows; j++) {
                var cell = world.cells[i][j];
                if (!cell.isCrossing()) continue;
                if (world.crossingMonitor[i][j] != null) continue;

                var isCrossingTopLeftCell
                    =  world.cells[i+1][j].isCrossing()
                    && world.cells[i][j+1].isCrossing()
                    && world.cells[i+1][j+1].isCrossing();

                if (isCrossingTopLeftCell) {
                    var monitor = new Object();
                    world.crossingMonitor[i][j]
                        = world.crossingMonitor[i+1][j]
                        = world.crossingMonitor[i][j+1]
                        = world.crossingMonitor[i+1][j+1]
                        = monitor;
                }
            }
        }

        world.generateEntryPoints();

        return world;
    }

    public Semaphore getSemaphore(int row, int col) {
        //var cell = get(row, col);
        //if (!cell.isCrossing()) throw new RuntimeException("getSemaphore() on non-crossing cell");
        return semaphores[row][col];
    }

    public Semaphore getSemaphore(Position pos) {
        return getSemaphore(pos.getRow(), pos.getColumn());
    }

    public Object crossingMonitor(int anyCrossingRow, int anyCrossingCol) {
        var cell = get(anyCrossingRow, anyCrossingCol);
        if (!cell.isCrossing()) 
            throw new RuntimeException("crossingLock() on non-crossing cell");
        return crossingMonitor[anyCrossingRow][anyCrossingCol];
    }

    public Object crossingMonitor(Position anyCrossingPos) {
        return crossingMonitor(anyCrossingPos.getRow(), anyCrossingPos.getColumn());
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
