package view.Game.Phase2Test;

import Model.Field.GameMap;
import javafx.stage.Stage;
import view.Game.GameMenu;
import view.Game.SetGameMenu;
import view.Menu;
import view.Transition;

public class GameThread extends Thread{
    GameMap gameMap;
    int mapSize;
    Stage stage;
    GameMenu gameMenu;
    SetGameMenu menu;

    public GameThread(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
    }

    public GameThread(SetGameMenu menu) {
        this.menu = menu;
    }

    public GameThread(GameMap gameMap, int mapSize, Stage stage) {
        this.gameMap = gameMap;
        this.mapSize = mapSize;
        this.stage = stage;
    }

    @Override
    public void run() {
//        GameGraphic gameGraphic = new GameGraphic(gameMap, mapSize);
//        try {
//            gameGraphic.start(stage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        menu.RunHandler();
    }
}
