package Model.Buildings.Enums;

public enum GeneratorTypes {
//    MILL,
//    INN,
//    IRONMINE,
//    OX_TETHER,
//    PITCH_RIG,
//    QUARRY,
//    WOODCUTTER,
//    ARMOURER,
//    TANNER,
//    BLACKSMITH,
//    FLETCHER,
//    POLE_TURNER,
//    OIL_SMELTER,
//    ORCHARD,
//    DAIRY_FARM,
//    BARLEY_FARM,
//    HUNTERS_HUT,
//    WHEAT_FARM,
//    BAKERY,
//    BREWERY,
//    STABLE,
//    HOVEL,
//    CHURCH;
    ;
    private int rate;
    private int Inventory;
    private int Capacity;
    private Resources product;
    private Resources Use;
    private int wood;
    private int gold;
    private int worker;

    GeneratorTypes(int rate, int inventory, int capacity, Resources product, Resources use, int wood, int gold, int worker) {
        this.rate = rate;
        Inventory = inventory;
        Capacity = capacity;
        this.product = product;
        Use = use;
        this.wood = wood;
        this.gold = gold;
        this.worker = worker;
    }

    public int getInventory() {
        return Inventory;
    }

    public int getCapacity() {
        return Capacity;
    }

    public Resources getProduct() {
        return product;
    }

    public Resources getUse() {
        return Use;
    }

    public int getWood() {
        return wood;
    }

    public int getGold() {
        return gold;
    }

    public int getRate() {
        return rate;
    }

    public int getWorker() {
        return worker;
    }
}
