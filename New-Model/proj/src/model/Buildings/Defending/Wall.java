package model.Buildings.Defending;

import model.Buildings.Defending.Enums.WallTypes;
import model.Field.Tile;
import model.GamePlay.Player;

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
        if(this.trap == null){
            return false;
        }
        this.trap = trap;
        return true;
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
