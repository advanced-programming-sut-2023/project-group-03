package Model.Units.Combat;

import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Enums.ThrowerTypes;

public class Throwers extends CombatUnit{
    private ThrowerTypes type;
    private int cost;

    public Throwers(Player owner, Tile position, ThrowerTypes type) {
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

    public ThrowerTypes getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public void print() {

    }
}
