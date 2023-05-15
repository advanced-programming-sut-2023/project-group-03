package Model.Units.Enums;

public enum WallClimberTypes {
    ;
    private final String name;
    private final int damage;
    private final int Speed;
    private final int HP;
    private final int range;
    private final int gold;

    WallClimberTypes(String name, int damage, int speed, int HP, int range, int gold) {
        this.Speed = speed;
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

    public static WallClimberTypes getWallClimberType(String name) {
        for (WallClimberTypes type : WallClimberTypes.values()) {
            if (type.name.equals(name)) return type;
        }
        return null;
    }
}
