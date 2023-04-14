package Model.Buildings.Defending;

public enum TowerTypes {
    LOOKOUT_TOWER("", 1, 1, 1, 1, 11, 1);
//    PERIMETER_TOWER,
//    TURRET,
//    SQUARE_TOWER,
//    ROUND_TOWER;

    public String name;
    public int HP;
    public int lenght;
    public int width;
    public int stoneCost;
    public int defenseIncrease;
    public int rangeIncrease;

    TowerTypes(String name, int HP, int lenght, int width, int stoneCost, int defenseIncrease, int rangeIncrease) {
        this.name = name;
        this.HP = HP;
        this.lenght = lenght;
        this.width = width;
        this.stoneCost = stoneCost;
        this.defenseIncrease = defenseIncrease;
        this.rangeIncrease = rangeIncrease;
    }
}
