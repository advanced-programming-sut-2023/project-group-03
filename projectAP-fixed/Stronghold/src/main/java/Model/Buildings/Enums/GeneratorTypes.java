package Model.Buildings.Enums;

import Model.Field.Texture;

import java.util.ArrayList;
import java.util.HashSet;

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
    private String name;
    private int rate;
    private int inventory;
    private int capacity;
    private Resources product;
    private Resources use;
    private int wood;
    private int gold;
    private int worker;
    private HashSet<Texture> textures;

    GeneratorTypes(String name, int rate, int inventory, int capacity, Resources product, Resources use, int wood, int gold, int worker, HashSet<Texture> textures) {
        this.name = name;
        this.rate = rate;
        this.inventory = inventory;
        this.capacity = capacity;
        this.product = product;
        this.use = use;
        this.wood = wood;
        this.gold = gold;
        this.worker = worker;
        this.textures = textures;
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

    public HashSet<Texture> getTextures() {
        return textures;
    }

    public static GeneratorTypes getBuildingTypeByName(String name) {
        for (GeneratorTypes type : GeneratorTypes.values()) {
            if (type.name.equals(name)) return type;
        }
        return null;
    }
}
