package Model.Buildings.Enums;

import Model.GamePlay.Material;

public enum TypeOfBarracks {
    ;
//    BARRACK,
//    MERCENARY_POST,
//    ENGINEER_GUILD,
//    TUNNELER_GUILD,
//    SIEGE_TENT;
    private int gold;
    private int wood;
    private int oil;
    private Material material;

    TypeOfBarracks(int gold, int wood, int oil, Material material) {
        this.gold = gold;
        this.wood = wood;
        this.oil = oil;
        this.material = material;
    }

    public int getGold() {
        return gold;
    }

    public int getWood() {
        return wood;
    }

    public int getOil() {
        return oil;
    }

    public Material getMaterial() {
        return material;
    }
}
