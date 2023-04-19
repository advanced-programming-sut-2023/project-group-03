package Model.Buildings.Defending;

public enum WallTypes {
    BIG("", 2, 2);
    //SMALL;

    private String name;
    private int HP;
    private int stoneCost;

    WallTypes(String name, int HP, int stoneCost) {
        this.name = name;
        this.HP = HP;
        this.stoneCost = stoneCost;
    }
}
