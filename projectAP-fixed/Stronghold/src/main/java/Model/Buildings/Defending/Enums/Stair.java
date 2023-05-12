package Model.Buildings.Defending.Enums;

import Model.Buildings.Defending.CastleBuilding;
import Model.Field.Tile;
import Model.GamePlay.Player;


public class Stair extends CastleBuilding {
    private StairsTypes types;
    public Stair(Player owner, Tile position,StairsTypes type) {
        super(owner, position, 1, type.getName());
    }

    @Override
    public void check() {

    }

    @Override
    public void print() {

    }
}
