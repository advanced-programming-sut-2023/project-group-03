package Model.GamePlay;

import Model.Buildings.Building;
import Model.Buildings.Keep;
import Model.Field.GameMap;
import Model.Units.Worker;

import javax.lang.model.type.ErrorType;
import java.util.ArrayList;

public class Game {
    private GameMap map;
    private ArrayList<Player> players;
    private int turn = 1;
    private Player currentPlayer;
    public Game(GameMap map, ArrayList<Player> players) {
        this.map = map;
        this.players = players;
    }

    public boolean nextTurn() {
        if (!currentPlayer.equals(players.get(players.size() - 1))) {
            for (int i = 0; i < players.size() - 1; i++) {
                if (currentPlayer.equals(players.get(i))) {
                    currentPlayer = players.get(i + 1);
                    return false;
                }
            }
        } else {
            int size = Drawable.getDrawables().size() - 1;
            for (int i = size; i >= 0; i--) {
                Drawable.getDrawables().get(i).check();
            }
            size = Drawable.getDrawables().size() - 1;
            for (int i = size; i > 0; i--) {
                if (Drawable.getDrawables().get(i) instanceof Building) {
                    if (!(Drawable.getDrawables().get(i) instanceof Keep)) {
                        Drawable.getDrawables().get(i).check();
                    }
                }
            }
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getKing().getHP() <= 0) {
                    for (int j = Drawable.getDrawables().size() - 1; j >= 0; j--) {
                        Drawable drawable = Drawable.getDrawables().get(j);
                        if (drawable.getOwner().equals(players.get(i))) {
                            drawable.erase();
                        }
                    }
                    players.remove(players.get(i));
                }
            }
            currentPlayer = players.get(0);
            turn++;
            if (FinishGame()) {
                return true;
            }
            return false;
        }
        return false;
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean FinishGame() {
        if (players.size() == 1) {
            System.out.println("winner >>>> "+players.get(0).getUser().getNickname()+" <<<<");
            System.out.println("1000 point for you");
            return true;
        }
        return false;

    }
}
