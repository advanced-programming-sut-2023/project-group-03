package model.Buildings.Enums;

public enum GeneratorTypes {
//    MILL,
//    INN,
//    IRON_MINE,
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
    private int inventory;
    private int capacity;
    private Resources product;
    private Resources use;
    private int wood;
    private int gold;
    private int worker;

    GeneratorTypes(int rate, int inventory, int capacity, Resources product, Resources use, int wood, int gold, int worker) {
        this.rate = rate;
        this.inventory = inventory;
        this.capacity = capacity;
        this.product = product;
        this.use = use;
        this.wood = wood;
        this.gold = gold;
        this.worker = worker;
    }

    public int getInventory() {
        return inventory;
    }

    public int getCapacity() {
        return capacity;
    }

    public Resources getProduct() {
        return product;
    }

    public Resources getUse() {
        return use;
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
