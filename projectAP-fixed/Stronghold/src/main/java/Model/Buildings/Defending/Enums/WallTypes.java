package Model.Buildings.Defending.Enums;

import Model.Field.RegularTextureGroups;
import Model.Field.Texture;

import java.util.HashSet;

public enum WallTypes {
    BIG("big wall", 700, 10, RegularTextureGroups.NORMAL.getTextureHashSet()),
    SMALL("small wall", 300, 6, RegularTextureGroups.NORMAL.getTextureHashSet());
    private final String name;
    private final int HP;
    private final int size = 1;
    private final int stoneCost;
    private final HashSet<Texture> textures;

    WallTypes(String name, int HP, int stoneCost, HashSet<Texture> textures) {
        this.textures = textures;
        this.name = name;
        this.HP = HP;
        this.stoneCost = stoneCost;
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

    public HashSet<Texture> getTextures() {
        return textures;
    }

    public static WallTypes getTypeByName(String name) {
        for (WallTypes type : WallTypes.values()) {
            if (type.name.equals(name)) return type;
        }
        return null;
    }
}
