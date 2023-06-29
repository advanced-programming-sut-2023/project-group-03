package Model.Buildings.Defending;

import Model.Buildings.Defending.Enums.TrapsTypes;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.graphics.MapFX;

public class CagedWarDogs extends Trap {
    public CagedWarDogs(Player owner, Tile position, MapFX mapFX) {
        super(owner, position, TrapsTypes.CAGED_WAR_DOGS, mapFX);
    }

    public void check() {
        super.check();
    }

    public void print() {

    }
}
