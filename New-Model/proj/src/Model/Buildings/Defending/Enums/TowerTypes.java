package Model.Buildings.Defending.Enums;

public enum TowerTypes {
    LOOKOUT_TOWER("", 1, 1, 1, 1, 11, 1)
//    PERIMETER_TOWER,
//    TURRET,
//    SQUARE_TOWER,
//    ROUND_TOWER
    ;

    private String name;
    private int HP;
    private int length;
    private int width;
    private int stoneCost;
    private int defenseIncrease;
    private int rangeIncrease;

    TowerTypes(String name, int HP, int length, int width, int stoneCost, int defenseIncrease, int rangeIncrease) {
        this.name = name;
        this.HP = HP;
        this.length = length;
        this.width = width;
        this.stoneCost = stoneCost;
        this.defenseIncrease = defenseIncrease;
        this.rangeIncrease = rangeIncrease;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getStoneCost() {
        return stoneCost;
    }

    public void setStoneCost(int stoneCost) {
        this.stoneCost = stoneCost;
    }

    public int getDefenseIncrease() {
        return defenseIncrease;
    }

    public void setDefenseIncrease(int defenseIncrease) {
        this.defenseIncrease = defenseIncrease;
    }

    public int getRangeIncrease() {
        return rangeIncrease;
    }

    public void setRangeIncrease(int rangeIncrease) {
        this.rangeIncrease = rangeIncrease;
    }
}
