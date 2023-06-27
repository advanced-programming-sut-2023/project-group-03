package Model.graphics;

import Model.Buildings.Building;
import Model.Buildings.Enums.TileGraphics;
import Model.Field.GameMap;
import graphicsTest.TestMap2;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
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
        Pane mapPane;


        public BuildingShape(Building building, Pane mapPane) {
            this.row = building.getPosition().getRowNum();
            this.col = building.getPosition().getColumnNum();
            this.height = building.getBuildingShape().getHeight();
            this.iSize = building.getBuildingShape().getWidth();
            this.jSize = building.getBuildingShape().getLength();
            this.image = building.getBuildingShape().getImageAddress();
            this.mapPane = mapPane;
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
    private int iDivider;
    private int jDivider;
    private int tileSize;
    private int tileSizeDelta;
    private int cameraRowSize = 60;
    private int cameraColSize = 60;
    double verticalCameraMove = 2 / jDivider * tileSize;
    double horizontalCameraMove = 2 / iDivider * tileSize;
    private Polygon view;
    private GameMap gameMap;
    private HashSet<Polygon> currentTiles = new HashSet<>();
    private Pane mapPane;
    private Polygon[][] allRecs = new Polygon[rowSize][colSize];
    ArrayList<BuildingShape> buildings = new ArrayList<>();

    public MapFX(int size, Pane mapPane, Stage stage) {
        this.rowSize = size;
        this.colSize = size;
        this.mapPane = mapPane;
        view = new Polygon(
                -stage.getWidth(), 0f,
                stage.getWidth(), 0f,
                stage.getWidth(), stage.getHeight(),
                -stage.getWidth(), stage.getHeight()
        );
        view.setFill(Color.TRANSPARENT);
        mapPane.getChildren().add(view);//todo
        view.setLayoutY(0);
        view.setLayoutX(0);
    }

    private void updateCamera() {
        for (Polygon polygon : currentTiles) {
            mapPane.getChildren().remove(polygon);
        }
        currentTiles.clear();
        for (int x = 0; x < rowSize; x++) {
            for (int y = 0; y < colSize; y++) {
                Polygon polygon = allRecs[x][y];
                if (polygon.getBoundsInParent().intersects(view.getBoundsInParent())) {
                    mapPane.getChildren().add(polygon);
                    currentTiles.add(polygon);
                }
            }
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
        allRecs = new Polygon[rowSize][colSize];
        updateAllRecs();

        verticalCameraMove = 2 / jDivider * tileSize;
        horizontalCameraMove = 2 / iDivider * tileSize;

        for (BuildingShape building : buildings) {
            building.updatePolygon();
        }

        updateCamera();
    }

    public void moveCameraWithPosition(int row, int col) {
        view.setLayoutY(allRecs[row][col].getPoints().get(1));
        mapPane.setLayoutY(-allRecs[row][col].getPoints().get(1));
        view.setLayoutX(allRecs[row][col].getPoints().get(0));
        mapPane.setLayoutX(-allRecs[row][col].getPoints().get(0));
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
                Image tileImage = TileGraphics.getTileGraphicByName(gameMap.getMap()[i][j].getTexture().getName()).getTileImage();
                tileShape.setFill(new ImagePattern(tileImage));
                tileShape.setOpacity(0.8);
                allRecs[i][j] = tileShape;
                tileShape.setStroke(Color.GREEN);//todo
            }
        }
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

}
