package Model.Buildings.Defending;

import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Combat.Throwers;
import Model.Units.Combat.Troop;

import java.util.ArrayList;

public class Towers extends CastleBuilding {
    private TowerTypes type;
    private Throwers thrower;
    private int defenseIncrease;
    private int rangeIncrease;

    public Towers(Player owner, Tile position, TowerTypes type) {
        super(owner, position);
        this.HP = type.getHP();
        this.length = type.getLength();
        this.width = type.getWidth();
        this.stoneCost = type.getStoneCost();
        this.defenseIncrease = type.getDefenseIncrease();
        this.rangeIncrease = type.getRangeIncrease();
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
