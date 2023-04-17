package Model.Buildings;

import Model.Feild.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;

public abstract class Building extends Drawable {
    protected int length;
    protected int width;
    public Building(Player owner, Tile position) {
        super(owner, position);
    }
}
