package domain.model.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum DirChange {
    FORWARDS,
    RIGHT_TURN,
    LEFT_TURN;

    private static final Random generator = new Random();

    public static DirChange random(List<DirChange> except) {
        List<DirChange> paths = new ArrayList<>(List.of(values()));
        paths.removeIf(except::contains);
        return paths.get(generator.nextInt(paths.size()));
    }

    public Direction changed(Direction dir) {
        return switch (this) {
            case FORWARDS -> dir.forwards();
            case RIGHT_TURN -> dir.turnRight();
            case LEFT_TURN -> dir.turnLeft();
        };
    }
}
