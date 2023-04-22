package model.Buildings.Defending.Enums;

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

    public String getName() {
        return name;
    }

    public int getHP() {
        return HP;
    }

    public int getStoneCost() {
        return stoneCost;
    }
}
