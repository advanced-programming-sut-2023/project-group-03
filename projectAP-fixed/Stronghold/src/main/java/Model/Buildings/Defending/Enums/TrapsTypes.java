package Model.Buildings.Defending.Enums;

import Model.Field.RegularTextureGroups;
import Model.Field.Texture;

import java.util.HashSet;

public enum TrapsTypes {
    BOILING_OIL("boiling oil", 3, 3, 3, 50, 3, RegularTextureGroups.ALL_LAND.getTextureHashSet()),
    PITCH_DITCH("pitch ditch", 0, 0, 2, 100, 0, RegularTextureGroups.OIL_RIG.getTextureHashSet()),
    CAGED_WAR_DOGS("caged war dogs", 100, 10, 0, 50, 0, RegularTextureGroups.NORMAL.getTextureHashSet()),
    KILLING_PIT("killing pit", 0, 6, 0, 150, 0, RegularTextureGroups.NORMAL.getTextureHashSet()),
    ;

    private String name;
    private final int size = 3;
    private int gold;
    private int wood;
    private int oil;
    private int worker;
    private int damage;
    private HashSet<Texture> textures;

    TrapsTypes(String name, int gold, int wood, int oil, int damage, int worker, HashSet<Texture> textures) {
        this.textures = textures;
        this.name = name;
        this.gold = gold;
        this.wood = wood;
        this.oil = oil;
        this.worker = worker;
    }

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

    public int getOil() {
        return oil;
    }

    public int getWorker() {
        return worker;
    }

    public int getDamage() {
        return damage;
    }

    public HashSet<Texture> getTextures() {
        return textures;
    }

    public static TrapsTypes getTypeByName(String name) {
        for (TrapsTypes type : TrapsTypes.values()) {
            if (type.name.equals(name)) return type;
        }
        return null;
    }
}
