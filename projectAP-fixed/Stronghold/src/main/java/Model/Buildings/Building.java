package Model.Buildings;

import Model.Buildings.Enums.Resources;
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
    protected static int size;
    protected int stoneCost;
    protected int woodCost;
    protected int goldCost;

    public void setName(String name) {
        this.name = name;
    }

    protected static HashSet<Texture> textures;

    public Building(Player owner, Tile position, int size,String name) {
        super(owner, position, name);
        this.size = size;

        GameMap gameMap = Tile.getGameMap();
        int xCenter = position.getRowNum();
        int yCenter = position.getColumnNum();
        int halfSize = size / 2;
        for (int x = xCenter - halfSize; x <= xCenter + halfSize; x++) {
            for (int y = yCenter - halfSize; y <= yCenter + halfSize; y++) {
                gameMap.getMap()[x][y].setBuilding(this);
            }
        }
    }

    protected void manageCost() {
        owner.decreaseGold(goldCost);
        owner.decreaseInventory(Resources.WOOD, woodCost);
        owner.decreaseInventory(Resources.STONE, stoneCost);
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

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        size = size;
    }

    public int getStoneCost() {
        return stoneCost;
    }

    public void setStoneCost(int stoneCost) {
        this.stoneCost = stoneCost;
    }

    public int getWoodCost() {
        return woodCost;
    }

    public void setWoodCost(int woodCost) {
        this.woodCost = woodCost;
    }

    public int getGoldCost() {
        return goldCost;
    }

    public void setGoldCost(int goldCost) {
        this.goldCost = goldCost;
    }

    public static void setTextures(HashSet<Texture> textures) {
        Building.textures = textures;
    }
}
