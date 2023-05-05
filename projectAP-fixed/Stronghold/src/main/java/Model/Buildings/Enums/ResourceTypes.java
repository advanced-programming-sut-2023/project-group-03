package Model.Buildings.Enums;

import java.util.ArrayList;

public enum ResourceTypes {
    WEAPON(InventoryTypes.ARMOURY),
    FOOD(InventoryTypes.FOOD_STORAGE),
    STOCK(InventoryTypes.STOCKPILE);

    private ArrayList<Resources> subset = new ArrayList<>();
    private InventoryTypes inventoryType;

    ResourceTypes(InventoryTypes inventoryType) {
        for (Resources now : Resources.values()) {
            if(now.getType().equals(this)){
                this.subset.add(now);
            }
        }
    }

    public ArrayList<Resources> getSubset() {
        return subset;
    }
}
