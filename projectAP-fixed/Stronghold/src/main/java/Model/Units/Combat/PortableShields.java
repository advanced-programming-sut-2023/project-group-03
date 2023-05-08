package Model.Units.Combat;

import Model.Field.Tile;
import Model.GamePlay.Player;

import java.util.ArrayList;

public class PortableShields extends CombatUnit{

    private ArrayList<Troop> underShield;
    private int defenseRate = 2;
    private int cost = 10;

    public void addToShield(Troop troop) {

    }

    public void removeFromShield(Troop troop) {

    }

    public void updateShield() {

    }

    public PortableShields(Player owner, Tile position) {
        super(owner, position,"portable shields");
        owner.decreaseGold(10);
    }

    public ArrayList<Troop> getUnderShield() {
        return underShield;
    }

    public int getDefenseRate() {
        return defenseRate;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public void check() {
        super.check();
        AutoMove();
    }

    @Override
    public void print() {

    }
}
