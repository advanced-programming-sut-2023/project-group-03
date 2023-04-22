package model.Buildings.Enums;

public enum RestTypes {
    //STABLE,
    // HOVEL;
    ;

    RestTypes(int length, int width, int gold, int wood) {
        this.length = length;
        this.width = width;
        this.gold = gold;
        this.wood = wood;
    }

    private int length;
    private int width;
    private int gold;
    private int wood;

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getGold() {
        return gold;
    }

    public int getWood() {
        return wood;
    }
}
