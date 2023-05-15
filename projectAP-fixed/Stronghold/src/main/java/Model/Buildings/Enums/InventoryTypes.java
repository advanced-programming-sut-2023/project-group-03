package Model.Buildings.Enums;

import Model.Field.RegularTextureGroups;
import Model.Field.Texture;

import java.util.Arrays;
import java.util.HashSet;

import static Model.Buildings.Enums.Resources.*;

public enum InventoryTypes {
    STOCKPILE(ResourceTypes.STOCK, "stockpile", 10000, 20000, null, 0, 0, RegularTextureGroups.NORMAL.getTextureHashSet()),
    ARMOURY(ResourceTypes.WEAPON, "armoury", 10000, 10000, null, 0, 0, RegularTextureGroups.NORMAL.getTextureHashSet()),
    FOOD_STORAGE(ResourceTypes.FOOD, "food storage", 10000, 10000, null, 0, 0, RegularTextureGroups.NORMAL.getTextureHashSet()),
    ;
    private final ResourceTypes resource;
    private final String name;
    private final int HP;
    private final int size = 1;
    private final int capacity;
    private final HashSet<Resources> resources;
    private final int wood;
    private final int stoneCost;
    private final HashSet<Texture> textures;

    private final HashSet<Resources> stockPileResources = new HashSet<Resources>(
            Arrays.asList(IRON, STONE, OIL, WOOD, CROSSBOW, LAVA)
    );

    private final HashSet<Resources> armouryResources = new HashSet<Resources>(
            Arrays.asList(METAL_ARMOUR, SWORD, PIKE, SPEAR, LEATHER_ARMOUR, BOW, HORSE)
    );

    private final HashSet<Resources> foodStorageResources = new HashSet<Resources>(
            Arrays.asList(FLOUR, APPLE, CHEESE, BARLEY, MEAT,
                    WHEAT, BREAD, WINE)
    );

    InventoryTypes(ResourceTypes resource, String name, int HP, int capacity, HashSet<Resources> resources, int wood, int stoneCost, HashSet<Texture> textures) {
        this.resource = resource;
        this.name = name;
        this.capacity = capacity;
        this.wood = wood;
        this.stoneCost = stoneCost;
        this.textures = textures;
        this.HP = HP;
        if (name.equals("stockpile")) this.resources = stockPileResources;
        else if (name.equals("armoury")) this.resources = armouryResources;
        else this.resources = foodStorageResources;
    }

    public int getHP() {
        return HP;
    }

    public int getSize() {
        return size;
    }

    public ResourceTypes getResource() {
        return resource;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public HashSet<Resources> getResources() {
        return resources;
    }

    public int getWood() {
        return wood;
    }

    public int getStoneCost() {
        return stoneCost;
    }

    public HashSet<Texture> getTextures() {
        return textures;
    }

    public static InventoryTypes getTypeByName(String name) {
        for (InventoryTypes type : InventoryTypes.values()) {
            if (type.name.equals(name)) return type;
        }
        return null;
    }
}
