package model.Buildings.Defending;

import model.Buildings.Defending.Enums.GateTypes;
import model.Field.Tile;
import model.GamePlay.Player;
import model.Units.Combat.Troop;

public class Gates extends CastleBuilding {
    private GateTypes type;
    private boolean isOpen;

    public Gates(Player owner, Tile position, GateTypes type) {
        super(owner, position);
        this.HP=type.getHP();
        this.length = type.getLength();
        this.width = type.getWidth();
        this.stoneCost = type.getStoneCost();

    }

    private void updateOwner() {
        for (Troop now: troops) {
            if(now.getOwner().equals(owner)){
                return;
            }
        }
        if(troops.size()==0){
            return;
        }
        owner = troops.get(0).getOwner();
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
        if(shouldBreak()){
            return;
        }
        updateOwner();
        updateCloseOrOpen();
    }

    @Override
    public void print() {

    }

}
