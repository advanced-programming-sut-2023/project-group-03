package Model.Buildings.Defending;

import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;

import java.util.ArrayList;

public class Wall extends CastleBuilding {
    private WallTypes type;
    private Trap trap;

    public Wall(Player owner, Tile position,WallTypes type) {
        super(owner, position);
        this.HP = type.getHP();
        this.stoneCost = type.getStoneCost();
    }

    public Trap getTrap() {
        return trap;
    }

    public boolean setTrap(Trap trap) {
        //this.trap = trap;
        //TODO
        return false;
    }

    @Override
    public void check() {

    }

    @Override
    public void print() {

    }

}
