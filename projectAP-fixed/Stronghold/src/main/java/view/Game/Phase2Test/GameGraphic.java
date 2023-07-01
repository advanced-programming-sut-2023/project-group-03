package view.Game.Phase2Test;

import Model.Buildings.Barracks;
import Model.Buildings.Building;
import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Game;
import Model.Units.Enums.AttackingMode;
import Model.Units.Enums.UnitGraphics;
import Model.graphics.MapFX;
import Model.graphics.MapFX.BuildingShape;
import Model.graphics.MapFX.TileShape;
import controller.ControllerFunctions;
import controller.gameControllers.GameController;
import controller.gameControllers.GeneralGameController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.Enums.GameMenuCommands;
import view.Game.GraphicalGameMenu;
import view.Network.GameEvent;
import view.fxmlMenu.GameLayout;

import java.util.ArrayList;
import java.util.HashMap;
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

    private static GraphicalGameMenu gameMenu;

    private boolean ctrlPressed = false;
    private boolean menuBarAction = false;
    private boolean dropBuilding = false;
    private boolean multiSelectingTiles = false;
    private boolean showInfo = false;
    private boolean selectingMoveTarget = false;
    private boolean selectingTileToAttack = false;

    private static HashMap<String, Integer> unitsAmount = new HashMap<>();

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

                //unit shortcuts
                if (keyCode.equals(KeyCode.M) && ctrlPressed) {
                    setSelectingMoveTarget(true);
                }
                if (keyCode.equals(KeyCode.C) && ctrlPressed) {
                    getAttackingMode();
                }
                if (keyCode.equals(KeyCode.D) && ctrlPressed) {
                    disbandUnits();
                }
                if (keyCode.equals(KeyCode.A) && ctrlPressed) {
                    setSelectingTileToAttack(true);
                }



                //test
                if (keyCode.equals(KeyCode.Y)) {
                    HBox hBox = getChooseUnitBox();
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
        ArrayList<Tile> previousTiles = gameMenu.getSelectedTiles().get(game.getCurrentPlayer());
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
        Button button = new Button("select unit");
        Label label = new Label(details);
        label.setStyle("-fx-background-color: white;");

        vBox.getChildren().addAll(button, label);
        vBox.setLayoutX(event.getX() - 1);
        vBox.setLayoutY(event.getY() - 1);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getChooseUnitBox();
            }
        });

        mapPane.getChildren().add(vBox);
        vBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mapPane.getChildren().remove(vBox);
            }
        });
        return output;
    }

    public void repairBuilding(BuildingShape buildingShape) {
        Building building = gameMap.getMap()[buildingShape.getBuilding().getPosition().getRowNum()]
                [buildingShape.getBuilding().getPosition().getColumnNum()].getBuilding();
        System.out.println(building.getHP());
        String result = gameController.repair(building, game.getCurrentPlayer());
        System.out.println(result + " hp: " + building.getHP());
    }

    public void moveUnit(int x, int y) {
        String command = "move unit -x " + x + " -y " + y;
        Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.MOVE_UNIT.toString());
        String result = gameController.moveUnit(matcher, gameMenu);
        System.out.println(result);
    }

    public String selectBuilding(BuildingShape buildingShape) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLUE);
        dropShadow.setSpread(0.3);
        buildingShape.polygon.setEffect(dropShadow);

        //removing previous drop shadow
        Drawable prevBuilding = gameMenu.getSelected();
        if (prevBuilding instanceof Building) {
            Building building = (Building) prevBuilding;
            if (!building.isOnFire()) building.getMapBuildingShape().polygon.setEffect(null);
            else {
                dropShadow.setColor(Color.RED);
                building.getMapBuildingShape().polygon.setEffect(dropShadow);
            }
        }


        Building building = buildingShape.getBuilding();
//        System.out.println("building type: " + building.getName());
//        System.out.println("building instance of inventory: " + (building instanceof Inventory));
//        System.out.println("x: " + building.getPosition().getRowNum() + " y " + building.getPosition().getColumnNum());
        String command = "select building -x " + (building.getPosition().getRowNum() + 1) + " -y " +
                (building.getPosition().getColumnNum() + 1);
        Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.SELECT_BUILDING.toString());
        String result = gameController.selectBuilding(matcher, game.getCurrentPlayer(), gameMenu);
//        gameLayout.setLog(result);
        System.out.println(command);
        System.out.println(result);
//        if (result.equals(Response.SUCCESSFUL_SELECT.getOutput())) {
//            if (building instanceof Inventory) {
//                System.out.println("inventory selected");
//                GameLayout.currentInstance.changeMenuToFood(((Inventory) building).getType().getResource());
//            }
//            else if (building instanceof Barracks) {
//                System.out.println("barrack selected");
//                gameGraphic.gameLayout.changeMenuToBarracks(((Barracks) building).getType());
//            }
//        }
        return command;
    }

    public String dropBuilding(String command) {
        Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.DROP_BUILDING.toString());
        String output = gameController.dropBuildingMatcherHandler(matcher, game.getCurrentPlayer());
        System.out.println(output);
        return output;
    }

    public void dropUnit(String name, int amount) {
        if (name.split(" ").length > 1) name = "\"" + name + "\"";

        HBox hBox = new HBox();
        TextField xTextField = new TextField();
        xTextField.setPromptText("x location");
        TextField yTextField = new TextField();
        yTextField.setPromptText("y location");
        Button accept = new Button("accept");

        hBox.getChildren().addAll(xTextField, yTextField, accept);

        String finalName = name;
        accept.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String xString = xTextField.getText();
                String yString = yTextField.getText();
                gamePane.getChildren().remove(hBox);

                String command = "create unit -t " + finalName + " -c " + amount;

                command += " -x " + xString + " -y " + yString;
                Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.CREATE_UNIT.toString());
                Drawable building = gameMenu.getSelected();
                if (building instanceof Barracks) {
                    Barracks barracks = (Barracks) building;
                    String result = gameController.addUnitMatcherHandler(matcher, game.getCurrentPlayer(), barracks);
                    System.out.println(result);
                }
                else {
                    System.out.println("no barracks selected");
                }
            }
        });

        hBox.setLayoutX(gamePane.getWidth() / 2);
        hBox.setLayoutY(gamePane.getHeight() / 2);
        gamePane.getChildren().add(hBox);
    }

    public boolean isSelectingMoveTarget() {
        return selectingMoveTarget;
    }

    public void setSelectingMoveTarget(boolean selectingMoveTarget) {
        this.selectingMoveTarget = selectingMoveTarget;
        mapFX.setSelectingMoveTarget(selectingMoveTarget);
    }

    public HBox getChooseUnitBox() {
        HBox hBox = new HBox();
        ChoiceBox<String> unitTypes = new ChoiceBox<>();
        for (UnitGraphics unitGraphics : UnitGraphics.values()) {
            unitTypes.getItems().add(unitGraphics.name());
        }
        unitTypes.setValue("archer");

        unitsAmount = new HashMap<>();


        TextField amount = new TextField();
        amount.setPromptText("amount");

        Button select = new Button("select");
        Button add = new Button("add");
        Button cancel = new Button("cancel");

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gamePane.getChildren().remove(hBox);
            }
        });

        select.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String input = "";
                for (String key : unitsAmount.keySet()) {
                    input += key + "ali" + unitsAmount.get(key) + "ali";
                }

                gameController.selectUnitMultipleTiles(gameMenu.getSelectedTiles().get(game.getCurrentPlayer()),
                        input, game.getCurrentPlayer(), gameMenu);
                gamePane.getChildren().remove(hBox);

                System.out.println(gameMenu.getSelectedUnits().size());
            }
        });

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String unitType = unitTypes.getValue();
                String count = amount.getText();
                if (!count.matches("[0-9]+")) return;
                unitsAmount.put(unitType, Integer.parseInt(count));
                amount.setText("");
                amount.setPromptText("amount");
            }
        });

        hBox.getChildren().addAll(unitTypes, amount, add, select, cancel);
        hBox.setLayoutX(gamePane.getWidth() / 2);
        hBox.setLayoutY(gamePane.getHeight() / 2);
        gamePane.getChildren().add(hBox);

        return hBox;
    }

    public void getAttackingMode() {
        HBox hBox = new HBox();

        ChoiceBox<String> modes = new ChoiceBox<>();
        for (AttackingMode attackingMode : AttackingMode.values()) {
            modes.getItems().add(attackingMode.getName());
        }
        modes.setValue("standing");

        Button accept = new Button("accept");
        accept.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String command = "set " + modes.getValue();
                Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.SET.toString());
                String result = gameGraphic.gameController.setState(matcher, game.getCurrentPlayer(), gameMenu);
                System.out.println(result);
                gamePane.getChildren().remove(hBox);
            }
        });

        hBox.getChildren().addAll(modes, accept);
        hBox.setLayoutX(gamePane.getWidth() / 2);
        hBox.setLayoutY(gamePane.getHeight() / 2);

        gamePane.getChildren().add(hBox);
    }

    public void disbandUnits() {
        gameController.disbandUnit(gameMenu);
    }

    public void attackUnit(int x, int y) {
        String command = "attack place -x " + x + " -y " + y;
        Matcher matcher = ControllerFunctions.getMatcher(command, GameMenuCommands.ATTACK_PLACE.toString());
        String result = gameController.attackMatcherHandler(matcher, gameMenu);
        System.out.println(result);
    }

    //getters and setters


    public boolean isSelectingTileToAttack() {
        return selectingTileToAttack;
    }

    public void setSelectingTileToAttack(boolean selectingTileToAttack) {
        this.selectingTileToAttack = selectingTileToAttack;
        mapFX.setSelectingTileToAttack(true);
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
