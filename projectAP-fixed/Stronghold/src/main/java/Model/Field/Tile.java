package Model.Field;

import Model.Buildings.Building;
import Model.Buildings.Defending.Enums.Stair;
import Model.Buildings.Defending.Enums.WallTypes;
import Model.Buildings.Defending.Gates;
import Model.Buildings.Defending.Towers;
import Model.Buildings.Defending.Wall;
import Model.Element;
import Model.GamePlay.Player;
import Model.Units.Unit;
import view.Enums.ConsoleColors;

import java.util.ArrayList;
import java.util.HashMap;

public class Tile extends Element {
    private static GameMap gameMap;
    int rowNum;
    int columnNum;
    private Stair ladder = null;
    private Height height;
    private Texture texture;
    private Building building = null;
    private mazafaza mazafaza;
    private ArrayList<Unit> units = new ArrayList<>();
    private HashMap<Direction, Tile> neighbours = new HashMap<>();
    private ArrayList<Tile> neighboursConnected = new ArrayList<>();
    private Player owner;
    private ArrayList<Tile> tilesToEdit = new ArrayList<>();

    public Tile(Height height, Texture texture) {
        this.height = height;
        this.texture = texture;
    }

    public void setHeight(Height height) {
        this.height = height;
    }


    public static void setGameMap(GameMap map) {
        gameMap = map;
    }

    public static GameMap getGameMap() {
        return gameMap;
    }

    public void removeBuilding() {
        this.building = null;
        updateHeight();
        updateNeighbours();
    }

    public boolean addUnit(Unit unit) {
        units.add(unit);
        return false;
    }


    public boolean removeUnit(Unit unit) {
        units.remove(unit);
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
        this.columnNum = columnNum;
    }

    public Height getHeight() {
        return height;
    }

    public void updateHeight(Height height) {
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
        updateHeight();
        updateNeighbours();
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

    public void updateNeighbours() {
        for (Direction direction : neighbours.keySet()) {
            if (neighbours.get(direction) != null) {
                Tile current = neighbours.get(direction);
                neighboursConnected.remove(neighbours.get(direction));
                current.neighboursConnected.remove(this);


                if (Math.abs(this.height.getValue() + modifiedLadder() - current.height.getValue() - current.modifiedLadder()) > 1) {
                    neighboursConnected.remove(neighbours.get(direction));
                } else {
                    neighboursConnected.add(current);
                    current.neighboursConnected.add(this);
                }
            }
        }
    }

    public void updateHeight() {
        if (this.texture == Texture.WATER) {
            this.height = Height.WATER;
        } else if (this.building instanceof Wall) {
            Wall wall = ((Wall) building);
            if (wall.getType() == WallTypes.BIG) {
                this.height = Height.BIG_WALL;
            } else {
                this.height = Height.SMALL_WALL;
            }
        } else if (this.building instanceof Gates) {
            Gates gates = ((Gates) building);
            this.height = Height.GATE;
        } else if (this.building instanceof Towers) {
            Towers towers = ((Towers) building);
            this.height = Height.BIG_TOWER;
        } else if (this.texture == Texture.STONE_SLAB) {
            this.height = Height.STONE_SLAB;
        }
    }

    public int modifiedLadder() {
        if (ladder != null) {
            return 1;
        } else return 0;
    }

    public String[] show() {
        String flag = ConsoleColors.formatPrinter(owner.getFlagColor().getColor(), ConsoleColors.TEXT_BG_BLACK, "#");
        String numberOfUnits = "";
        if (units.size() == 0) {
            numberOfUnits = ConsoleColors.formatPrinter("", texture.getColor(), String.format(":%3d", units.size()));
        } else {
            numberOfUnits = ConsoleColors.formatPrinter(owner.getFlagColor().getColor(), ConsoleColors.TEXT_BG_BLACK,
                    String.format(":%3d", units.size()));
        }
        String numberOfUnitsBuff = "";
        if (this.building != null) {
            char charForBuilding = Building.getTagOfBuilding(building);
            if (building instanceof Wall || building instanceof Towers || building instanceof Gates) {
                numberOfUnitsBuff = ConsoleColors.formatPrinter(owner.getFlagColor().getColor(), ConsoleColors.TEXT_BG_BLACK, "║");
            } else {
                numberOfUnitsBuff = ConsoleColors.formatPrinter("", texture.getColor(), "║");
            }
            numberOfUnitsBuff += ConsoleColors.formatPrinter("", texture.getColor(), "" + charForBuilding);
        } else if (this.mazafaza != null) {
            numberOfUnitsBuff = ConsoleColors.formatPrinter("", texture.getColor(), "║Q");
        } else {
            numberOfUnitsBuff = ConsoleColors.formatPrinter("", texture.getColor(), "  ");
        }
        String[] ans = new String[4];
        String coordinate = String.format("%3d,%3d", rowNum + 1, columnNum + 1);
        ans[2] = "═══════╬";
        ans[0] = flag + numberOfUnits + numberOfUnitsBuff + "║";
        ans[1] = ConsoleColors.formatPrinter("", texture.getColor(), coordinate) + "║";
        return ans;
    }

    public HashMap<Direction, Tile> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(HashMap<Direction, Tile> neighbours) {
        this.neighbours = neighbours;
    }

    public ArrayList<Tile> getNeighboursConnected() {
        return neighboursConnected;
    }

    public Stair getLadder() {
        return ladder;
    }

    public void setLadder(Stair ladder) {
        this.ladder = ladder;
    }

    public ArrayList<Tile> getTilesToEdit() {
        return tilesToEdit;
    }

    public void setNeighboursConnected(ArrayList<Tile> neighboursConnected) {
        this.neighboursConnected = neighboursConnected;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public void setTilesToEdit(ArrayList<Tile> tilesToEdit) {
        this.tilesToEdit = tilesToEdit;
    }
}
