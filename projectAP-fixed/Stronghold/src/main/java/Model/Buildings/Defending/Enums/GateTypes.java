package Model.Buildings.Defending.Enums;

import Model.Field.Texture;

import java.util.HashSet;

public enum GateTypes {
    SMALL_STONE_GATE_HOUSE(null, 0, 3, 5, 6, 0, null),
    BIG_STONE_GATE_HOUSE(null, 0, 3, 5, 6, 0, null),
    DRAWBRIDGE(null, 0, 3, 5, 6, 0, null),
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
