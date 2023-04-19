package Model.Buildings.Defending;

public enum GateTypes {
    SMALL_STONE_GATE_HOUSE("", 0, 3, 5, 6);
//    BIG_STONE_GATE_HOUSE,
//    DRAWBBRIDGE;

    private String name;
    private int HP;
    private int lenght;
    private int width;
    private int stoneCost;

    GateTypes(String name, int HP, int lenght, int width, int stoneCost) {
        this.name = name;
        this.HP = HP;
        this.lenght = lenght;
        this.width = width;
        this.stoneCost = stoneCost;
    }

    public String getName() {
        return name;
    }

    public int getHP() {
        return HP;
    }

    public int getLenght() {
        return lenght;
    }

    public int getWidth() {
        return width;
    }

    public int getStoneCost() {
        return stoneCost;
    }
}
