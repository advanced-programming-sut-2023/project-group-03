package Model.Units.Enums;

import Model.Units.Combat.CombatUnit;
import Model.Buildings.Enums.Resources;

import java.util.HashSet;

public enum TroopTypes {

    ;
    private String name;
    private int damage;
    private int speed;
    private int HP;
    private int range;
    private int gold;
    HashSet<Resources> equipment;
    TroopTypes(String name, int damage, int speed, int HP, int range, int gold, HashSet<Resources> equipment) {
        this.speed =speed;
        this.name = name;
        this.damage = damage;
        this.HP = HP;
        this.range = range;
        this.gold = gold;
        this.equipment = equipment;
    }

    public HashSet<Resources> getEquipment() {
        return equipment;
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

    public int getRange() {
        return range;
    }


    public int getGold() {
        return gold;
    }

    public int getSpeed() {
        return speed;
    }

    public CombatUnit create(String name) {
        return null;
    }

    public static TroopTypes getTroopTypeByName(String name) {
        for (TroopTypes throwerType : TroopTypes.values()) {
            if (throwerType.name.equals(name)) return throwerType;
        }
        return null;
    }
}
