package Model.Units.Enums;

public enum ThrowerTypes {
//    CATAPULTS,
//    TREBUCHETS,
//    FIRE_BALLISTA,
//    CATAPULTS_ON_TOWER,
//    TREBUCHETS_ON_TOWER,
//    FIRE_BALLISTA_ON_TOWER;
    ;
    private String name;
    private int damage;
    private int Speed;
    private int HP;
    private int range;
    private int gold;
    ThrowerTypes(String name, int damage, int speed, int HP, int range, int gold) {
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
}