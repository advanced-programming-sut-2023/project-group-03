package Model.Field;

public enum Direction {
    UP("up"),
    DOWN("down"),
    RIGHT("right"),
    LEFT("left"),
    ;

    private final String name;

    Direction(String name) {
        this.name = name;
    }

    public static Direction getDirectionByName(String name) {
        for (Direction direction : Direction.values()) {
            if (direction.name.equals(name)) return direction;
        }
        return null;
    }
}
