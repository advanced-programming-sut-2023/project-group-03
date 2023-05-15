package Model.Field;

import Model.Element;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMap extends Element {
    private final int size;
    private Tile center;
    private final Tile[][] map;
    private int numberOfPlayers;
    private String name;
    private ArrayList<Drawable> drawables = new ArrayList<>();

    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }

    public void setDrawables(ArrayList<Drawable> drawables) {
        this.drawables = drawables;
    }

    private final Player[] players = new Player[4];

    public GameMap(int size) {
        this.size = size;
        map = new Tile[size][size];
        Tile.setGameMap(this);
        //...
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = new Tile(Height.GROUND, Texture.GROUND);
                map[i][j].setColumnNum(j);
                map[i][j].setRowNum(i);
            }
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                HashMap<Direction, Tile> neighbours = new HashMap<>();
                if (j - 1 >= 0) {
                    neighbours.put(Direction.LEFT, map[i][j - 1]);
                }
                if (i - 1 >= 0) {
                    neighbours.put(Direction.UP, map[i - 1][j]);
                }
                if (j + 1 < map.length) {
                    neighbours.put(Direction.RIGHT, map[i][j + 1]);
                }
                if (i + 1 < map.length) {
                    neighbours.put(Direction.DOWN, map[i + 1][j]);
                }
                map[i][j].setNeighbours(neighbours);
            }
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j].updateHeight();
                map[i][j].updateNeighbours();
            }
        }
    }

    public void updateMap(int xpos, int ypos) {

    }

    public int getSize() {
        return size;
    }

    public Tile[][] getMap() {
        return map;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        if (this.numberOfPlayers > 1) {
            return;
        }
        this.numberOfPlayers = numberOfPlayers;
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new Player(null, null);
            if (i == 0) {
                players[i].setFlagColor(flagColors.RED);
            } else if (i == 1) {
                players[i].setFlagColor(flagColors.GREEN);
            } else if (i == 2) {
                players[i].setFlagColor(flagColors.BLUE);
            } else if (i == 3) {
                players[i].setFlagColor(flagColors.BLACK);
            }
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j].setOwner(players[0]);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tile getCenter() {
        return center;
    }

    public void setCenter(int row, int column) {
        this.center = map[row][column];
    }

    public void setCenter(Tile tile) {
        this.center = tile;
    }

    public void showMap(int halfSide) {
        System.out.print("╔");
        int colNumber = halfSide * 3;
        for (int i = 0; i < (2 * colNumber + 1); i++) {
            System.out.print("═══════╦");
        }
        System.out.println();
        for (int i = center.getRowNum() - halfSide; i < center.getRowNum() + halfSide + 1; i++) {
            for (int j = 0; j < 3; j++) {
                if (j % 3 == 2) {
                    System.out.print("╠");
                } else {
                    System.out.print("║");
                }
                for (int k = center.getColumnNum() - colNumber; k < center.getColumnNum() + colNumber + 1; k++) {
                    System.out.print(map[i][k].show()[j]);
                }
                System.out.println();
            }
        }
    }

    public void setPlayerI(int numberOfPlayer, Player player) {
        this.players[numberOfPlayer] = player;
    }

    public Player[] getPlayers() {
        return players;
    }
}
