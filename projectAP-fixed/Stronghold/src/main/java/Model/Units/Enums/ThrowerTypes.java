package Model.Units.Enums;

import Model.GamePlay.Material;

public enum ThrowerTypes {
    CATAPULTS("catapults", 120, 5, 100, 40, 80,Material.STONE, 2,2),
    TREBUCHETS("trebuchets", 80, 8, 80, 20, 50,Material.STONE, 2,1),
    FIRE_BALLISTA("fire Ballista",100,10,90,30,60,Material.WOOD, 1,0),
    TREBUCHETS_ON_TOWER("trebuchets on tower",80,0,120,35,70,Material.STONE, 4,2),
    FIRE_BALLISTA_ON_TOWER("fire ballista on tower",100,0,90,40,90,Material.STONE, 4,1);
    ;
    private Material target;
    private String name;
    private int damage;
    private int speed;
    private int HP;
    private int range;
    private int gold;
    private int engineer;
    private int power;
    ThrowerTypes(String name, int damage, int speed, int HP, int range, int gold,Material target, int engineer,int power) {
        this.speed =speed;
        this.name = name;
        this.damage = damage;
        this.HP = HP;
        this.range = range;
        this.gold = gold;
        this.target = target;
        this.engineer = engineer;
        this.power = power;
    }

    public int getEngineer() {
        return engineer;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getHP() {
        return HP;
    }

    public Material getTarget() {
        return target;
    }

    public int getRange() {
        return range;
    }

    public int getGold() {
        return gold;
    }

    public int getSpeed() {
        return speed;
    }

    public static ThrowerTypes getThrowerTypeByName(String name) {
        for (ThrowerTypes throwerType : ThrowerTypes.values()) {
            if (throwerType.name.equals(name)) return throwerType;
        }
        return null;
    }

    public int getPower() {
        return power;
    }
}
