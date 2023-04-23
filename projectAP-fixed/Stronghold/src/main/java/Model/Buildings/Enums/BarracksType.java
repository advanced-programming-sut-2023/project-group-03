package Model.Buildings.Enums;

import Model.Field.Texture;
import Model.GamePlay.Material;

import java.util.ArrayList;
import java.util.HashSet;
import Model.GamePlay.Material;
public enum BarracksType {
//    BARRACK,
//    MERCENARY_POST,
//    ENGINEER_GUILD,
//    TUNNELER_GUILD,
//    SIEGE_TENT
    ;
    private String name;
    private int gold;
    private int wood;
    private int oil;
    private Material material;
    private HashSet<Texture> textures;

    private BarracksType(String name, int gold, int wood, int oil, Material material, HashSet<Texture> textures) {
        this.name = name;
        this.gold = gold;
        this.wood = wood;
        this.oil = oil;
        this.material = material;
        this.textures = textures;
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

    public HashSet<Texture> getTextures() {
        return textures;
    }

    public static BarracksType getBuildingTypeByName(String name) {
        for (BarracksType type : BarracksType.values()) {
            if (type.name.equals(name)) return type;
        }
        return null;
    }
}
