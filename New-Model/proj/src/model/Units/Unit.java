package model.Units;

import model.Field.Tile;
import model.GamePlay.Drawable;
import model.GamePlay.Player;

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
