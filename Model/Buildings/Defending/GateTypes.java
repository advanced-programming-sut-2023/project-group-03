package Model.Buildings.Defending;

public enum GateTypes {
    SMALL_STONE_GATE_HOUSE("", 0, 3, 5, 6);
//    BIG_STONE_GATE_HOUSE,
//    DRAWBBRIDGE;

    public String name;
    public int HP;
    public int lenght;
    public int width;
    public int stoneCost;

    GateTypes(String name, int HP, int lenght, int width, int stoneCost) {
        this.name = name;
        this.HP = HP;
        this.lenght = lenght;
        this.width = width;
        this.stoneCost = stoneCost;
    }
}
