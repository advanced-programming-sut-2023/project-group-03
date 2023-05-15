package Model.Units.Enums;

import Model.Buildings.Enums.Resources;
import Model.GamePlay.Material;
import Model.Units.Combat.CombatUnit;

import java.util.Collections;
import java.util.HashSet;

public enum TroopTypes {
    KING("king", 100, 10, 300, 0, 0, Material.WOOD),
    ARCHER("archer", 18, 15, 50, 15, 0, Material.FLESH, Resources.BOW),
    CROSSBOWMEN("crossbowmen", 30, 10, 100, 12, 0, Material.FLESH, Resources.LEATHER_ARMOUR, Resources.CROSSBOW),
    SPEARMEN("spearmen", 30, 15, 80, 0, 0, Material.WOOD, Resources.SPEAR),
    PIKEMEN("pikemen", 40, 10, 120, 0, 0, Material.WOOD, Resources.METAL_ARMOUR, Resources.PIKE),
    MACEMEN("macemen", 40, 20, 90, 0, 0, Material.WOOD, Resources.LEATHER_ARMOUR),
    SWORDMEN("swordmen", 60, 4, 150, 0, 0, Material.WOOD, Resources.SWORD, Resources.METAL_ARMOUR),
    KNIGHT("knight", 80, 25, 200, 0, 20, Material.WOOD, Resources.SWORD, Resources.HORSE, Resources.METAL_ARMOUR),
    TUNELLER("tunneler", 30, 20, 60, 0, 30, Material.STONE),
    LADDERMEN("laddermen", 0, 30, 30, 1, 3, Material.FLESH),
    BLACKMONK("blackmonk", 40, 13, 120, 0, 40, Material.WOOD),
    ARABIAN_BOWS("arabian bows", 30, 15, 60, 20, 30, Material.FLESH),
    SLAVES("slaves", 20, 15, 40, 15, 10, Material.STONE),
    SLINGERS("slingers", 30, 15, 60, 15, 30, Material.STONE),
    ASSASSINS("assassins", 50, 15, 60, 0, 30, Material.WOOD),
    HORSE_ARCHERS("horse archers", 30, 25, 50, 25, 40, Material.FLESH),
    ;
    private final String name;
    private final int damage;
    private final int speed;
    private final int HP;
    private final int range;
    private final int gold;
    Material target;
    HashSet<Resources> equipment = new HashSet<>();

    TroopTypes(String name, int damage, int speed, int HP, int range, int gold, Material target, Resources... equipments) {
        this.target = target;
        this.speed = speed;
        this.name = name;
        this.damage = damage;
        this.HP = HP;
        this.range = range;
        this.gold = gold;
        Collections.addAll(this.equipment, equipments);
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

    public Material getTarget() {
        return target;
    }

    public void setTarget(Material target) {
        this.target = target;
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
