package Model.Buildings.Defending.Enums;

import Model.Field.RegularTextureGroups;
import Model.Field.Texture;

import java.util.HashSet;

public enum GateTypes {
    SMALL_STONE_GATE_HOUSE("small stone gate", 1000, 3, 5, 0, 0, RegularTextureGroups.NORMAL.getTextures()),
    BIG_STONE_GATE_HOUSE("big stone gate", 2000, 3, 5, 20, 0,  RegularTextureGroups.NORMAL.getTextures()),
    DRAWBRIDGE("drawbridge", 1000, 2, 1, 0, 10,  RegularTextureGroups.NORMAL.getTextures()),
    ;

    private String name;
    private int HP;
    private int length;
    private int width;
    private int stoneCost;
    private int woodCost;

    private HashSet<Texture> textures;

    GateTypes(String name, int HP, int length, int width, int stoneCost, int woodCost, HashSet textures) {
        this.textures = textures;
        this.name = name;
        this.HP = HP;
        this.length = length;
        this.width = width;
        this.stoneCost = stoneCost;
        this.woodCost = woodCost;
    }

    public String getName() {
        return name;
    }

    public int getHP() {
        return HP;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
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
