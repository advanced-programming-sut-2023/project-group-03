package Model.Buildings.Enums;

import Model.Field.RegularTextureGroups;
import Model.Field.Texture;
import Model.GamePlay.Material;

import java.util.HashSet;

public enum BarracksType {
    BARRACK("barrack",150,0,0,15,0,0,Material.STONE, RegularTextureGroups.NORMAL.getTextures()),
    MERCENARY_POST("mercenary post", 150, 0,10,0,0,0,Material.WOOD,RegularTextureGroups.NORMAL.getTextures()),
    ENGINEER_GUILD("engineer guild", 180,100,10,0,0,0,Material.WOOD,RegularTextureGroups.NORMAL.getTextures()),
    TUNNELER_GUILD("tunneler guild", 180,100,0,15,5,0,Material.STONE,RegularTextureGroups.NORMAL.getTextures()),
    SIEGE_TENT("siege tent",0,0,0,0,0,1,Material.WOOD,RegularTextureGroups.NORMAL.getTextures())
    ;
    private String name;
    private int HP;
    private final int size = 5;
    private int gold;
    private int wood;
    private int stoneCost;
    private int oil;
    private int engineer;
    private Material material;
    private HashSet<Texture> textures;

    private BarracksType(String name, int HP, int gold, int wood, int stoneCost, int oil,int engineer, Material material, HashSet<Texture> textures) {
        this.name = name;
        this.HP = HP;
        this.gold = gold;
        this.wood = wood;
        this.stoneCost = stoneCost;
        this.oil = oil;
        this.engineer = engineer;
        this.material = material;
        this.textures = textures;
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

    public int getGold() {
        return gold;
    }

    public int getWood() {
        return wood;
    }

    public int getStoneCost() {
        return stoneCost;
    }

    public int getOil() {
        return oil;
    }

    public int getEngineer() { return engineer; }

    public Material getMaterial() {
        return material;
    }

    public HashSet<Texture> getTextures() {
        return textures;
    }

    public static BarracksType getTypeByName(String name) {
        for (BarracksType type : BarracksType.values()) {
            if (type.name.equals(name)) return type;
        }
        return null;
    }
}
