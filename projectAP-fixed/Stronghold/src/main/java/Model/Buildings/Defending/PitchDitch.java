package Model.Buildings.Defending;

import Model.Buildings.Defending.Enums.TrapsTypes;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.graphics.MapFX;

public class PitchDitch extends Trap {
    public PitchDitch(Player owner, Tile position, MapFX mapFX) {
        super(owner, position, TrapsTypes.PITCH_DITCH, mapFX);
    }

    public void check() {
        super.check();
    }

    public void print() {

    }
}
