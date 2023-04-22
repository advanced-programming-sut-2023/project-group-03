package Model.Units.Enums;

import Model.Units.Combat.CombatUnit;

public enum TroopTypes {

    ;
    private String name;
    private int damage;
    private int Speed;
    private int HP;
    private int range;
    private int gold;
    TroopTypes(String name, int damage, int speed, int HP, int range, int gold) {
        this.Speed=speed;
        this.name = name;
        this.damage = damage;
        this.HP = HP;
        this.range = range;
        this.gold = gold;
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
        return Speed;
    }

    public CombatUnit create(String name) {

        return null;
    }
}
