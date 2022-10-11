package com.udesc.t2_dsd.model;

public enum ICell {
    NOTHING(0, ""),
    ROAD_NORTH(1, "⭡"),
    ROAD_EAST(2, "⭢"),
    ROAD_SOUTH(3, "⭣"),
    ROAD_WEST(4, "⭠"),
    CROSSING_NORTH(5, "⇈"),
    CROSSING_EAST(6, "⇉"),
    CROSSING_SOUTH(7, "⇊"),
    CROSSING_WEST(8, "⇇"),
    CROSSING_NORTH_EAST(9, "⮣"),
    CROSSING_NORTH_WEST(10, "⮤"),
    CROSSING_SOUTH_EAST(11, "⮧"),
    CROSSING_SOUTH_WEST(12, "⮠");

    private final int id;
    private final String str;

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

    ICell(int id, String str) {
        this.id = id;
        this.str = str;
    }
    
    public static ICell from(int id) {
        for (var c : ICell.values()) {
            if (c.id == id) {
                return c;
            }
        }
        return null;
    }

    public String toString() {
        return this.str;
    }
}
