package Model.Buildings.Defending;

import Model.Buildings.Defending.CastleBuilding;
import Model.Buildings.Defending.Enums.StairsTypes;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.graphics.MapFX;


public class Stair extends CastleBuilding {
    private StairsTypes types;

    public Stair(Player owner, Tile position, StairsTypes type, MapFX mapFX) {
        super(owner, position, 1, type.getName(), mapFX);
    }

    @Override
    public void check() {
        if (HP < 0) {
            this.erase();
        }
    }

    @Override
    public void print() {

    }
}
