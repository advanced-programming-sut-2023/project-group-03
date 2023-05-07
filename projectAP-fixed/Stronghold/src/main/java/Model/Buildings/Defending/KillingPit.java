package Model.Buildings.Defending;

import Model.Buildings.Defending.Enums.TrapsTypes;
import Model.Field.Tile;
import Model.GamePlay.Player;

public class KillingPit extends Trap{
    public KillingPit(Player owner, Tile position) {
        super(owner, position, TrapsTypes.KILLING_PIT);
    }

    public void check() {

    }

    public void print() {

    }
}
