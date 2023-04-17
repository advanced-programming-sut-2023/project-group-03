package Model.Buildings.Defending;

import Model.Feild.Tile;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;

import java.util.ArrayList;

public class Wall extends CastleBuilding {
    private WallTypes type;
    private ArrayList<Troop> troops;
    private Trap trap;

    public Wall(Player owner, Tile position,WallTypes type) {
        super(owner, position);
    }


    public void addTroop(Troop troop) {

    }

    public void removeTroop(Troop troop) {

    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public Trap getTrap() {
        return trap;
    }

    public boolean setTrap(Trap trap) {
        //this.trap = trap;
        return false;
    }

    @Override
    public void check() {

    }

    @Override
    public void print() {

    }

}
