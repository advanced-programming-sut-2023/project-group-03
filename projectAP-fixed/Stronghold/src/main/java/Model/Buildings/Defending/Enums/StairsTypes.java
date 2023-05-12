package Model.Buildings.Defending.Enums;

import Model.GamePlay.Material;

public enum StairsTypes {
    STAIRS("stairs"),
    LADDER("ladder"),
    SEIEGETOWER("seigetower"),
    ;
    private String name;

    StairsTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
