package Model.Buildings.Defending.Enums;

import Model.Buildings.Enums.RestTypes;
import Model.Field.RegularTextureGroups;
import Model.Field.Texture;

import java.util.HashSet;

public enum TrapsTypes {
    BOILING_OIL("boiling oil", 3, 3, 3, 3, RegularTextureGroups.ALL_LAND.getTextures()),
    PITCH_DITCH("pitch ditch",0,0,2,0,RegularTextureGroups.NORMAL.getTextures()),
    CAGED_WAR_DOGS("caged war dogs",100,10,0,0,RegularTextureGroups.NORMAL.getTextures()),
    KILLING_PIT("killing pit", 0, 6,0,0,RegularTextureGroups.NORMAL.getTextures())
    ;

    String name;
    private int gold;
    private int wood;
    private int oil;
    private int worker;
    private HashSet<Texture> textures;

    TrapsTypes(String name, int gold, int wood, int oil, int worker, HashSet textures) {
        this.textures = textures;
        this.name=name;
        this.gold = gold;
        this.wood = wood;
        this.oil = oil;
        this.worker = worker;
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
