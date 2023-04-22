package model.Buildings;

import model.Field.Texture;
import model.Field.Tile;
import model.GamePlay.Drawable;
import model.GamePlay.Player;

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

    protected boolean shouldBreak(){
        if(HP<=0){
            position.destroyBuilding();
            return true;
        }
        return false;
    }
}
