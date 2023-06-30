package view.Game.Phase2Test;

import Model.Buildings.Barracks;
import Model.Buildings.Building;
import Model.Buildings.Inventory;
import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Game;
import Model.graphics.MapFX;
import Model.graphics.MapFX.BuildingShape;
import Model.graphics.MapFX.TileShape;
import controller.ControllerFunctions;
import controller.Enums.Response;
import controller.gameControllers.GameController;
import controller.gameControllers.GeneralGameController;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import view.Enums.GameMenuCommands;
import view.Game.GameMenu;
import view.Game.GraphicalGameMenu;
import view.fxmlMenu.GameLayout;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameGraphic extends Application {
    private static GameGraphic gameGraphic;
    private Pane mapPane;
    private Pane gamePane;
    private Scene gameScene;
    public static GameMap gameMap;
    private int mapSize;
    private MapFX mapFX;
    private Game game;
    private GameController gameController;

    private GraphicalGameMenu gameMenu;

    private boolean ctrlPressed = false;
    private boolean menuBarAction = false;
    private boolean dropBuilding = false;
    private boolean multiSelectingTiles = false;
    private boolean showInfo = false;

    GameLayout gameLayout;


    public GameGraphic(GameMap gameMap, int mapSize, Game game) {
        this.gameMap = gameMap;
        this.mapSize = mapSize;
        this.game = game;
        gameGraphic = this;
        gameLayout = new GameLayout();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("in the game graphics");


        //setup stage
//        primaryStage.setHeight(720);
//        primaryStage.setWidth(1240);

        System.out.println("setup stage");

        //setup panes
        gamePane = new Pane();
        gameLayout.setGameGraphic(this);
        gamePane.setPrefSize(1240, 720);
        mapPane = new Pane();
        Pane root = GameLayout.getFxmlRoot();
        root.getChildren().set(0, mapPane);
        gamePane.getChildren().add(root);
        System.out.println("setup panes");
        mapPane.setOnMouseClicked(event->{
            mapPane.requestFocus();
        });

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

    public String selectTiles(ArrayList<TileShape> tileShapes, MouseEvent event) {
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
        ArrayList<Tile> previousTiles = GameMenu.getSelectedTiles().get(game.getCurrentPlayer());
        for (Tile previousTile : previousTiles) {
            mapFX.getAllRecs()[previousTile.getRowNum()][previousTile.getColumnNum()].shape.setStroke(Color.GREEN);
            mapFX.getAllRecs()[previousTile.getRowNum()][previousTile.getColumnNum()].shape.setStrokeWidth(1);
        
        }

        Matcher matcher = ControllerFunctions.getMatcher(output, GameMenuCommands.SELECT_TILE.toString());
        GeneralGameController generalGameController = new GeneralGameController(gameMap, mapFX);
        generalGameController.selectTilesMatcherHandler(matcher, gameMenu, game.getCurrentPlayer());
//        System.out.println(generalGameController.showDetailsMultipleTiles(matcher));

        //showing details of selected tiles
        String details = generalGameController.showDetailsMultipleTiles(matcher);
        VBox vBox = new VBox();
        Label label = new Label(details);
        label.setStyle("-fx-background-color: white;");
        vBox.getChildren().add(label);
        vBox.setLayoutX(event.getX() - 1);
        vBox.setLayoutY(event.getY() - 1);
        mapPane.getChildren().add(vBox);
        vBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mapPane.getChildren().remove(vBox);
            }
        });
        return output;
    }

    public String selectBuilding(BuildingShape buildingShape) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLUE);
        dropShadow.setSpread(0.3);
        buildingShape.polygon.setEffect(dropShadow);

        //removing previous drop shadow
        Drawable prevBuilding = GameMenu.getSelected();
        System.out.println("prev building null? " + prevBuilding);
        if (prevBuilding instanceof Building) {
            ((Building) prevBuilding).getMapBuildingShape().polygon.setEffect(null);
        }


        Building building = buildingShape.getBuilding();
//        System.out.println("building type: " + building.getName());
//        System.out.println("building instance of inventory: " + (building instanceof Inventory));
//        System.out.println("x: " + building.getPosition().getRowNum() + " y " + building.getPosition().getColumnNum());
        String output = "select building -x " + (building.getPosition().getRowNum() + 1) + " -y " +
                (building.getPosition().getColumnNum() + 1);
        Matcher matcher = ControllerFunctions.getMatcher(output, GameMenuCommands.SELECT_BUILDING.toString());
        String result = gameController.selectBuilding(matcher, game.getCurrentPlayer(), gameMenu);
//        gameLayout.setLog(result);
        System.out.println(output);
        System.out.println(result);
        if (result.equals(Response.SUCCESSFUL_SELECT.getOutput())) {
            if (building instanceof Inventory) {
                System.out.println("inventory selected");
                GameLayout.currentInstance.changeMenuToFood(((Inventory) building).getType().getResource());
            }
            else if (building instanceof Barracks) {
                System.out.println("barrack selected");
                gameGraphic.gameLayout.changeMenuToBarracks(((Barracks) building).getType());
            }
        }
        return output;
    }

    public String dropBuilding(String command) {
        Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.DROP_BUILDING.toString());
        String output = gameController.dropBuildingMatcherHandler(matcher, game.getCurrentPlayer());
        System.out.println(output);
        return output;
    }

    public GameLayout getGameLayout() {
        return gameLayout;
    }

    public void setGameLayout(GameLayout gameLayout) {
        this.gameLayout = gameLayout;
    }

    public static GameGraphic getGameGraphic() {
        return gameGraphic;
    }

    public static void setGameGraphic(GameGraphic gameGraphic) {
        GameGraphic.gameGraphic = gameGraphic;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
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

    public boolean isDropBuilding() {
        return dropBuilding;
    }

    public void setDropBuilding(boolean dropBuilding) {
        this.dropBuilding = dropBuilding;
        mapFX.setDropBuilding(dropBuilding);
    }
}
