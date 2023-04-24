package Model.Buildings.Defending.Enums;

import Model.Buildings.Enums.RestTypes;
import Model.Field.Texture;
import org.w3c.dom.Text;

import java.util.HashSet;

public enum WallTypes {
//    BIG("", 2, 2),
    //SMALL,
;
    private String name;
    private int HP;
    private int stoneCost;
    private HashSet<Texture> textures;

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
