package Model.Buildings.Defending.Enums;

public enum StairsTypes {
    STAIRS("stairs", 10),
    LADDER("ladder", 0),
    SIEGE_TOWER("siege tower", 0),
    ;
    private final String name;
    private final int stoneCost;

    StairsTypes(String name, int stoneCost) {
        this.name = name;
        this.stoneCost = stoneCost;
    }

    public String getName() {
        return name;
    }

    public int getStoneCost() {
        return stoneCost;
    }
}
