package model.Buildings.Defending;

import model.Buildings.Defending.Enums.TowerTypes;
import model.Field.Tile;
import model.GamePlay.Player;
import model.Units.Combat.Throwers;

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
        if(shouldBreak()){
            return;
        }
    }

    @Override
    public void print() {

    }
}
