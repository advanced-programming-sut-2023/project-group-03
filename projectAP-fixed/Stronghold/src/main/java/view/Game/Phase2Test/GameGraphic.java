package view.Game.Phase2Test;

import Model.Buildings.Building;
import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Game;
import Model.graphics.MapFX;
import Model.graphics.MapFX.BuildingShape;
import Model.graphics.MapFX.TileShape;
import controller.ControllerFunctions;
import controller.gameControllers.GameController;
import controller.gameControllers.GeneralGameController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.Enums.GameMenuCommands;
import view.Game.GameMenu;
import view.Game.GraphicalGameMenu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameGraphic extends Application {
    private Pane mapPane;
    private Pane gamePane;
    private Scene gameScene;
    private GameMap gameMap;
    private int mapSize;
    private MapFX mapFX;
    private Game game;
    private GameController gameController;

    private GraphicalGameMenu gameMenu;

    private boolean ctrlPressed = false;
    private boolean menuBarAction = false;
    private boolean multiSelectingTiles = false;
    private boolean showInfo = false;

    


    public GameGraphic(GameMap gameMap, int mapSize, Game game) {
        this.gameMap = gameMap;
        this.mapSize = mapSize;
        this.game = game;
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

        //mapFX
        mapFX = new MapFX(mapSize, mapPane, primaryStage, gameMap);
        mapFX.setGameGraphic(this);
        game.setMapFX(mapFX);
        this.gameController = new GameController(gameMap, mapFX);
        this.gameMenu = new GraphicalGameMenu(game, mapFX);
        System.out.println("made mapFX");

        gameScene = new Scene(gamePane);
        primaryStage.setScene(gameScene);
        primaryStage.show();
        System.out.println("showing the stage.");

        //key pressed
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();

                //move map with keys
                if (keyCode.equals(KeyCode.RIGHT) || keyCode.equals(KeyCode.LEFT) ||
                        keyCode.equals(KeyCode.UP) || keyCode.equals(KeyCode.DOWN)) {
                    mapFX.moveMapWithKeys(event);
                }
                if (keyCode.equals(KeyCode.CONTROL)) ctrlPressed = true;

                //change zoom
                if (keyCode.equals(KeyCode.MINUS) && ctrlPressed) {
                    mapFX.changeZoom(false);
                } else if (keyCode.equals(KeyCode.EQUALS) && ctrlPressed) {
                    mapFX.changeZoom(true);
                }

                if (keyCode.equals(KeyCode.S) && !menuBarAction) {
                    setMultiSelectingTiles(!multiSelectingTiles);
                }

                //change ability to see the info of tiles
                if (keyCode.equals(KeyCode.Q)) {
                    setShowInfo(!showInfo);
                }
            }
        });

        //key released
        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();
                if (keyCode.equals(KeyCode.CONTROL)) ctrlPressed = false;
            }
        });

        mapPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mapFX.startDragging(event);
            }
        });

        mapPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mapFX.mouseReleasedHandler(event);
            }
        });
    }

    public String selectTiles(ArrayList<TileShape> tileShapes) {
        if (tileShapes == null || tileShapes.size() == 0) return null;
        ArrayList<Tile> tiles = new ArrayList<>();
        String output = "select tile";
        Tile tile;
        for (TileShape tileShape : tileShapes) {
            tileShape.shape.setStroke(Color.BLACK);//todo just temporarily for checking
            tileShape.shape.setStrokeWidth(2);
            tiles.add(tileShape.getTile());
            tile = tileShape.getTile();
            output += " " + tile.getRowNum() + " " + tile.getColumnNum();
        }

        //removing previous tiles' colors
        ArrayList<Tile> previousTiles = gameMenu.getSelectedTiles().get(game.getCurrentPlayer());
        for (Tile previousTile : previousTiles) {
            mapFX.getAllRecs()[previousTile.getRowNum()][previousTile.getColumnNum()].shape.setStroke(Color.GREEN);
            mapFX.getAllRecs()[previousTile.getRowNum()][previousTile.getColumnNum()].shape.setStrokeWidth(1);
        
        }

        Matcher matcher = ControllerFunctions.getMatcher(output, GameMenuCommands.SELECT_TILE.toString());
        new GeneralGameController(gameMap, mapFX).selectTilesMatcherHandler(matcher, gameMenu, game.getCurrentPlayer());
        return output;
    }

    public String selectBuilding(BuildingShape buildingShape) {
        Building building = buildingShape.getBuilding();
        String output = "select building -x " + building.getPosition().getRowNum() + " -y " + building.getPosition().getColumnNum();
        Matcher matcher = ControllerFunctions.getMatcher(output, GameMenuCommands.SELECT_BUILDING.toString());
        String result = gameController.selectBuilding(matcher, game.getCurrentPlayer(), gameMenu);
        return output;
    }

    public MapFX getMapFX() {
        return mapFX;
    }

    public boolean isMenuBarAction() {
        return menuBarAction;
    }

    public void setMenuBarAction(boolean isMenuBarAction) {
        this.menuBarAction = isMenuBarAction;
        mapFX.setMenuBarAction(isMenuBarAction);
    }

    public boolean isMultiSelectingTiles() {
        return multiSelectingTiles;
    }

    public void setMultiSelectingTiles(boolean multiSelectingTiles) {
        this.multiSelectingTiles = multiSelectingTiles;
        mapFX.setMultiSelectingTiles(multiSelectingTiles);
    }

    public boolean isShowInfo() {
        return showInfo;
    }

    public void setShowInfo(boolean showInfo) {
        this.showInfo = showInfo;
        mapFX.setShowInfo(showInfo);
    }
}
