package view.Game.Phase2Test;

import Model.Field.GameMap;
import Model.graphics.MapFX;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Scanner;

public class GameGraphic extends Application {
    private Pane mapPane;
    private Pane gamePane;
    private Scene gameScene;
    private MapFX mapHandler;
    private GameMap gameMap;
    private int mapSize;


    public GameGraphic(GameMap gameMap, int mapSize) {
        this.gameMap = gameMap;
        this.mapSize = mapSize;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("in the game graphics");

        //setup stage
        primaryStage.setHeight(720);
        primaryStage.setWidth(1240);
        System.out.println("setup stage");

        //setup panes
        gamePane = new Pane();
        mapPane = new Pane();
        gamePane.getChildren().add(mapPane);
        System.out.println("setup panes");

        mapHandler = new MapFX(mapSize, mapPane, primaryStage, gameMap);
        System.out.println("made mapFX");

        gameScene = new Scene(gamePane);
        primaryStage.setScene(gameScene);
        primaryStage.show();
        System.out.println("showing the stage.");

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();
                if (keyCode.equals(KeyCode.RIGHT) || keyCode.equals(KeyCode.LEFT) ||
                        keyCode.equals(KeyCode.UP) || keyCode.equals(KeyCode.DOWN)) {
                    mapHandler.moveMapWithKeys(event);
                }
                if (keyCode.equals(KeyCode.F)) {
                    primaryStage.close();
                }
            }
        });
    }

    public MapFX getMapHandler() {
        return mapHandler;
    }
}
