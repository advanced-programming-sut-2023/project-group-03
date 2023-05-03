package Model.Buildings.Enums;

import Model.Field.RegularTextureGroups;
import Model.Field.Texture;

import java.util.Arrays;
import java.util.HashSet;

public enum  InventoryTypes {
    STOCKPILE(ResourceTypes.STOCK, "stockpile",400, 200, null, 0, 0, RegularTextureGroups.NORMAL.getTextures()),
    ARMOURY(ResourceTypes.WEAPON, "armoury", 500, 0, null, 0, 0,RegularTextureGroups.NORMAL.getTextures()),
    FOOD_STORAGE(ResourceTypes.FOOD, "food storage",200, 0, null, 0, 0,RegularTextureGroups.NORMAL.getTextures()),
    ;
    private ResourceTypes resource;
    private String name;
    private int HP;
    private final int size = 3;
    private int capacity;
    private HashSet<Resources> resources;
    private int wood;
    private int stoneCost;
    private HashSet<Texture> textures;

    InventoryTypes(ResourceTypes resource, String name, int HP, int capacity, HashSet<Resources> resources, int wood, int stoneCost, HashSet<Texture> textures) {
        this.resource = resource;
        this.name = name;
        this.capacity = capacity;
        this.resources = resources;
        this.wood = wood;
        this.stoneCost = stoneCost;
        this.textures = textures;
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
