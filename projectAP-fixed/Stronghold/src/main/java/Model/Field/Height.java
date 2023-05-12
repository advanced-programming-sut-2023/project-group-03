package Model.Field;

public enum Height {
    WATER(-3),
    SMALL_WALL(2),
    BIG_WALL(4),
    GROUND(0),
    BIG_TOWER(3),
    SMALL_TOWER(3),
    STONE_SLAB(20),
    GATE(3),
    ;
    int value;

    Height(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
