package Model.GamePlay;

import Model.Field.GameMap;

import java.util.ArrayList;

public class Game {
    private GameMap map;
    private ArrayList<Player> players;
    private int turn;

    public Game(GameMap map, ArrayList<Player> players) {
        this.map = map;
        this.players = players;
    }

    public void nextTurn() {

    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getTurn() {
        return turn;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

}
