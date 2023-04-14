package Model.Buildings.Defending;

import Model.Feild.Tile;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;

import java.util.ArrayList;

public class Gates extends CastleBuilding {
    private GateTypes type;
    private ArrayList<Troop> troops;
    private boolean isOpen;

    public Gates(Player owner, Tile position, GateTypes type) {
        super(owner, position);
    }

    public void addTroop(Troop troop) {

    }

    public void removeTroop(Troop troop) {

    }
    private void updateOwner() {

    }

    private void updateCloseOrOpen() {

    }

    public GateTypes getType() {
        return type;
    }

    public void setType(GateTypes type) {
        this.type = type;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void Open() {
        this.isOpen = true;
    }

    public void Close() {
        this.isOpen = false;
    }

    @Override
    public void check() {

    }

    @Override
    public void print() {

    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

}
