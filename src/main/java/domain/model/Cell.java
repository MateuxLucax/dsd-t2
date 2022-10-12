package domain.model;

import java.awt.*;

public enum Cell {
    NOTHING(0),
    ROAD_NORTH(1),
    ROAD_EAST(2),
    ROAD_SOUTH(3),
    ROAD_WEST(4),
    CROSSING_NORTH(5),
    CROSSING_EAST(6),
    CROSSING_SOUTH(7),
    CROSSING_WEST(8),
    CROSSING_NORTH_EAST(9),
    CROSSING_NORTH_WEST(10),
    CROSSING_SOUTH_EAST(11),
    CROSSING_SOUTH_WEST(12);

    private final int id;

    public boolean isRoad() {
        return this == ROAD_NORTH || this == ROAD_SOUTH || this == ROAD_WEST || this == ROAD_EAST;
    }

    public boolean isNothing() {
        return this == NOTHING;
    }

    public boolean isCrossing() {
        return !isNothing() && !isRoad();
    }

    public Direction roadDirection() {
        switch(this) {
            case ROAD_NORTH -> { return Direction.NORTH; }
            case ROAD_SOUTH -> { return Direction.SOUTH; }
            case ROAD_EAST -> { return Direction.EAST; }
            case ROAD_WEST -> { return Direction.WEST; }
            default -> { throw new RuntimeException("roadDirection() applicable only for roads"); }
        }
    }

    Cell(int id) {
        this.id = id;
    }
    
    public static Cell from(int id) {
        for (var c : Cell.values()) {
            if (c.id == id) {
                return c;
            }
        }
        return null;
    }

    public String toString() {
        return switch (this) {
            case ROAD_NORTH -> "^";
            case ROAD_EAST -> ">";
            case ROAD_SOUTH -> "v";
            case ROAD_WEST -> "<";
            default -> isCrossing() ? "X" : " ";
        };
    }

    public Color toColor() {
        return switch (this) {
            case ROAD_NORTH -> Color.decode("#FCD5B4");
            case ROAD_EAST -> Color.decode("#B8CCE4");
            case ROAD_SOUTH -> Color.decode("#E6B8B7");
            case ROAD_WEST -> Color.decode("#D8E4BC");
            default -> isCrossing() ? Color.decode("#A7A6A8") : Color.white;
        };
    }


}
