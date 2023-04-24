package Model.Buildings.Defending.Enums;

import Model.Buildings.Enums.RestTypes;
import Model.Field.Texture;

import java.util.HashSet;

public enum GateTypes {
//    SMALL_STONE_GATE_HOUSE("", 0, 3, 5, 6)
//    BIG_STONE_GATE_HOUSE,
//    DRAWBBRIDGE
    ;

    private String name;
    private int HP;
    private int length;
    private int width;
    private int stoneCost;

    private HashSet<Texture> textures;

    GateTypes(String name, int HP, int length, int width, int stoneCost, HashSet textures) {
        this.textures = textures;
        this.name = name;
        this.HP = HP;
        this.length = length;
        this.width = width;
        this.stoneCost = stoneCost;
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
