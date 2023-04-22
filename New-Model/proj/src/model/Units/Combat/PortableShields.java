package model.Units.Combat;

import model.Field.Tile;
import model.GamePlay.Player;

import java.util.ArrayList;

public class PortableShields extends CombatUnit{

    private ArrayList<Troop> underShield;
    private int defenseRate;
    private int cost;

    public void addToShield(Troop troop) {

    }

    public void removeFromShield(Troop troop) {

    }

    public void updateShield() {

    }

    public PortableShields(Player owner, Tile position) {
        super(owner, position);
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
    public void print() {

    }
}
