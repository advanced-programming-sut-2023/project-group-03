package Model.Buildings.Defending;

import Model.Buildings.Defending.Enums.WallTypes;
import Model.Field.Tile;
import Model.GamePlay.Player;

public class Wall extends CastleBuilding {
    private WallTypes type;
    private Trap trap;

    public Wall(Player owner, Tile position,WallTypes type) {
        super(owner, position, 1);
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

    public WallTypes getType() {
        return type;
    }
}
