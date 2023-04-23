package Model.Buildings.Defending.Enums;

public enum TrapsTypes {
    BOILING_OIL("", 3, 3, 3, 3)
//    PITCH_DITCH,
//    CAGED_WAR_DOGS,
//    KILLING_PIT
    ;

    String name;
    private int gold;
    private int wood;
    private int oil;
    private int worker;

    TrapsTypes(String name, int gold, int wood, int oil, int worker) {
        this.name=name;
        this.gold = gold;
        this.wood = wood;
        this.oil = oil;
        this.worker = worker;
    }

    public int getGold() {
        return gold;
    }

    public int getWood() {
        return wood;
    }

    public int getOil() {
        return oil;
    }

    public int getWorker() {
        return worker;
    }
}
