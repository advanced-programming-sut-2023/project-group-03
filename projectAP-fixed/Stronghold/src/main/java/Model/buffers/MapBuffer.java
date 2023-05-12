package Model.buffers;

import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;

import java.util.ArrayList;

public class MapBuffer {
    private int size;
    private TileBuffer[][] map;
    private int numberOfPlayers;
    private String name;
    //private Player[] players = new Player[4];

    public MapBuffer(GameMap gameMap) {
        numberOfPlayers = gameMap.getNumberOfPlayers();
        size = gameMap.getSize();
        name = gameMap.getName();
        map = new TileBuffer[size][size];
        //players = map.getPlayers();
        for (int i = 0; i < gameMap.getSize(); i++) {
            for (int j = 0; j < gameMap.getSize(); j++) {
                TileBuffer tileBuffer = new TileBuffer(gameMap.getMap()[i][j], gameMap);
                this.map[i][j] = tileBuffer;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public TileBuffer[][] getMap() {
        return map;
    }

    public void setMap(TileBuffer[][] map) {
        this.map = map;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameMap HandleMap() {
        GameMap gameMap = new GameMap(this.size);
        gameMap.setName(name);
        gameMap.setNumberOfPlayers(numberOfPlayers);
        for (int i = 0; i < gameMap.getSize(); i++) {
            for (int j = 0; j < gameMap.getSize(); j++) {
                map[i][j].writeOnTile(gameMap.getMap()[i][j],gameMap);
            }
        }
        gameMap.setCenter(size / 2, size / 2);
        return gameMap;
    }

//    public Player[] getPlayers() {
//        return players;
//    }
//
//    public void setPlayers(Player[] players) {
//        this.players = players;
//    }
}
