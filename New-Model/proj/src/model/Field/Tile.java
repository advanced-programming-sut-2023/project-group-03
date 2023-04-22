package model.Field;

import model.Buildings.Building;
import model.GamePlay.Player;
import model.Units.Unit;

import java.util.ArrayList;
import java.util.HashMap;
public class Tile {
    int xpos;
    int ypos;
    private height height;
    private Texture texture;
    private Building building;
    private ArrayList<Unit> units;
    private HashMap<Direction,Tile> neighbours;
    private Player owner;

    public Tile(height height, Texture texture) {
        this.height = height;
        this.texture = texture;
    }

    public void destroyBuilding(){}

    public boolean addBuilding(Building building) {
        return false;
    }

    public void removeBuilding() {
    }

    public boolean addUnit(Unit unit) {
        return false;
    }


    public boolean removeUnit(Unit unit) {
        return true;
    }

    public int getXpos() {
        return xpos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    public height getHeight() {
        return height;
    }

    public void setHeight(height height) {
        this.height = height;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
