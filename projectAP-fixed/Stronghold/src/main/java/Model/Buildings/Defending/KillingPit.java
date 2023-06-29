package Model.Buildings.Defending;

import Model.Buildings.Defending.Enums.TrapsTypes;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.graphics.MapFX;

public class KillingPit extends Trap {
    public KillingPit(Player owner, Tile position, MapFX mapFX) {
        super(owner, position, TrapsTypes.KILLING_PIT, mapFX);
    }

    public void check() {
        super.check();
    }

    public void print() {

    }
}
