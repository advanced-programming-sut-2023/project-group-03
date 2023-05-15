package Model.Buildings.Enums;

import Model.Field.RegularTextureGroups;
import Model.Field.Texture;

import java.util.HashSet;

public enum RestTypes {
    STABLE("stable", 50, 25, RegularTextureGroups.NORMAL.getTextureHashSet()),
    HOVEL("hovel", 0, 30, RegularTextureGroups.NORMAL.getTextureHashSet()),
    OX_TETHER("ox tether", 5, 25, RegularTextureGroups.NORMAL.getTextureHashSet()),
    ;

    RestTypes(String name, int gold, int wood, HashSet<Texture> textures) {
        this.name = name;
        this.gold = gold;
        this.wood = wood;
        this.textures = textures;
    }

    private final String name;
    private final int size = 1;
    private final int gold;
    private final int wood;
    private final HashSet<Texture> textures;

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
