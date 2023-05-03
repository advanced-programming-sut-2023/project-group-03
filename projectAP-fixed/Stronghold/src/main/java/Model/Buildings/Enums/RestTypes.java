package Model.Buildings.Enums;

import Model.Field.Texture;

import java.util.HashSet;

public enum RestTypes {
    //STABLE,
    // HOVEL;
    ;

    RestTypes(String name, int length, int width, int gold, int wood, HashSet<Texture> textures) {
        this.name = name;
        this.gold = gold;
        this.wood = wood;
        this.textures = textures;
    }

    private String name;
    private final int size = 1;
    private int gold;
    private int wood;
    private HashSet<Texture> textures;

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
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


    public static RestTypes getTypeByName(String name) {
        for (RestTypes type : RestTypes.values()) {
            if (type.name.equals(name)) return type;
        }
        return null;
    }
}
