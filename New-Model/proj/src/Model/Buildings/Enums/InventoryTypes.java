package Model.Buildings.Enums;

import java.util.HashSet;

public enum InventoryTypes {
//    STOCKPILE,
//    ARMOURY,
//    FOOD_STORAGE
    ;
    private TypeOfResource resource;
    private String name;
    private int capacity;
    private HashSet<Resources> resources;
    private int wood;

    InventoryTypes(TypeOfResource resource, String name, int capacity, HashSet<Resources> resources, int wood) {
        this.resource = resource;
        this.name = name;
        this.capacity = capacity;
        this.resources = resources;
        this.wood = wood;
    }

    public TypeOfResource getResource() {
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
}