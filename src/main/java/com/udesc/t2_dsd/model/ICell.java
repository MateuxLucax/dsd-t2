package com.udesc.t2_dsd.model;

public enum ICell {
    NOTHING(0, ""),
    ROAD_UP(1, "⭡"),
    ROAD_RIGHT(2, "⭢"),
    ROAD_DOWN(3, "⭣"),
    ROAD_LEFT(4, "⭠"),
    CROSSING_UP(5, "⇈"),
    CROSSING_RIGHT(6, "⇉"),
    CROSSING_DOWN(7, "⇊"),
    CROSSING_LEFT(8, "⇇"),
    CROSSING_UP_RIGHT(9, "⮣"),
    CROSSING_LEFT_UP(10, "⮤"),
    CROSSING_RIGHT_DOWN(11, "⮧"),
    CROSSING_DOWN_LEFT(12, "⮠");

    private final int id;
    private final String str;

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
