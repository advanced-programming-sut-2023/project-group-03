package Model.Buildings.Defending;

import Model.Buildings.Defending.Enums.TrapsTypes;
import Model.Field.Tile;
import Model.GamePlay.Player;

public class PitchDitch extends Trap{
    public PitchDitch(Player owner, Tile position, TrapsTypes trapsType) {
        super(owner, position, trapsType);
    }

    public void check() {

    }

    public void print() {

    }
}
