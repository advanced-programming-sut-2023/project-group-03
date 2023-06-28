package graphicsTest;

import Model.Buildings.Enums.BuildingGraphics;
import Model.Field.Tile;
import Model.Units.Unit;
import Model.graphics.MapFX;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;

public class TestMap2 extends Application {
    public class BuildingShape {
        int row;
        int col;
        double height;
        double iSize;
        double jSize;
        String image;
        Polygon polygon;
        Pane mapPane;

        public BuildingShape(int row, int col, double height, double iSize, double jSize, String image, Pane mapPane, Polygon polygon) {
            this.row = row;
            this.col = col;
            this.height = height;
            this.iSize = iSize;
            this.jSize = jSize;
            this.image = image;
            this.mapPane = mapPane;
            this.polygon = polygon;
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
        int row;
        int col;
        int height = 40;
        int width = 20;
        Rectangle shape = new Rectangle(20, 40);
        Unit unit;

    }

    public class TileShape {
        Tile tile;
        Polygon shape;
        public TileShape(Tile tile) {
            this.tile = tile;

        }
    }
    int tileSize = 80;
    int tileSizeDelta = 20;
    int rowSize = 125;
    int colSize = 125;
    int halfSatr = rowSize / 2;
    int halfSotoon = colSize / 2;
    double iDivider = 2;
    double jDivider = 3.5;
    int[] currentTile = {0, 0};
    double verticalCameraMove = 2 / jDivider * tileSize;
    double horizontalCameraMove = 2 / iDivider * tileSize;
    int cameraRowSize = 60;
    int cameraColSize = 60;
    boolean isCtrlPressed = false;
    ImagePattern imagePattern = new ImagePattern(new Image(TestMap2.class.getResource("/images/tiles/ground.jpg").toExternalForm()));

    HashSet<Polygon> currentTiles = new HashSet<>();
    TileShape[][] allRecs = new TileShape[rowSize][colSize];
    ArrayList<BuildingShape> buildings = new ArrayList<>();
    ArrayList<UnitShape> units = new ArrayList<>();
    Polygon view;
    Pane mapPane = new Pane();
    double dragXStart;
    double dragXFinish;
    double dragYStart;
    double dragYFinish;
    boolean isDragging;

    public Polygon getHex(double height, int i, int j, double iSize, double jSize, String image, Pane pane) {
        Polygon thing = new Polygon(
                ((double)(i + iSize - j)) / iDivider * tileSize, ((double)(i + iSize + j)) / jDivider * tileSize,
                ((double)(i - j + iSize - jSize)) / iDivider * tileSize, ((double)(i + j + iSize + jSize)) / jDivider * tileSize,
                ((double)(i - j - jSize)) / iDivider * tileSize, ((double)(i + j + jSize)) / jDivider * tileSize,
                ((double)(i - j - jSize)) / iDivider * tileSize, ((double)(i + j + jSize)) / jDivider * tileSize - height * tileSize,
                ((double)(i - j)) / iDivider * tileSize, ((double)(i + j)) / jDivider * tileSize - height * tileSize,
                ((double)(i + iSize - j)) / iDivider * tileSize, ((double)(i + iSize + j)) / jDivider * tileSize - height * tileSize
                );
        thing.setFill(new ImagePattern(new Image(TestMap2.class.getResource("/images/buildings/" + image).toExternalForm())
        , 0, 0, 1, 1, true));
        thing.setStroke(Color.WHITE);
//        thing.setFill(Color.BROWN);

        Polygon thing2 = new Polygon(
                ((double)(i + iSize - j)) / iDivider * tileSize, ((double)(i + iSize + j)) / jDivider * tileSize,
                ((double)(i - j + iSize - jSize)) / iDivider * tileSize, ((double)(i + j + iSize + jSize)) / jDivider * tileSize,
                ((double)(i - j - jSize)) / iDivider * tileSize, ((double)(i + j + jSize)) / jDivider * tileSize,
                ((double)(i - j - jSize)) / iDivider * tileSize, ((double)(i + j + jSize)) / jDivider * tileSize - height * tileSize,
                ((double)(i - j)) / iDivider * tileSize, ((double)(i + j)) / jDivider * tileSize - height * tileSize,
                ((double)(i + iSize - j)) / iDivider * tileSize, ((double)(i + iSize + j)) / jDivider * tileSize - height * tileSize
        );

        thing2.setFill(Color.color(192f / 254, 157f / 255, 94 / 255f));
//        pane.getChildren().add(thing2);
        pane.getChildren().add(thing);
        //rotate
//        thing.setRotationAxis(Rotate.Y_AXIS);
//        thing.setRotate(180);

        buildings.add(new BuildingShape(i, j, height, iSize, jSize, image, mapPane, thing));
        return thing;
    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setHeight(720);
        stage.setWidth(1240);
//        stage.setResizable(false);
//        stage.setMaximized(true);
//        System.out.println(stage.getHeight() + " " + stage.getWidth());
        Pane pane = new Pane();

<<<<<<< HEAD
=======
        //camera setup
        view = new Polygon(
                -stage.getWidth(), 0f,
                stage.getWidth(), 0f,
                stage.getWidth(), stage.getHeight(),
                -stage.getWidth(), stage.getHeight()
        );
        view.setFill(Color.TRANSPARENT);
        view.setOpacity(0.5);
        mapPane.getChildren().add(view);//todo
        view.setLayoutY(0);
        view.setLayoutX(0);

>>>>>>> phase2GraphicsAli
        //setup allRecs
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                allRecs[i][j] = new TileShape(null);
            }
        }
        updateAllRecs();

        for (int x = currentTile[0]; x < currentTile[0] + cameraRowSize; x++) {
            for (int y = currentTile[1]; y < currentTile[1] + cameraColSize; y++) {
                currentTiles.add(allRecs[x][y].shape);
            }
        }

//        for (Polygon polygon : currentTiles) {
//            mapPane.getChildren().add(polygon);
//        }


        BuildingGraphics tower = BuildingGraphics.LOOKOUT_TOWER;
        getHex(tower.getHeight(), 5, 5, tower.getWidth(), tower.getLength(), tower.getImageAddress(), mapPane);
        getHex(1, 12, 6, 3, 3, "stable.jpg", mapPane);
        getHex(2,  9, 15, 3, 3, "barrack.jpg", mapPane);
        getHex(2,  12, 12, 3, 3, "barrack.jpg", mapPane);
        getHex(0.66, 12, 15, 1, 1, "food_storage.png", mapPane);
        pane.getChildren().add(mapPane);
        Scene gameScene = new Scene(pane);
        stage.setScene(gameScene);

//        int halfSums = - halfSatr - halfSotoon;
//        int halfMinus = - halfSatr + halfSotoon;
        //mehran's rectangle
//        view = new Polygon(
//                ((double)(halfSatr + halfMinus)) / iDivider * tileSize, ((double)(halfSatr + halfSums)) / jDivider * tileSize,
//                ((double)(satr - halfSotoon + halfMinus)) / iDivider * tileSize, ((double)(satr + halfSotoon + halfSums)) / jDivider * tileSize,
//                ((double)(halfSatr - sotoon + halfMinus)) / iDivider * tileSize, ((double)(halfSatr + sotoon + halfSums)) / jDivider * tileSize,
//                ((double)( - halfSotoon + halfMinus)) / iDivider * tileSize, ((double)(halfSotoon + halfSums)) / jDivider * tileSize
//        );

        //test unit movement
        Image image1 = new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim1.png").toExternalForm());
        Image image2 = new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim4.png").toExternalForm());
        UnitMovementTemp unitAnimation = new UnitMovementTemp(allRecs[2][4].shape.getPoints().get(2), allRecs[2][4].shape.getPoints().get(3)
                , 10000, image1, image2);
        unitAnimation.shape.setLayoutX(allRecs[0][0].shape.getPoints().get(2));
        unitAnimation.shape.setLayoutY(allRecs[0][0].shape.getPoints().get(3));
        UnitShape unitShape = new UnitShape();
        unitShape.shape = unitAnimation.shape;
        units.add(unitShape);

        mapPane.getChildren().add(unitAnimation.shape);

        unitAnimation.startAnimations();
        //test unit movement

        double x1 = view.getPoints().get(0);
        double y1 = view.getPoints().get(1);
        mapPane.setLayoutX(x1);
        mapPane.setLayoutY(-y1);
        mapPane.setLayoutX(stage.getWidth() / 2);
        mapPane.setLayoutY(0);
//        mapPane.setLayoutY(20 * verticalCameraMove);
        stage.show();


        mapPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isDragging) {
                    dragXStart = event.getX();
                    dragYStart = event.getY();
                    isDragging = true;
                }
            }
        });

//        mapPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if (!isDragging) {
//                    dragXStart = event.getX();
//                    dragYStart = event.getY();
//                }
//                isDragging = true;
//                System.out.println(dragXStart);
//                System.out.println(dragYStart);
//            }
//        });
//
        mapPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isDragging) {
                    isDragging = false;
                    dragXFinish = event.getX();
                    dragYFinish = event.getY();
                    Polygon dragRect = new Polygon(dragXStart, dragYStart, dragXStart, dragYFinish, dragXFinish, dragYFinish, dragXFinish, dragYStart);
                    mapPane.getChildren().add(dragRect);
                    dragRect.setFill(Color.BLACK);
                    for (Polygon tile : currentTiles) {
                        if (tile.getBoundsInParent().intersects(dragRect.getBoundsInParent())) {
                            tile.setFill(Color.GREEN);
                        }
                    }

                }
            }
        });
        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.CONTROL)) {
                    isCtrlPressed = false;
                    addAllBuildings();
                }

            }
        });

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.Q)) {
                    moveCamera(20, 50);
                }
                currentTile[0] = 50;
                currentTile[1] = 50;
                if (keyEvent.getCode().equals(KeyCode.LEFT)) {
                    if (currentTile[0] > 0 && currentTile[1] < colSize - cameraColSize / 2) {
                        mapPane.setLayoutX(mapPane.getLayoutX() + horizontalCameraMove);
                        view.setLayoutX(view.getLayoutX() - horizontalCameraMove);
                        currentTile[1]++;
                        currentTile[0]--;
                        updateCamera();
                    }
                } else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
                    if (currentTile[0] < rowSize - cameraRowSize / 2 && currentTile[1] > 0) {
                        mapPane.setLayoutX(mapPane.getLayoutX() - horizontalCameraMove);
                        view.setLayoutX(view.getLayoutX() + horizontalCameraMove);
                        currentTile[1]--;
                        currentTile[0]++;
                        updateCamera();
                    }
                } else if (keyEvent.getCode().equals(KeyCode.UP)) {
                    if (currentTile[0] > 0 && currentTile[1] > 0) {
                        mapPane.setLayoutY(mapPane.getLayoutY() + verticalCameraMove);
                        view.setLayoutY(view.getLayoutY() - verticalCameraMove);
                        currentTile[1]--;
                        currentTile[0]--;
                        updateCamera();
                    }
                } else if (keyEvent.getCode().equals(KeyCode.DOWN)) {
                    if (currentTile[0] < rowSize - cameraRowSize / 2 && currentTile[1] < colSize - cameraColSize / 2) {
                        mapPane.setLayoutY(mapPane.getLayoutY() - verticalCameraMove);
                        view.setLayoutY(view.getLayoutY() + verticalCameraMove);
                        currentTile[1]++;
                        currentTile[0]++;
                        updateCamera();
                    }
                }
                if (keyEvent.getCode().equals(KeyCode.CONTROL)) {
                    isCtrlPressed = true;
                    removeBuildings();
                }

                if (keyEvent.getCode().equals(KeyCode.MINUS) && isCtrlPressed) {
                    changeZoom(false);
                } else if (keyEvent.getCode().equals(KeyCode.EQUALS) && isCtrlPressed) {
                    changeZoom(true);
                }
            }
        });
//        mapPane.setStyle("-fx-background-color: black;");
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

    private void updateAllRecs() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                Polygon thing = new Polygon(
                        ((double)(i + 1 - j)) / iDivider * tileSize, ((double)(i + 1 + j)) / jDivider * tileSize,
                        ((double)(i - j)) / iDivider * tileSize, ((double)(i + j)) / jDivider * tileSize,
                        ((double)(i - j - 1)) / iDivider * tileSize, ((double)(i + j + 1)) / jDivider * tileSize,
                        ((double)(i - j)) / iDivider * tileSize, ((double)(i + j + 2)) / jDivider * tileSize
                );
                thing.setOpacity(0.8);
                thing.setFill(imagePattern);
                allRecs[i][j].shape = thing;
                if (i == 2 && j == 4 || i == 20 && j == 50) {
                    thing.setFill(Color.RED);
                    thing.hoverProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            VBox tempBox = new VBox();
                            tempBox.setStyle("-fx-background-color: white;");
                            tempBox.getChildren().add(new Button("hello"));
                            tempBox.getChildren().add(new Label("darn it"));
                            tempBox.setLayoutX(thing.getPoints().get(0) / 2 + thing.getPoints().get(4) / 2);
                            tempBox.setLayoutY(thing.getPoints().get(3) / 2 + thing.getPoints().get(7) / 2);
                            mapPane.getChildren().add(tempBox);
                            tempBox.hoverProperty().addListener(((observable1, oldValue1, newValue1) -> {
                                if (!newValue1 && oldValue1) mapPane.getChildren().remove(tempBox);
                            }));
                            System.out.println("you are in");
                        }
                        else if (oldValue) System.out.println("We got out!");
                    });
                    thing.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            thing.setStroke(Color.BLUE);
                        }
                    });
                    thing.setOnMouseReleased(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            thing.setStroke(Color.RED);
                        }
                    });
                }
                if (i == rowSize / 2 && j == colSize / 2) thing.setFill(Color.GREEN);
                thing.setStroke(Color.GREEN);
            }
        }
        updateCamera();
    }

    private void changeZoom(boolean plus) {
        if (plus && tileSize >= 100) return;
        if (!plus && tileSize <= 40) return;
        if (plus) tileSize += tileSizeDelta;
        else tileSize -= tileSizeDelta;
//        for (Polygon polygon : currentTiles) {
//            mapPane.getChildren().remove(polygon);
//        }
        mapPane.getChildren().clear();
//        currentTiles.clear();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                allRecs[i][j] = new TileShape(null);
            }
        }
<<<<<<< HEAD
        updateAllRecs();
=======
>>>>>>> phase2GraphicsAli
        verticalCameraMove = 2 / jDivider * tileSize;
        horizontalCameraMove = 2 / iDivider * tileSize;
        for (BuildingShape building : buildings) {
            building.updatePolygon();
        }
        updateAllRecs();
    }

    public void moveCamera(int row, int col) {
        view.setLayoutY(allRecs[row][col].shape.getPoints().get(1));
        mapPane.setLayoutY(-allRecs[row][col].shape.getPoints().get(1));
        view.setLayoutX(allRecs[row][col].shape.getPoints().get(0));
        mapPane.setLayoutX(-allRecs[row][col].shape.getPoints().get(0));
        updateCamera();
    }

    public void removeBuildings() {
        for (BuildingShape buildingShape : buildings) {
            mapPane.getChildren().remove(buildingShape.polygon);
        }
    }

    public void addAllBuildings() {
        for (BuildingShape buildingShape : buildings) {
            if (!mapPane.getChildren().contains(buildingShape.polygon)) mapPane.getChildren().add(buildingShape.polygon);
        }
    }
}
