package Model.Buildings.Defending.Enums;

import Model.Buildings.Enums.RestTypes;
import Model.Field.Texture;

import java.util.HashSet;

public enum TrapsTypes {
//    BOILING_OIL("", 3, 3, 3, 3)
//    PITCH_DITCH,
//    CAGED_WAR_DOGS,
//    KILLING_PIT
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
