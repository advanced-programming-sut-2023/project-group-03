package Model.Units.Combat;

import Model.Field.Tile;
import Model.GamePlay.Player;

import java.util.ArrayList;

public class PortableShields extends CombatUnit {

    private ArrayList<Troop> underShield;
    private final int defenseRate = 2;
    private static final int cost = 10;

    private static final int wood = 10;

    public void addToShield(Troop troop) {

    }

    public void removeFromShield(Troop troop) {

    }

    public void updateShield() {

    }

    public PortableShields(Player owner, Tile position) {
        super(owner, position, "portable shields");

        owner.decreaseGold(10);
    }

    public static int getWood() {
        return wood;
    }

    public ArrayList<Troop> getUnderShield() {
        return underShield;
    }

    public int getDefenseRate() {
        return defenseRate;
    }

    public static int getCost() {
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
