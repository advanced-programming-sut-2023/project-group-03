package Model.Buildings.Defending.Enums;

import Model.Field.RegularTextureGroups;
import Model.Field.Texture;

import java.util.HashSet;

public enum TowerTypes {
    LOOKOUT_TOWER("lookout tower", 1000, 1, 1, 10, 10, 20,  RegularTextureGroups.NORMAL.getTextureHashSet()),
    PERIMETER_TOWER("perimeter tower",1200,1,1,10,10,10, RegularTextureGroups.NORMAL.getTextureHashSet() ),
    TURRET("turret", 1500,1,1,15,10,10, RegularTextureGroups.NORMAL.getTextureHashSet()),
    SQUARE_TOWER("square tower", 3000, 3,3,35,15,5, RegularTextureGroups.NORMAL.getTextureHashSet()),
    ROUND_TOWER("round tower",4000,3,3,40,18,5, RegularTextureGroups.NORMAL.getTextureHashSet())
    ;

    private String name;
    private int HP;
    private int size;
    private int width;
    private int stoneCost;
    private int defenseIncrease;
    private int rangeIncrease;
    private HashSet<Texture> textures;

    TowerTypes(String name, int HP, int size, int width, int stoneCost, int defenseIncrease, int rangeIncrease, HashSet textures) {
        this.textures = textures;
        this.name = name;
        this.HP = HP;
        this.size = size;
        this.width = width;
        this.stoneCost = stoneCost;
        this.defenseIncrease = defenseIncrease;
        this.rangeIncrease = rangeIncrease;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getSize() {
        return size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getStoneCost() {
        return stoneCost;
    }

    public void setStoneCost(int stoneCost) {
        this.stoneCost = stoneCost;
    }

    public int getDefenseIncrease() {
        return defenseIncrease;
    }

    public void setDefenseIncrease(int defenseIncrease) {
        this.defenseIncrease = defenseIncrease;
    }

    public int getRangeIncrease() {
        return rangeIncrease;
    }

    public void setRangeIncrease(int rangeIncrease) {
        this.rangeIncrease = rangeIncrease;
    }

    public HashSet<Texture> getTextures() {
        return textures;
    }

    public static TowerTypes getTypeByName(String name) {
        for (TowerTypes type : TowerTypes.values()) {
            if (type.name.equals(name)) return type;
        }
        return null;
    }
}
