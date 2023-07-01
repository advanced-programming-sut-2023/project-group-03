package Model.graphics;

import Model.Buildings.Building;
import Model.Buildings.Enums.BuildingGraphics;
import Model.Buildings.Enums.TileGraphics;
import Model.Buildings.Keep;
import Model.Field.GameMap;
import Model.Field.Tile;
import Model.Units.Enums.UnitGraphics;
import controller.ControllerFunctions;
import controller.gameControllers.GeneralGameController;
import Model.Units.Unit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import view.Enums.GameMenuCommands;
import view.Game.Phase2Test.GameGraphic;
import view.fxmlMenu.GameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;

public class MapFX {
//    @FunctionalInterface
//    public interface HelpInterface {
//
//    }
    public static class BuildingShape {
        private Building building;
        int row;
        int col;
        double height;
        double iSize;
        double jSize;
        String image;
        public Polygon polygon;
        MapFX mapFX;
        public VBox infoBox;

        public BuildingShape(Building building, MapFX mapFX) {
            this.mapFX = mapFX;
            this.building = building;
            this.row = building.getPosition().getRowNum();
            this.col = building.getPosition().getColumnNum();
            this.height = building.getBuildingShape().getHeight();
            this.iSize = building.getBuildingShape().getWidth();
            this.jSize = building.getBuildingShape().getLength();
            this.image = building.getBuildingShape().getImageAddress();
            this.polygon = getHex(height, row, col, iSize, jSize, building.getBuildingShape().getBuildingImage()
                    , mapFX.iDivider, mapFX.jDivider, mapFX.tileSize);

            mapFX.buildings.add(this);
            mapFX.mapPane.getChildren().add(polygon);
            BuildingShape current = this;
            updatePolygonActions(polygon, this);
        }

        public void removeBuildingShape() {
            mapFX.buildings.remove(this);
            mapFX.mapPane.getChildren().remove(polygon);
        }

        public void updatePolygon (){
            this.polygon = getHex(height, row, col, iSize, jSize, building.getBuildingShape().getBuildingImage()
                    , mapFX.iDivider, mapFX.jDivider, mapFX.tileSize);

            updatePolygonActions(polygon, this);
        }

        private void updatePolygonActions(Polygon polygon, BuildingShape buildingShape) {
            polygon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!mapFX.menuBarAction) mapFX.gameGraphic.selectBuilding(buildingShape);
                }
            });

            polygon.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    infoBox = new VBox();
                    String output = "";
                    Building currentBuilding = GameGraphic.gameMap.getMap()
                            [building.getPosition().getRowNum()][building.getPosition().getColumnNum()].getBuilding();
                    output += "type: " + currentBuilding.getName() + "\nHP: " + currentBuilding.getHP();
                    Label label = new Label(output);
                    label.setStyle("-fx-background-color: white;");

                    Button repairButton = new Button("repair");
                    repairButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            mapFX.gameGraphic.repairBuilding(buildingShape);
                        }
                    });
                    Button removeButton = new Button("remove");
                    removeButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            building.setHP(-1);
                            building.shouldBreak();
                        }
                    });
                    HBox buttons = new HBox();
                    buttons.getChildren().addAll(removeButton, repairButton);

                    infoBox.getChildren().addAll(label, buttons);
                    infoBox.setLayoutX(mapFX.tilesCenters[building.getPosition().getRowNum()][building.getPosition().getColumnNum()][0]);
                    infoBox.setLayoutY(mapFX.tilesCenters[building.getPosition().getRowNum()][building.getPosition().getColumnNum()][1]);
                    mapFX.mapPane.getChildren().add(infoBox);
                    infoBox.setOnMouseExited(infoBoxEvent -> {
                        mapFX.mapPane.getChildren().remove(infoBox);
                    });
                }
            });
        }

        public Building getBuilding() {
            return building;
        }
    }

    public static class UnitShape {
        int height = 40;
        int width = 20;
        MapFX mapFX;
        public Rectangle shape = new Rectangle(20, 40);
        Unit unit;
        public UnitShape(Unit unit, MapFX mapFX) {
            shape.setX(mapFX.tilesCenters[unit.getPosition().getRowNum()][unit.getPosition().getColumnNum()][0] - shape.getWidth() / 2);
            shape.setY(mapFX.tilesCenters[unit.getPosition().getRowNum()][unit.getPosition().getColumnNum()][1] - shape.getHeight() / 2);
            shape.setFill(new ImagePattern(UnitGraphics.getUnitGraphicsByName(unit.getName()).getImages().get(0)));
            this.mapFX = mapFX;
            mapFX.units.add(this);
            Building building = mapFX.allRecs[unit.getPosition().getRowNum()][unit.getPosition().getColumnNum()].tile.getBuilding();
            if (building != null && !(building instanceof Keep)) {
                BuildingShape buildingShape = building.getMapBuildingShape();
                mapFX.mapPane.getChildren().remove(buildingShape.polygon);
                mapFX.mapPane.getChildren().add(shape);
                mapFX.mapPane.getChildren().add(buildingShape.polygon);
            }
        }

        public void removeUnitShape() {
            mapFX.units.remove(this);
            mapFX.mapPane.getChildren().remove(shape);
        }

        public void updateShape() {
            shape.setX(mapFX.tilesCenters[unit.getPosition().getRowNum()][unit.getPosition().getColumnNum()][0] - shape.getWidth() / 2);
            shape.setY(mapFX.tilesCenters[unit.getPosition().getRowNum()][unit.getPosition().getColumnNum()][1] - shape.getHeight() / 2);
        }

    }

    public static class TileShape {
        private Tile tile;
        public Polygon shape;
        public ScrollPane infoBox;
        public boolean onInfoBox = false;
        public TileShape(Tile tile) {
            this.tile = tile;
        }
        public Tile getTile() {
            return tile;
        }
    }

    public static Polygon getHex(double height, int i, int j, double iSize, double jSize, Image image, double iDivider, double jDivider, int tileSize) {
        i -= Math.floor(iSize / 2);
        j -= Math.floor(jSize / 2);
        Polygon shape = new Polygon(
                ((double)(i + iSize - j)) / iDivider * tileSize, ((double)(i + iSize + j)) / jDivider * tileSize,
                ((double)(i - j + iSize - jSize)) / iDivider * tileSize, ((double)(i + j + iSize + jSize)) / jDivider * tileSize,
                ((double)(i - j - jSize)) / iDivider * tileSize, ((double)(i + j + jSize)) / jDivider * tileSize,
                ((double)(i - j - jSize)) / iDivider * tileSize, ((double)(i + j + jSize)) / jDivider * tileSize - height * tileSize,
                ((double)(i - j)) / iDivider * tileSize, ((double)(i + j)) / jDivider * tileSize - height * tileSize,
                ((double)(i + iSize - j)) / iDivider * tileSize, ((double)(i + iSize + j)) / jDivider * tileSize - height * tileSize
        );
        shape.setFill(new ImagePattern(image
                , 0, 0, 1, 1, true));
        shape.setStroke(Color.WHITE);
//        shape.setFill(Color.BROWN);
//        pane.getChildren().add(shape);
        //rotate
//        shape.setRotationAxis(Rotate.Y_AXIS);
//        shape.setRotate(180);
        return shape;
    }

    private int rowSize;
    private int colSize;
    private double iDivider = 2;
    private double jDivider = 3.5;
    private int tileSize = 80;
    private int tileSizeDelta = 20;
    private int cameraRowSize = 60;
    private int cameraColSize = 60;
    double verticalCameraMove = 2 / jDivider * tileSize;
    double horizontalCameraMove = 2 / iDivider * tileSize;
    private Polygon view;
    private GameMap gameMap;
    private HashSet<TileShape> currentTiles = new HashSet<>();
    private Pane mapPane;
    private TileShape[][] allRecs = new TileShape[rowSize][colSize];
    private double[][][] tilesCenters;
    //booleans
    private boolean menuBarAction = false;
    private boolean dropBuilding = false;
    private boolean multiSelectingTiles = false;
    private boolean showInfo = false;
    private boolean selectingMoveTarget = false;
    private boolean selectingTileToAttack = false;

    //mouse event for multiSelecting
    MouseEvent event;

    //dragging
    private boolean dragging = false;
    private double dragXStart;
    private double dragXFinish;
    private double dragYStart;
    private double dragYFinish;

    //multiSelecting
    ArrayList<TileShape> multiSelectedTiles = new ArrayList<>();

    ArrayList<BuildingShape> buildings = new ArrayList<>();
    ArrayList<UnitShape> units = new ArrayList<>();

    private GameGraphic gameGraphic;

    public MapFX(int size, Pane mapPane, Stage stage, GameMap gameMap) {
        this.rowSize = size;
        this.colSize = size;
        this.mapPane = mapPane;
        this.gameMap = gameMap;
        int cameraWidth = 1240;
        int cameraHeight = 720;

        //setup camera
        view = new Polygon(
                -cameraHeight, 0f,
                cameraWidth, 0f,
                cameraWidth, cameraHeight,
                -cameraWidth, cameraHeight
        );
        view.setFill(Color.TRANSPARENT);
        mapPane.getChildren().add(view);//todo

        view.setLayoutY(0);
        view.setLayoutX(0);

        double x1 = view.getPoints().get(0);
        double y1 = view.getPoints().get(1);
        mapPane.setLayoutX(x1);
        mapPane.setLayoutY(-y1);
        mapPane.setLayoutX(stage.getWidth() / 2);
        mapPane.setLayoutY(0);

        //centers
        tilesCenters = new double[size][size][2];

        //setup allRecs
        allRecs = new TileShape[size][size];
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                allRecs[i][j] = new TileShape(gameMap.getMap()[i][j]);
            }
        }
        updateAllRecs();
//        mapPane = new VBox();
//        mapPane.getChildren().add(new Button("you were in mapfx"));
//        stage.setScene(new Scene(mapPane));
//        mapPane.setStyle("-fx-background-color: black;");
    }

    public double[][][] getTilesCenters() {
        return tilesCenters;
    }

    private void updateCamera() {
        for (TileShape tileShape : currentTiles) {
            Polygon polygon = tileShape.shape;
            mapPane.getChildren().remove(polygon);
            mapPane.getChildren().remove(tileShape.infoBox);
        }
        currentTiles.clear();
        for (int x = 0; x < rowSize; x++) {
            for (int y = 0; y < colSize; y++) {
                Polygon polygon = allRecs[x][y].shape;
                if (polygon.getBoundsInParent().intersects(view.getBoundsInParent())) {
                    mapPane.getChildren().add(polygon);
                    currentTiles.add(allRecs[x][y]);
                }
            }
        }
        for (UnitShape unit : units) {
            Rectangle shape = unit.shape;
            mapPane.getChildren().remove(shape);
            mapPane.getChildren().add(shape);
        }
        for (BuildingShape building : buildings) {
            Polygon polygon = building.polygon;
            mapPane.getChildren().remove(polygon);
            mapPane.getChildren().add(polygon);
        }
    }

    public void changeZoom(boolean plus) {
        if (plus && tileSize >= 100) return;
        if (!plus && tileSize <= 40) return;

        if (plus) tileSize += tileSizeDelta;
        else tileSize -= tileSizeDelta;
        mapPane.getChildren().clear();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                allRecs[i][j] = new TileShape(gameMap.getMap()[i][j]);
            }
        }

        verticalCameraMove = (double) 2 / jDivider * tileSize;
        horizontalCameraMove = (double) 2 / iDivider * tileSize;

        for (BuildingShape building : buildings) {
            building.updatePolygon();
        }

        for (UnitShape unitShape : units) {
            unitShape.updateShape();
        }

        updateAllRecs();
    }

    public void moveCameraWithPosition(int row, int col) {
        view.setLayoutY(allRecs[row][col].shape.getPoints().get(1));
        mapPane.setLayoutY(-allRecs[row][col].shape.getPoints().get(1));
        view.setLayoutX(allRecs[row][col].shape.getPoints().get(0));
        mapPane.setLayoutX(-allRecs[row][col].shape.getPoints().get(0));
        updateCamera();
    }

    private void updateAllRecs() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                Polygon tilePolygon = new Polygon(
                        ((double)(i + 1 - j)) / iDivider * tileSize, ((double)(i + 1 + j)) / jDivider * tileSize,
                        ((double)(i - j)) / iDivider * tileSize, ((double)(i + j)) / jDivider * tileSize,
                        ((double)(i - j - 1)) / iDivider * tileSize, ((double)(i + j + 1)) / jDivider * tileSize,
                        ((double)(i - j)) / iDivider * tileSize, ((double)(i + j + 2)) / jDivider * tileSize
                );
                //update tilesCenters
                tilesCenters[i][j][0] = tilePolygon.getPoints().get(0) / 2 + tilePolygon.getPoints().get(4) / 2;
                tilesCenters[i][j][1] = tilePolygon.getPoints().get(3) / 2 + tilePolygon.getPoints().get(7) / 2;

                Image tileImage = null;
                try {
                    tileImage = TileGraphics.getTileGraphicByName(gameMap.getMap()[i][j].getTexture().getName()).getTileImage();
                } catch (Exception e) {
                    System.out.println(gameMap.getMap()[i][j] == null);
                    throw new RuntimeException(e);
                }
                tilePolygon.setFill(new ImagePattern(tileImage));
//                tilePolygon.setOpacity(0.8);
                allRecs[i][j].shape = tilePolygon;
                tilePolygon.setStroke(Color.GREEN);//todo

                //set on clicked for tile
                TileShape mapTileShape = allRecs[i][j];
                MapFX current = this;
                tilePolygon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (!multiSelectingTiles && !menuBarAction && !dropBuilding && !dragging && !selectingMoveTarget && !selectingTileToAttack) {
                            gameGraphic.selectTiles(new ArrayList<>(Arrays.asList(mapTileShape)), event);
                        }
                        if (multiSelectingTiles && !menuBarAction && !selectingMoveTarget && !selectingTileToAttack) {
                            multiSelectedTiles.add(mapTileShape);
                            current.event = event;
                        }
                        if (selectingMoveTarget && !multiSelectingTiles && !selectingTileToAttack) {
                            int x = mapTileShape.getTile().getRowNum();
                            int y = mapTileShape.getTile().getColumnNum();
                            gameGraphic.setSelectingMoveTarget(false);
                            selectingMoveTarget = false;
                            gameGraphic.moveUnit(x, y);
                        }
                        if (selectingTileToAttack && !multiSelectingTiles) {
                            int x = mapTileShape.getTile().getRowNum();
                            int y = mapTileShape.getTile().getColumnNum();
                            gameGraphic.setSelectingTileToAttack(false);
                            selectingTileToAttack = false;
                            gameGraphic.attackUnit(x, y);
                        }
                    }
                });

                tilePolygon.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //shit
                        if (!multiSelectingTiles && dropBuilding && menuBarAction) {
                            int row = mapTileShape.getTile().getRowNum();
                            int col = mapTileShape.getTile().getColumnNum();
                            dropBuilding = false;
                            menuBarAction = false;
                            BuildingGraphics buildingGraphics = GameLayout.currentAtomicBuildingGraphics.get();
                            String buildingName = buildingGraphics.getName();
                            if (buildingName.split(" ").length > 1) buildingName = "\"" + buildingName + "\"";
                            String command = "drop building -x " + (row + 1) + " -y " + (col + 1) + " -t " + buildingName;
                            GameGraphic.getGameGraphic().dropBuilding(command);
                            //todo gate
                        }
                    }
                });
                final int finali = i;
                final int finalj = j;

                tilePolygon.hoverProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue && showInfo) {
                        String command = "show details -x " + (finali + 1) + " -y " + (finalj + 1);
                        Matcher matcher = ControllerFunctions.getMatcher(command , GameMenuCommands.SHOW_DETAILS.toString());
                        VBox tempBox = new VBox();
                        Label label = new Label(new GeneralGameController(gameMap, this).showDetails(matcher));
                        label.setStyle("-fx-background-color: white;");
                        if (showInfo) tempBox.getChildren().add(
                                label
                        );

                        ScrollPane scrollPane = new ScrollPane();
                        mapTileShape.infoBox = scrollPane;

                        scrollPane.setPrefSize(300, 100);
                        scrollPane.setContent(tempBox);

//                        scrollPane.setLayoutX(tilePolygon.getPoints().get(0) / 2 + tilePolygon.getPoints().get(4) / 2);
//                        scrollPane.setLayoutY(tilePolygon.getPoints().get(3) / 2 + tilePolygon.getPoints().get(7) / 2);

//                        tempBox.setLayoutX(tilePolygon.getPoints().get(6));
//                        tempBox.setLayoutY(tilePolygon.getPoints().get(7) - 1);

                        scrollPane.setLayoutX(tilePolygon.getPoints().get(4));
                        scrollPane.setLayoutY(tilePolygon.getPoints().get(3));
                        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                tempBox.setVisible(false);
                            }
                        });

                        if (!mapPane.getChildren().contains(scrollPane)) mapPane.getChildren().add(scrollPane);
                        mapTileShape.onInfoBox = false;
                        scrollPane.hoverProperty().addListener((observable1, oldValue1, newValue1) -> {
                            if (!newValue1) mapPane.getChildren().remove(scrollPane);
                        });
                    }
                });
            }
        }
        updateCamera();
    }

    public void update(TileShape lastTileShape,Polygon tileShape) {
        tileShape.setOnMouseReleased(event -> {
            if (!multiSelectingTiles && dropBuilding && menuBarAction) {
                int row = lastTileShape.getTile().getRowNum();
                int col = lastTileShape.getTile().getColumnNum();
                dropBuilding = false;
                menuBarAction = false;
                BuildingGraphics buildingGraphics = gameGraphic.getGameLayout().currentbuildingGraphics;
                String buildingName = buildingGraphics.getName();
                String command = "drop building -x " + row + " -y " + col + " -t " + buildingName;
                gameGraphic.dropBuilding(command);
            }
        });
    }

    public void moveMapWithKeys(KeyEvent keyEvent) {
        if (showInfo) return;//when showing info, not update camera
        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            mapPane.setLayoutX(mapPane.getLayoutX() + horizontalCameraMove);
            view.setLayoutX(view.getLayoutX() - horizontalCameraMove);
            updateCamera();
        } else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            mapPane.setLayoutX(mapPane.getLayoutX() - horizontalCameraMove);
            view.setLayoutX(view.getLayoutX() + horizontalCameraMove);
            updateCamera();
        } else if (keyEvent.getCode().equals(KeyCode.UP)) {
            mapPane.setLayoutY(mapPane.getLayoutY() + verticalCameraMove);
            view.setLayoutY(view.getLayoutY() - verticalCameraMove);
            updateCamera();

        } else if (keyEvent.getCode().equals(KeyCode.DOWN)) {
            mapPane.setLayoutY(mapPane.getLayoutY() - verticalCameraMove);
            view.setLayoutY(view.getLayoutY() + verticalCameraMove);
            updateCamera();
        }
    }

    public void startDragging(MouseEvent event) {
        if (!dragging && !multiSelectingTiles && !menuBarAction) {
            dragXStart = event.getX();
            dragYStart = event.getY();
            dragging = true;
        }
    }

    public void mouseReleasedHandler(MouseEvent event) {
        if (dragging && !multiSelectingTiles && !menuBarAction) {
            dragging = false;
            dragXFinish = event.getX();
            dragYFinish = event.getY();
            Polygon dragRect = new Polygon(dragXStart, dragYStart, dragXStart, dragYFinish, dragXFinish, dragYFinish, dragXFinish, dragYStart);
            mapPane.getChildren().add(dragRect);
            dragRect.setFill(Color.TRANSPARENT);

            //sending selected tiles to mapFX
            ArrayList<TileShape> selectedTiles = new ArrayList<>();
            for (TileShape shape : currentTiles) {
                Polygon tile = shape.shape;
                if (tile.getBoundsInParent().intersects(dragRect.getBoundsInParent())) {
                    selectedTiles.add(shape);
                }
            }
            getGameGraphic().selectTiles(selectedTiles, event);
        }

        // else if (!dragging && multiSelectingTiles && !menuBarAction) {
        //     gameGraphic.setMultiSelectingTiles(false);
        //     getGameGraphic().selectTiles(multiSelectedTiles);
        //     multiSelectedTiles.clear();
        // }
    }

    public void removeBuildings() {
        for (BuildingShape buildingShape : buildings) {
            mapPane.getChildren().remove(buildingShape.polygon);
        }
    }

    public void addAllBuildings() {
        for (BuildingShape buildingShape : buildings) {
            mapPane.getChildren().add(buildingShape.polygon);
        }
    }


    //getters and setters


    public boolean isSelectingTileToAttack() {
        return selectingTileToAttack;
    }

    public void setSelectingTileToAttack(boolean selectingTileToAttack) {
        this.selectingTileToAttack = selectingTileToAttack;
    }

    public boolean isSelectingMoveTarget() {
        return selectingMoveTarget;
    }

    public void setSelectingMoveTarget(boolean selectingMoveTarget) {
        this.selectingMoveTarget = selectingMoveTarget;
    }

    public boolean isShowInfo() {
        return showInfo;
    }

    public void setShowInfo(boolean showInfo) {
        this.showInfo = showInfo;
    }

    public boolean isDropBuilding() {
        return dropBuilding;
    }

    public void setDropBuilding(boolean dropBuilding) {
        this.dropBuilding = dropBuilding;
    }

    public GameGraphic getGameGraphic() {
        return gameGraphic;
    }

    public void setGameGraphic(GameGraphic gameGraphic) {
        this.gameGraphic = gameGraphic;
    }

    public boolean isMenuBarAction() {
        return menuBarAction;
    }

    public void setMenuBarAction(boolean menuBarAction) {
        this.menuBarAction = menuBarAction;
    }

    public boolean isMultiSelectingTiles() {
        return multiSelectingTiles;
    }

    public void setMultiSelectingTiles(boolean multiSelectingTiles) {
        this.multiSelectingTiles = multiSelectingTiles;
        if (!multiSelectingTiles) {
            if (event == null) {
                System.out.println("mouse event was null");
                return;
            }
            gameGraphic.selectTiles(multiSelectedTiles, event);
            multiSelectedTiles.clear();
        }
    }

    public int getRowSize() {
        return rowSize;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public double getiDivider() {
        return iDivider;
    }

    public void setiDivider(double iDivider) {
        this.iDivider = iDivider;
    }

    public double getjDivider() {
        return jDivider;
    }

    public void setjDivider(double jDivider) {
        this.jDivider = jDivider;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public TileShape[][] getAllRecs() {
        return allRecs;
    }

    public void setAllRecs(TileShape[][] allRecs) {
        this.allRecs = allRecs;
    }

    public void setTilesCenters(double[][][] tilesCenters) {
        this.tilesCenters = tilesCenters;
    }
}
