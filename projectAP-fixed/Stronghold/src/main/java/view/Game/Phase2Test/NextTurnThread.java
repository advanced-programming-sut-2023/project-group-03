package view.Game.Phase2Test;

import Model.Field.GameMap;
import Model.GamePlay.Game;
import javafx.stage.Stage;
import view.Game.GameMenu;
import view.Game.SetGameMenu;

public class NextTurnThread extends Thread{
    private Game game;
    private boolean gameStarted = false;
    private boolean gameFinished =false;
//    GameMap gameMap;
//    int mapSize;
//    Stage stage;
//    GameMenu gameMenu;
//    SetGameMenu menu;
//
//    public NextTurnThread(GameMenu gameMenu) {
//        this.gameMenu = gameMenu;
//    }
//
//    public NextTurnThread(SetGameMenu menu) {
//        this.menu = menu;
//    }
//
//    public NextTurnThread(GameMap gameMap, int mapSize, Stage stage) {
//        this.gameMap = gameMap;
//        this.mapSize = mapSize;
//        this.stage = stage;
//    }

    public NextTurnThread(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (!gameStarted) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        System.out.println("game has started.");

        while (!gameFinished){
            game.nextTurnPhase3();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public synchronized void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
        notify();
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }
}
