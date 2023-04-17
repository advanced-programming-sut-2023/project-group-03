package Model.Buildings.Defending;

public enum WallTypes {
    BIG("", 2, 2);
    //SMALL;

    public String name;
    public int HP;
    public int stoneCost;

    WallTypes(String name, int HP, int stoneCost) {
        this.name = name;
        this.HP = HP;
        this.stoneCost = stoneCost;
    }
}
