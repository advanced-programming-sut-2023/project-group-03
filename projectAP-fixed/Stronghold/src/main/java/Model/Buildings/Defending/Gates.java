package Model.Buildings.Defending;

import Model.Buildings.Defending.Enums.GateTypes;
import Model.Field.Direction;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;

import java.util.ArrayList;

public class Gates extends CastleBuilding {
    private GateTypes type;
    private boolean isOpen;
    private Direction direction;
    private ArrayList<Tile> terminals = new ArrayList<>();

    public Gates(Player owner, Tile position, GateTypes type) {
        super(owner, position);
        this.HP = type.getHP();
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

    public void setTerminals(ArrayList<Tile> terminals) {
        this.terminals = terminals;
    }

    public void addTerminal(Tile tile) {
        terminals.add(tile);
    }

    public ArrayList<Tile> getTerminals() {
        return terminals;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void print() {

    }

}
