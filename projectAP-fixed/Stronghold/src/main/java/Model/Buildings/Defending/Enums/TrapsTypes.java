package Model.Buildings.Defending.Enums;

import Model.Field.RegularTextureGroups;
import Model.Field.Texture;

import java.util.HashSet;

public enum TrapsTypes {
    BOILING_OIL("boiling oil", 1, 3, 3, 3, 5000, 3, RegularTextureGroups.ALL_LAND.getTextureHashSet()),
    PITCH_DITCH("pitch ditch", 1, 0, 0, 2, 1000, 0, RegularTextureGroups.OIL_RIG.getTextureHashSet()),
    CAGED_WAR_DOGS("caged war dogs", 1, 100, 10, 0, 5000, 0, RegularTextureGroups.NORMAL.getTextureHashSet()),
    KILLING_PIT("killing pit", 1, 0, 6, 0, 4500, 0, RegularTextureGroups.NORMAL.getTextureHashSet()),
    ;

    private final String name;
    private final int size;
    private final int gold;
    private final int wood;
    private final int oil;
    private final int worker;
    private int damage;
    private final HashSet<Texture> textures;

    TrapsTypes(String name, int size, int gold, int wood, int oil, int damage, int worker, HashSet<Texture> textures) {
        this.size = size;
        this.textures = textures;
        this.name = name;
        this.gold = gold;
        this.wood = wood;
        this.oil = oil;
        this.worker = worker;
        this.damage = damage;
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
