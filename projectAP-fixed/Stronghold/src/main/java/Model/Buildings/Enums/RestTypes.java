package Model.Buildings.Enums;

import Model.Field.Texture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public enum RestTypes {
    //STABLE,
    // HOVEL;
    ;

    RestTypes(String name, int length, int width, int gold, int wood, HashSet<Texture> textures) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.gold = gold;
        this.wood = wood;
        this.textures = textures;
    }

    private String name;
    private int length;
    private int width;
    private int gold;
    private int wood;
    private HashSet<Texture> textures;

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getGold() {
        return gold;
    }

    public int getWood() {
        return wood;
    }

    public HashSet<Texture> getTextures() {
        return textures;
    }


    public static RestTypes getBuildingTypeByName(String name) {
        for (RestTypes type : RestTypes.values()) {
            if (type.name.equals(name)) return type;
        }
        return null;
    }
}
