package Model.Units.Combat;

import Model.Buildings.Enums.Resources;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;

import java.util.ArrayList;

public class SiegeTower extends CombatUnit{
    private final static int goldCost = 10;
    private final static int stoneCost = 10;
    private final static int woodCost = 10;

    public SiegeTower(Player owner, Tile position, Tile target) {
        super(owner, position,"siege tower");
        owner.decreaseGold(goldCost);
        targets.add(Material.STONE);
        owner.decreaseInventory(Resources.WOOD, woodCost);
        owner.decreaseInventory(Resources.STONE, stoneCost);
        //maybe TODO
    }

    public static int getGoldCost() {return goldCost;}

    public static int getWoodCost() {return woodCost;}

    public static int getStoneCost() {return stoneCost;}

    public int getRangeIncreaseRate() {
        return RangeIncreaseRate;
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    private int RangeIncreaseRate;
    private ArrayList<Troop> troops;
    private Tile target;

    public void addTroop(Troop troop) {

    }

    public void removeTroop(Troop troop) {

    }

    public void MakeLadder() {

    }

    @Override
    public void attackTo(Tile tile) {
        super.attackTo(tile);
    }

    public SiegeTower(Player owner, Tile position) {
        super(owner, position,"siege tower");
    }

    public Tile getTarget() {
        return target;
    }

    public void setTarget(Tile target) {
        this.target = target;
    }

    public void makeStairs() {

    }
    @Override
    public void check() {
        super.check();
        attackToEnemy();
        AutoMove();
    }

    @Override
    public void print() {

    }
}
