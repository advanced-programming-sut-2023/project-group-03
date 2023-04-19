package Model.Buildings;

import Model.Feild.Texture;
import Model.Feild.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;

import java.util.HashSet;

public abstract class Building extends Drawable {
    protected int length;
    protected int width;
    protected HashSet<Texture> textures;
    public Building(Player owner, Tile position) {
        super(owner, position);
    }
}
