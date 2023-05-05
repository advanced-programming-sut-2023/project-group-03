package Model.Field;

import Model.Buildings.Building;
import Model.Buildings.Defending.Enums.WallTypes;
import Model.Buildings.Defending.Gates;
import Model.Buildings.Defending.Towers;
import Model.Buildings.Defending.Wall;
import Model.GamePlay.Player;
import Model.Units.Unit;
import view.Enums.ConsoleColors;

import java.util.ArrayList;
import java.util.HashMap;
public class Tile {
    private static GameMap gameMap;
    int rowNum;
    int columnNum;
    private boolean isLadder = false;
    private Height height;
    private Texture texture;
    private Building building;
    private mazafaza mazafaza;
    private ArrayList<Unit> units=new ArrayList<>();
    private HashMap<Direction, Tile> neighbours = new HashMap<>();
    private ArrayList<Tile> neighboursConnected = new ArrayList<>();
    private Player owner;
    public Tile(Height height, Texture texture) {
        this.height = height;
        this.texture = texture;
    }

//    public void destroyBuilding(){
//        this.building = null;
//        setHeight();
//    }

//    public boolean addBuilding(Building building) {
//        return false;
//    }

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
        this.columnNum = columnNum;}

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

                if (current.getBuilding() instanceof Gates) {
                    Gates gates = ((Gates) current.getBuilding());
                    if (gates.getTerminals().contains(this)) {
                        neighboursConnected.add(neighbours.get(direction));
                        current.neighboursConnected.add(this);
                        return;
                    }
                }
                if (Math.abs(this.height.getValue() - current.height.getValue()) > 1) {
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
        } else if (this.texture == texture.STONE_SLAB) {
            this.height = Height.STONE_SLAB;
        }
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

    public HashMap<Direction, Tile> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(HashMap<Direction, Tile> neighbours) {
        this.neighbours = neighbours;
    }

    public ArrayList<Tile> getNeighboursConnected() {
        return neighboursConnected;
    }
}
