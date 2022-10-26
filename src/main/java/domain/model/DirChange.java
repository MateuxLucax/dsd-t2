package domain.model;

import java.util.Random;

public enum DirChange {
    FORWARDS,
    RIGHT_TURN,
    LEFT_TURN;

    private static final Random rng = new Random();

    public static DirChange random() {
        return values()[rng.nextInt(0, 3)];
    }

    public static DirChange[] except(DirChange dir){
        return switch (dir) {
            case FORWARDS -> new DirChange[]{RIGHT_TURN, LEFT_TURN};
            case RIGHT_TURN -> new DirChange[]{FORWARDS, LEFT_TURN};
            case LEFT_TURN -> new DirChange[]{FORWARDS, RIGHT_TURN};
        };
    }

    public static DirChange fromDirectionChange(Direction start, Direction end) {
        return switch (start) {
            case NORTH -> switch(end) {
                case NORTH -> FORWARDS;
                case SOUTH -> throw new RuntimeException("Cannot go backwards");
                case WEST -> LEFT_TURN;
                case EAST -> RIGHT_TURN;
            };
            case SOUTH -> switch(end) {
                case NORTH -> throw new RuntimeException("Cannot go backwards");
                case SOUTH -> FORWARDS;
                case WEST -> RIGHT_TURN; // TODO check
                case EAST -> LEFT_TURN;
            };
            case WEST -> switch(end) {
                case NORTH -> RIGHT_TURN;
                case SOUTH -> LEFT_TURN;
                case WEST -> FORWARDS;
                case EAST -> throw new RuntimeException("Cannot go backwards");
            };
            case EAST -> switch(end) {
                case NORTH -> LEFT_TURN;
                case SOUTH -> RIGHT_TURN;
                case WEST -> throw new RuntimeException("Cannot go backwards");
                case EAST -> FORWARDS;
            };
        };
    }

    public Direction changed(Direction dir) {
        return switch (this) {
            case FORWARDS -> dir.forwards();
            case RIGHT_TURN -> dir.turnRight();
            case LEFT_TURN -> dir.turnLeft();
        };
    }
}
