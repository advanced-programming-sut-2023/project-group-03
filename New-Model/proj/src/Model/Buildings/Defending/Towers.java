package Model.Buildings.Defending;

import Model.Feild.Tile;
import Model.GamePlay.Player;
import Model.Units.Combat.Throwers;
import Model.Units.Combat.Troop;

import java.util.ArrayList;

public class Towers extends CastleBuilding {
    private TowerTypes type;
    private Throwers thrower;
    private ArrayList<Troop> troops;
    private int defenseIncrease;
    private int rangeIncrease;

    public Towers(Player owner, Tile position, TowerTypes type) {
        super(owner, position);
        this.setHP(type.HP);
        this.
    }

    public void addTroop(Troop troop) {

    }

    public void removeTroop(Troop troop) {

    }

    public Throwers getThrower() {
        return thrower;
    }

    public void setThrower(Throwers thrower) {
        this.thrower = thrower;
    }

    public TowerTypes getType() {
        return type;
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public int getDefenseIncrease() {
        return defenseIncrease;
    }

    public int getRangeIncrease() {
        return rangeIncrease;
    }

    @Override
    public void check() {

    }

    @Override
    public void print() {

    }
}
