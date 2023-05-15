package Model.Buildings.Enums;

import java.util.HashSet;

public enum Resources
{
     FLOUR("flour", ResourceTypes.FOOD, 10),
    IRON("iron", ResourceTypes.STOCK, 10),
    STONE("stone", ResourceTypes.STOCK,10),
    OIL("oil", ResourceTypes.STOCK, 10),
    WOOD("wood", ResourceTypes.STOCK, 10),
    METAL_ARMOUR("metal armour", ResourceTypes.WEAPON, 10),
    SWORD("sword", ResourceTypes.WEAPON, 10),
    PIKE("pike", ResourceTypes.WEAPON, 10),
    SPEAR("spear", ResourceTypes.WEAPON,10),
    LEATHER_ARMOUR("leather armour", ResourceTypes.WEAPON,10),
    BOW("bow", ResourceTypes.WEAPON,10),
    CROSSBOW("crossbow", ResourceTypes.STOCK,10),
    LAVA("lava", ResourceTypes.STOCK,10),
    APPLE("apple", ResourceTypes.FOOD,10),
    CHEESE("cheese", ResourceTypes.FOOD,10),
    BARLEY("barley", ResourceTypes.FOOD,10),
    MEAT("meat", ResourceTypes.FOOD,10),
    WHEAT("wheat", ResourceTypes.FOOD,10),
    BREAD("bread", ResourceTypes.FOOD,10),
    WINE("wine", ResourceTypes.FOOD,10),
    HORSE("horse", ResourceTypes.WEAPON,10)
    ;

    private ResourceTypes type;
    private int gold;
    private String name;
    private static HashSet<Resources> foods = new HashSet<>();
    private int sellPrice;
    static {
        foods.add(APPLE);
        foods.add(BREAD);
        foods.add(CHEESE);
        foods.add(MEAT);
    }
    Resources(String name, ResourceTypes type, int gold) {
        this.type = type;
        this.gold = gold;
        this.name = name;
        sellPrice = (int) Math.floor(0.8 * gold);
    }

    public ResourceTypes getType() {
        return type;
    }

    public int getGold() {
        return gold;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public static Resources getResourceByName(String name) {
        for (Resources resource : Resources.values()) {
            if (resource.getName().equals(name)) return resource;
        }
        return null;
    }

    public static HashSet<Resources> getFoods() {
        return foods;
    }


}
