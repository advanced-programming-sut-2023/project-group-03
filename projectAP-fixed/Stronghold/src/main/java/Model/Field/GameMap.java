package Model.Field;

import Model.GamePlay.Player;
public class GameMap {
    private int size;
    private Tile center;
    private Tile[][] map;
    private int numberOfPlayers;
    private String name;
    private Player[] players = new Player[4];
    public GameMap(int size) {
        this.size=size;
        map = new Tile[size][size];
        //...
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = new Tile(height.GROUND,Texture.GROUND);
                map[i][j].setColumnNum(j);
                map[i][j].setRowNum(i);
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
        int colNumber = halfSide * 2;
        for (int i = 0; i < (2 * colNumber + 1); i++) {
            System.out.print("═══════╦");
        }
        System.out.println();
        for (int i = center.getRowNum() - halfSide; i < center.getRowNum() + halfSide + 1; i++) {
            for (int j = 0; j < 4; j++) {
                if (j % 4 == 3) {
                    System.out.print("╠");
                } else {
                    System.out.print("║");
                }
                for (int k = center.getColumnNum() - colNumber; k < center.getColumnNum() +colNumber + 1; k++) {
                    System.out.print(map[i][k].show()[j]);
                }
                System.out.println();
            }
        }
    }

    public Player[] getPlayers() {
        return players;
    }
}
