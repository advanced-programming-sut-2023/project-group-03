package Model.Field;

public class GameMap {
    private int size;
    private Tile center;
    private Tile[][] map;
    private int numberOfPlayers;
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tile getCenter() {
        return center;
    }

    public void setCenter(Tile center) {
        this.center = center;
    }
}
