package Model.graphics;

import Model.Buildings.Building;
import Model.Buildings.Enums.BuildingGraphics;
import Model.Buildings.Enums.TileGraphics;
import Model.Field.GameMap;
import Model.Field.Tile;
import Model.Units.Unit;
import graphicsTest.TestMap2;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;

public class MapFX {
    public class BuildingShape {
        int row;
        int col;
        double height;
        double iSize;
        double jSize;
        String image;
        Polygon polygon;

        public BuildingShape(Building building) {
            this.row = building.getPosition().getRowNum();
            this.col = building.getPosition().getColumnNum();
            this.height = building.getBuildingShape().getHeight();
            this.iSize = building.getBuildingShape().getWidth();
            this.jSize = building.getBuildingShape().getLength();
            this.image = building.getBuildingShape().getImageAddress();
            this.polygon = getHex(height, row, col, iSize, jSize, building.getBuildingShape().getBuildingImage());
        }

        public void updatePolygon (){
            this.polygon = new Polygon(
                    ((double)(row + iSize - col)) / iDivider * tileSize, ((double)(row + iSize + col)) / jDivider * tileSize,
                    ((double)(row - col + iSize - jSize)) / iDivider * tileSize, ((double)(row + col + iSize + jSize)) / jDivider * tileSize,
                    ((double)(row - col - jSize)) / iDivider * tileSize, ((double)(row + col + jSize)) / jDivider * tileSize,
                    ((double)(row - col - jSize)) / iDivider * tileSize, ((double)(row + col + jSize)) / jDivider * tileSize - height * tileSize,
                    ((double)(row - col)) / iDivider * tileSize, ((double)(row + col)) / jDivider * tileSize - height * tileSize,
                    ((double)(row + iSize - col)) / iDivider * tileSize, ((double)(row + iSize + col)) / jDivider * tileSize - height * tileSize
            );
            polygon.setFill(new ImagePattern(new Image(TestMap2.class.getResource("/images/buildings/" + image).toExternalForm())
                    , 0, 0, 1, 1, true));
            polygon.setStroke(Color.WHITE);;
        }
    }

    public class UnitShape {
        int height = 40;
        int width = 20;
        Rectangle shape = new Rectangle(20, 40);
        Unit unit;
        public UnitShape(Unit unit) {
            shape.setX(tilesCenters[unit.getPosition().getRowNum()][unit.getPosition().getColumnNum()][0]);
            shape.setY(tilesCenters[unit.getPosition().getRowNum()][unit.getPosition().getColumnNum()][1]);
        }

        public void updateShape() {
            shape.setX(tilesCenters[unit.getPosition().getRowNum()][unit.getPosition().getColumnNum()][0]);
            shape.setY(tilesCenters[unit.getPosition().getRowNum()][unit.getPosition().getColumnNum()][1]);
        }

    }

    public class TileShape {
        Tile tile;
        Polygon shape;
        public TileShape(Tile tile) {
            this.tile = tile;

        }
    }

    public Polygon getHex(double height, int i, int j, double iSize, double jSize, Image image) {
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
    private int tileSizeDelta;
    private int cameraRowSize = 60;
    private int cameraColSize = 60;
    double verticalCameraMove = 2 / jDivider * tileSize;
    double horizontalCameraMove = 2 / iDivider * tileSize;
    private Polygon view;
    private GameMap gameMap;
    private HashSet<Polygon> currentTiles = new HashSet<>();
    private Pane mapPane;
    private TileShape[][] allRecs = new TileShape[rowSize][colSize];
    private double[][][] tilesCenters;
    ArrayList<BuildingShape> buildings = new ArrayList<>();
    ArrayList<UnitShape> units = new ArrayList<>();

    public MapFX(int size, Pane mapPane, Stage stage, GameMap gameMap) {
        this.rowSize = size;
        this.colSize = size;
        this.mapPane = mapPane;
        this.gameMap = gameMap;

        //setup camera
        view = new Polygon(
                -stage.getWidth(), 0f,
                stage.getWidth(), 0f,
                stage.getWidth(), stage.getHeight(),
                -stage.getWidth(), stage.getHeight()
        );
        System.out.println(view.getPoints());
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
        for (Polygon polygon : currentTiles) {
            mapPane.getChildren().remove(polygon);
        }
        currentTiles.clear();
        for (int x = 0; x < rowSize; x++) {
            for (int y = 0; y < colSize; y++) {
                Polygon polygon = allRecs[x][y].shape;
                if (polygon.getBoundsInParent().intersects(view.getBoundsInParent())) {
                    mapPane.getChildren().add(polygon);
                    currentTiles.add(polygon);
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

    private void changeZoom(boolean plus) {
        if (plus && tileSize >= 100) return;
        if (!plus && tileSize <= 40) return;

        if (plus) tileSize += tileSizeDelta;
        else tileSize -= tileSizeDelta;
        mapPane.getChildren().clear();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                allRecs[i][j] = new TileShape(null);
            }
        }

        verticalCameraMove = (double) 2 / jDivider * tileSize;
        horizontalCameraMove = (double) 2 / iDivider * tileSize;

        for (BuildingShape building : buildings) {
            building.updatePolygon();
        }

        //todo for units
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
                Polygon tileShape = new Polygon(
                        ((double)(i + 1 - j)) / iDivider * tileSize, ((double)(i + 1 + j)) / jDivider * tileSize,
                        ((double)(i - j)) / iDivider * tileSize, ((double)(i + j)) / jDivider * tileSize,
                        ((double)(i - j - 1)) / iDivider * tileSize, ((double)(i + j + 1)) / jDivider * tileSize,
                        ((double)(i - j)) / iDivider * tileSize, ((double)(i + j + 2)) / jDivider * tileSize
                );
                //update tilesCenters
                tilesCenters[i][j][0] = tileShape.getPoints().get(0) / 2 + tileShape.getPoints().get(4) / 2;
                tilesCenters[i][j][1] = tileShape.getPoints().get(3) / 2 + tileShape.getPoints().get(7) / 2;

                Image tileImage = null;
                try {
                    tileImage = TileGraphics.getTileGraphicByName(gameMap.getMap()[i][j].getTexture().getName()).getTileImage();
                } catch (Exception e) {
                    //todo
                    System.out.println(gameMap.getMap()[i][j] == null);
                    throw new RuntimeException(e);
                }
                tileShape.setFill(new ImagePattern(tileImage));
                tileShape.setOpacity(0.8);
                allRecs[i][j].shape = tileShape;
                tileShape.setStroke(Color.GREEN);//todo
            }
        }
        updateCamera(); 
    }

    public void moveMapWithKeys(KeyEvent keyEvent) {
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

}
