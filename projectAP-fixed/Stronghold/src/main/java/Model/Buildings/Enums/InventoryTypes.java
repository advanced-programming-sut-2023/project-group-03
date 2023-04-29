package Model.Buildings.Enums;

import Model.Field.Texture;

import java.util.HashSet;

public enum  InventoryTypes {
    STOCKPILE(null, null, 0, null, 0, 0, null),
    ARMOURY(null, null, 0, null, 0, 0, null),
    FOOD_STORAGE(null, null, 0, null, 0, 0, null),
    ;
    private ResourceTypes resource;
    private String name;
    private int capacity;
    private HashSet<Resources> resources;
    private int wood;
    private int stoneCost;
    private HashSet<Texture> textures;

    InventoryTypes(ResourceTypes resource, String name, int capacity, HashSet<Resources> resources, int wood, int stoneCost, HashSet<Texture> textures) {
        this.resource = resource;
        this.name = name;
        this.capacity = capacity;
        this.resources = resources;
        this.wood = wood;
        this.stoneCost = stoneCost;
        this.textures = textures;
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
