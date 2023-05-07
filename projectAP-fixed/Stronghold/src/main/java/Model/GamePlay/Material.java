package Model.GamePlay;

public enum Material {
    FLESH(1),
    WOOD(2),
    STONE(3),
    IRON(4);
    private int value;

    Material(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
