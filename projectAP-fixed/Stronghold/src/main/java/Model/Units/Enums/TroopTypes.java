package Model.Units.Enums;

import Model.Buildings.Enums.BarracksType;
import Model.Buildings.Enums.Resources;
import Model.GamePlay.Material;
import Model.Units.Combat.CombatUnit;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import view.fxmlMenu.GameLayout;

import java.util.Collections;
import java.util.HashSet;

public enum TroopTypes {
    KING("king",null, 100, 10, 300, 0, 0, Material.WOOD),
    ARCHER("archer",BarracksType.BARRACK, 18, 15, 50, 15, 0, Material.FLESH, Resources.BOW),
    CROSSBOWMEN("crossbowmen",BarracksType.BARRACK, 30, 10, 100, 12, 5, Material.FLESH, Resources.LEATHER_ARMOUR, Resources.CROSSBOW),
    SPEARMEN("spearmen",BarracksType.BARRACK, 30, 15, 80, 0, 0, Material.WOOD, Resources.SPEAR),
    PIKEMEN("pikemen",BarracksType.BARRACK, 40, 10, 120, 0, 5, Material.WOOD, Resources.METAL_ARMOUR, Resources.PIKE),
    MACEMEN("macemen",BarracksType.BARRACK, 40, 20, 90, 0, 13, Material.WOOD, Resources.LEATHER_ARMOUR),
    SWORDMEN("swordmen",BarracksType.BARRACK, 60, 4, 150, 0, 3, Material.WOOD, Resources.SWORD, Resources.METAL_ARMOUR),
    KNIGHT("knight",BarracksType.BARRACK, 80, 25, 200, 0, 20, Material.WOOD, Resources.SWORD, Resources.HORSE, Resources.METAL_ARMOUR),
    TUNELLER("tunneler",BarracksType.TUNNELER_GUILD, 30, 20, 60, 0, 30, Material.STONE),
    LADDERMEN("laddermen",BarracksType.SIEGE_TENT, 0, 30, 30, 1, 3, Material.FLESH),
    BLACKMONK("blackmonk",BarracksType.MERCENARY_POST, 40, 13, 120, 0, 40, Material.WOOD),
    ARABIAN_BOWS("arabian bows",BarracksType.MERCENARY_POST, 30, 15, 60, 20, 30, Material.FLESH),
    SLAVES("slaves",BarracksType.MERCENARY_POST, 20, 15, 40, 15, 10, Material.STONE),
    SLINGERS("slingers",BarracksType.MERCENARY_POST, 30, 15, 60, 15, 30, Material.STONE),
    ASSASSINS("assassins",BarracksType.MERCENARY_POST, 50, 15, 60, 0, 30, Material.WOOD),
    HORSE_ARCHERS("horse archers",BarracksType.MERCENARY_POST, 30, 25, 50, 25, 40, Material.FLESH),
    ;
    private BarracksType barracksType;
    private final String name;
    private final int damage;
    private final int speed;
    private final int HP;
    private final int range;
    private final int gold;
    Material target;
    HashSet<Resources> equipment = new HashSet<>();

    private ImagePattern imagePattern;

    TroopTypes(String name,BarracksType barracksType, int damage, int speed, int HP, int range, int gold, Material target, Resources... equipments) {
        this.target = target;
        this.speed = speed;
        this.barracksType = barracksType;
        this.name = name;
        this.damage = damage;
        this.HP = HP;
        this.range = range;
        this.gold = gold;
        Collections.addAll(this.equipment, equipments);
        //System.out.println(name);
        if(barracksType!=null)
        imagePattern = new ImagePattern(new Image(GameLayout.class.getResource("/images/troops/Menu/" + name + ".png").toExternalForm()));
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

    public BarracksType getBarracksType() {
        return barracksType;
    }

    public ImagePattern getImagePattern() {
        return imagePattern;
    }
}
