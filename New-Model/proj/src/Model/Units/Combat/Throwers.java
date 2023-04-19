package Model.Units.Combat;

import Model.Field.Tile;
import Model.GamePlay.Player;

public class Throwers extends CombatUnit{
    private Throwers type;
    private int cost;

    public Throwers(Player owner, Tile position, Throwers type) {
        super(owner, position);
    }

    @Override
    public boolean moveTo(Tile tile) {
        return super.moveTo(tile);
    }

    @Override
    public void attackTo(Tile tile) {
        super.attackTo(tile);
    }

    public Throwers getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public void print() {

    }
}
