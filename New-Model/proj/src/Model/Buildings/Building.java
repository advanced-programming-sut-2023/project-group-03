package Model.Buildings;

import Model.Field.Texture;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;

import java.util.HashSet;

public abstract class Building extends Drawable {
    protected int length;
    protected int width;
    protected int stoneCost;
    protected int woodCost;
    protected int goldCost;

    protected HashSet<Texture> textures;
    public Building(Player owner, Tile position) {
        super(owner, position);
    }
}
