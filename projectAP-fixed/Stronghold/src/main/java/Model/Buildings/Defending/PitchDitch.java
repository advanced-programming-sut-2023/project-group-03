package Model.Buildings.Defending;

import Model.Buildings.Defending.Enums.TrapsTypes;
import Model.Field.Tile;
import Model.GamePlay.Player;

public class PitchDitch extends Trap {
    public PitchDitch(Player owner, Tile position) {
        super(owner, position, TrapsTypes.PITCH_DITCH);
    }

    public void check() {

    }

    public void print() {

    }
}
