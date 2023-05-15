package Model.Buildings.Enums;

import java.util.ArrayList;

public enum ResourceTypes {
    WEAPON("weapon"),
    FOOD("food"),
    STOCK("stock"),
    ;

    private String name;

    ResourceTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
