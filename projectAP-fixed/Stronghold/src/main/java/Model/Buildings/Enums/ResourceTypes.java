package Model.Buildings.Enums;

import java.util.ArrayList;

public enum ResourceTypes {
    WEAPON,
    FOOD,
    STOCK;
    private ArrayList<Resources> subset = new ArrayList<>();

    ResourceTypes() {
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
