package domain.model;

import java.util.Random;

public enum DirChange {
    FORWARDS,
    RIGHT_TURN,
    LEFT_TURN;

    private static final Random rng = new Random();

    public static DirChange random() {
        var vals = values();
        return vals[rng.nextInt(vals.length)];
    }

    public Direction changed(Direction dir) {
        return switch (this) {
            case FORWARDS -> dir.forwards();
            case RIGHT_TURN -> dir.turnRight();
            case LEFT_TURN -> dir.turnLeft();
        };
    }
}
