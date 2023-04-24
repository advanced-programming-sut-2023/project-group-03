package Model.Field;

import Model.Buildings.Building;
import Model.GamePlay.Player;
import Model.Units.Unit;
import view.Enums.ConsoleColors;
import view.Game.MapMenu;

import java.util.ArrayList;
import java.util.HashMap;
public class Tile {
    int rowNum;
    int columnNum;
    private height height;
    private Texture texture;
    private Building building;
    private mazafaza mazafaza;
    private ArrayList<Unit> units=new ArrayList<>();
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
    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;}

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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Model.Field.mazafaza getMazafaza() {
        return mazafaza;
    }

    public void setMazafaza(Model.Field.mazafaza mazafaza) {
        this.mazafaza = mazafaza;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public String[] show() {
        String numberOfUnits = String.format("#:%3d",units.size());
        if (this.building != null) {
            numberOfUnits += "║B";
        }
        if (this.mazafaza != null) {
            numberOfUnits += "║T";
        } else {
            numberOfUnits += "║N";
        }
        String[] ans = new String[4];
        String coordinate = String.format("%3d,%3d", rowNum, columnNum);
        ans[3]="═══════╬";
        ans[0] = ConsoleColors.formatPrinter("", texture.getColor(), numberOfUnits)+"║";
        ans[1] = ConsoleColors.formatPrinter("", texture.getColor(), coordinate) + "║";
        ans[2] = ConsoleColors.formatPrinter(owner.getFlagColor().getColor(), texture.getColor(), "║░flag░")+"║";
        return ans;
    }
}
