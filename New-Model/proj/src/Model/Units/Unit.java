package Model.Units;

import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;

public abstract class Unit extends Drawable {

    public Unit(Player owner, Tile position) {
        super(owner, position);
    }

    public boolean moveTo(Tile tile) {

        return true;
    }

    @Override
    public void check() {

    }
}
