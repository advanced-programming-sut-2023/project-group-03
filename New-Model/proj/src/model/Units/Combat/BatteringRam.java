package model.Units.Combat;

import model.Field.Tile;
import model.GamePlay.Player;

public class BatteringRam extends CombatUnit {
    protected int cost;
    private Tile target;

    public BatteringRam(Player owner, Tile position) {
        super(owner, position);
        cost = 10;// TODO
    }

    @Override
    public void attackTo(Tile tile) {
        super.attackTo(tile);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
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
