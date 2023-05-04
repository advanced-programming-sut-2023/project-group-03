package Model.Buildings;

import Model.Buildings.Defending.Gates;
import Model.Field.GameMap;
import Model.Field.Texture;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;

import java.util.HashSet;

public abstract class Building extends Drawable {
    protected int length;
    protected int width;
    protected final int size;
    protected int stoneCost;
    protected int woodCost;
    protected int goldCost;

    protected static HashSet<Texture> textures;
    public Building(Player owner, Tile position, int size) {
        super(owner, position);
        this.size = size;
    }

    public static HashSet<Texture> getTextures() {
        return textures;
    }
    protected boolean shouldBreak(){
        if(HP<=0){
            this.erase();
            return true;
        }
        return false;
    }

    public void erase() {
        super.erase();
        GameMap gameMap = Tile.getGameMap();
        int halfSize = size / 2;
        for (int x = position.getRowNum() - halfSize; x < position.getRowNum() + halfSize; x++) {
            for (int y = position.getColumnNum() - halfSize; y < position.getColumnNum() + halfSize; y++){
                gameMap.getMap()[x][y].setBuilding(null);
            }
        }

        if (this instanceof Gates) {
            Gates gates = ((Gates) this);
            for (Tile tile : gates.getTerminals()) tile.setBuilding(null);
        }
    }
}
