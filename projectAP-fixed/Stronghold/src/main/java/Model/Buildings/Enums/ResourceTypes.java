package Model.Buildings.Enums;

import java.util.ArrayList;

public enum ResourceTypes {
    WEAPON,
    FOOD,
    STOCK,
    ;

    private ArrayList<Resources> subset = new ArrayList<>();
    private InventoryTypes inventoryType;

    public ArrayList<Resources> getSubset() {
        return subset;
    }
}
