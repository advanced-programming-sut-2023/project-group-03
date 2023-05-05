package Model.Units.Combat;

import Model.Buildings.Enums.Resources;
import Model.Field.Tile;
import Model.GamePlay.Player;

public class BatteringRam extends CombatUnit {
    private final static int goldCost = 10;
    private final static int stoneCost = 10;
    private final static int woodCost = 10;
    private Tile target;

    public BatteringRam(Player owner, Tile position) {
        super(owner, position);
        owner.decreaseGold(goldCost);
        owner.decreaseInventory(Resources.WOOD, woodCost);
        owner.decreaseInventory(Resources.STONE, stoneCost);
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
    public void print() {

    }
}
