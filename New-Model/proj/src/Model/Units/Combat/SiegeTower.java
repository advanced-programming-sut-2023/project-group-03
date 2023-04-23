package Model.Units.Combat;

import Model.Field.Tile;
import Model.GamePlay.Player;

import java.util.ArrayList;

public class SiegeTower extends CombatUnit{
    private int cost;
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
        super(owner, position);
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
