package Model.Units.Combat;

import Model.Buildings.Enums.Resources;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.graphics.MapFX;

public class BatteringRam extends CombatUnit {
    private final static int goldCost = 10;
    private final static int stoneCost = 0;
    private final static int woodCost = 10;
    private Tile target;

    public BatteringRam(Player owner, Tile position, MapFX mapFX) {
        super(owner, position, "battering ram", mapFX);
        this.setDamage(1500);
        this.setMaterial(Material.FLESH);
        this.setSpeed(7);
        this.setHP(3600);
        this.setBaseRange(0);
        this.setGold(25);
        owner.decreaseGold(goldCost);
        owner.decreaseInventory(Resources.WOOD, woodCost);
        owner.decreaseInventory(Resources.STONE, stoneCost);
        targets.add(Material.STONE);
        // TODO
    }

    @Override
    public void attackTo(Tile tile) {
        super.attackTo(tile);
    }

    public static int getGoldCost() {
        return goldCost;
    }

    public static int getStoneCost() {
        return stoneCost;
    }

    public static int getWoodCost() {
        return woodCost;
    }

    public Tile getTarget() {
        return target;
    }

    public void setTarget(Tile target) {
        this.target = target;
    }

    @Override
    public void check() {
        if (EnemyTarget == null) {
            return;
        }
        attackToEnemy();
    }

    @Override
    public void print() {

    }
}
