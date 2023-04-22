package model.Field;

public class GameMap {
    private int size;
    private Tile[][] map;
    private int numberOfPlayers;
    public GameMap(int size) {
        this.size=size;
        map = new Tile[size][size];
        //...
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
        this.numberOfPlayers = numberOfPlayers;
    }
}