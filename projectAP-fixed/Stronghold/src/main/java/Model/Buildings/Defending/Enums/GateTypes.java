package Model.Buildings.Defending.Enums;

import Model.Field.RegularTextureGroups;
import Model.Field.Texture;

import java.util.HashSet;

public enum GateTypes {
    SMALL_STONE_GATE_HOUSE("small stone gate", 1000, 3, 0, 0, RegularTextureGroups.NORMAL.getTextures()),
    BIG_STONE_GATE_HOUSE("big stone gate", 2000, 5, 20, 0,  RegularTextureGroups.NORMAL.getTextures()),
    DRAWBRIDGE("drawbridge", 1000, 1,  0, 10,  RegularTextureGroups.NORMAL.getTextures()),
    ;

    private String name;
    private int HP;
    private int size;
    private int stoneCost;
    private int woodCost;

    private HashSet<Texture> textures;

    GateTypes(String name, int HP, int size, int stoneCost, int woodCost, HashSet<Texture> textures) {
        this.textures = textures;
        this.name = name;
        this.HP = HP;
        this.size = size;
        this.stoneCost = stoneCost;
        this.woodCost = woodCost;
    }

    public String getName() {
        return name;
    }

    public int getHP() {
        return HP;
    }

    public int getSize() {
        return size;
    }

    public int getStoneCost() {
        return stoneCost;
    }

    public int getWoodCost() {
        return woodCost;
    }

    public HashSet<Texture> getTextures() {
        return textures;
    }

    public static GateTypes getTypeByName(String name) {
        for (GateTypes type : GateTypes.values()) {
            if (type.name.equals(name)) return type;
        }
        return null;
    }
}
