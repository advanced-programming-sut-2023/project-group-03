package graphicsTest;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Scanner;

public class TestMap2 extends Application {
    int tileSize = 50;
    int tileSizeDelta = 20;
    int satr = 400;
    int sotoon = 400;
    int halfSatr = satr / 2;
    int halfSotoon = sotoon / 2;
    double iDivider = 2;
    double jDivider = 3.5;
    int[] currentTile = {0, 0};
    double verticalCameraMove = 2 / jDivider * tileSize;
    double horizontalCameraMove = 2 / iDivider * tileSize;
    int cameraRow = 60;
    int cameraCol = 60;
    boolean isCtrlPressed = false;
    ImagePattern imagePattern = new ImagePattern(new Image(TestMap2.class.getResource("/images/Plain1.jpg").toExternalForm()));

    HashSet<Polygon> currentTiles = new HashSet<>();
    Polygon[][] allRecs = new Polygon[satr][sotoon];
    Polygon view;
    Pane mapPane = new Pane();

    public Polygon getHex(int height, int i, int j, int iSize, int jSize, String image, Pane pane) {
        Polygon thing = new Polygon(
                ((double)(i + iSize - j)) / iDivider * tileSize, ((double)(i + iSize + j)) / jDivider * tileSize,
                ((double)(i - j + iSize - jSize)) / iDivider * tileSize, ((double)(i + j + iSize + jSize)) / jDivider * tileSize,
                ((double)(i - j - jSize)) / iDivider * tileSize, ((double)(i + j + jSize)) / jDivider * tileSize,
                ((double)(i - j - jSize)) / iDivider * tileSize, ((double)(i + j + jSize)) / jDivider * tileSize - height * tileSize,
                ((double)(i - j)) / iDivider * tileSize, ((double)(i + j)) / jDivider * tileSize - height * tileSize,
                ((double)(i + iSize - j)) / iDivider * tileSize, ((double)(i + iSize + j)) / jDivider * tileSize - height * tileSize
                );
        thing.setFill(new ImagePattern(new Image(TestMap2.class.getResource("/images/" + image).toExternalForm())
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

        return thing;
    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setHeight(720);
        stage.setWidth(1240);
        Pane pane = new Pane();
//        pane.setStyle("-fx-background-color: #c09d5e;");
        updateAllRecs();

        for (int x = currentTile[0]; x < currentTile[0] + cameraRow; x++) {
            for (int y = currentTile[1]; y < currentTile[1] + cameraCol; y++) {
                currentTiles.add(allRecs[x][y]);
            }
        }

        for (Polygon polygon : currentTiles) {
            mapPane.getChildren().add(polygon);
        }



        getHex(4, 5, 5, 1, 1, "tower1.png", mapPane);
        getHex(1, 12, 6, 3, 3, "wine.png", mapPane);
        getHex(2,  9, 15, 3, 3, "Barracks.png", mapPane);
        getHex(2,  12, 12, 3, 3, "Barracks.png", mapPane);
        getHex(2, 12, 15, 3, 3, "Barracks.png", mapPane);
        pane.getChildren().add(mapPane);
        Scene gameScene = new Scene(pane);
        stage.setScene(gameScene);

        int halfSums = - halfSatr - halfSotoon;
        int halfMinus = - halfSatr + halfSotoon;
        //mehran's rectangle
//        view = new Polygon(
//                ((double)(halfSatr + halfMinus)) / iDivider * tileSize, ((double)(halfSatr + halfSums)) / jDivider * tileSize,
//                ((double)(satr - halfSotoon + halfMinus)) / iDivider * tileSize, ((double)(satr + halfSotoon + halfSums)) / jDivider * tileSize,
//                ((double)(halfSatr - sotoon + halfMinus)) / iDivider * tileSize, ((double)(halfSatr + sotoon + halfSums)) / jDivider * tileSize,
//                ((double)( - halfSotoon + halfMinus)) / iDivider * tileSize, ((double)(halfSotoon + halfSums)) / jDivider * tileSize
//        );

        view = new Polygon(
                -stage.getWidth(), 0f,
                stage.getWidth(), 0f,
                stage.getWidth(), stage.getHeight(),
                -stage.getWidth(), stage.getHeight()
        );

        view.setFill(Color.TRANSPARENT);
//        mapPane.getChildren().add(view);
        view.setLayoutY(0);
        view.setLayoutX(0);

        double x1 = view.getPoints().get(0);
        double y1 = view.getPoints().get(1);
        mapPane.setLayoutX(x1);
        mapPane.setLayoutY(-y1);
        mapPane.setLayoutX(stage.getWidth() / 2);
//        mapPane.setLayoutY(20 * verticalCameraMove);
        mapPane.setLayoutY(0);
        stage.show();

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.CONTROL)) isCtrlPressed = false;
            }
        });

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                currentTile[0] = 50;
                currentTile[1] = 50;
                if (keyEvent.getCode().equals(KeyCode.LEFT)) {
                    if (currentTile[0] > 0 && currentTile[1] < sotoon - cameraCol / 2) {
                        mapPane.setLayoutX(mapPane.getLayoutX() + horizontalCameraMove);
                        view.setLayoutX(view.getLayoutX() - horizontalCameraMove);
                        currentTile[1]++;
                        currentTile[0]--;
                        updateCamera();
                    }
                } else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
                    if (currentTile[0] < satr - cameraRow / 2 && currentTile[1] > 0) {
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
                    if (currentTile[0] < satr - cameraRow / 2 && currentTile[1] < sotoon - cameraCol / 2) {
                        mapPane.setLayoutY(mapPane.getLayoutY() - verticalCameraMove);
                        view.setLayoutY(view.getLayoutY() + verticalCameraMove);
                        currentTile[1]++;
                        currentTile[0]++;
                        updateCamera();
                    }
                } else if (keyEvent.getCode().equals(KeyCode.CONTROL)) {
                    isCtrlPressed = true;
                } else if (keyEvent.getCode().equals(KeyCode.MINUS) && isCtrlPressed) {
                    changeZoom(false);
                } else if (keyEvent.getCode().equals(KeyCode.EQUALS) && isCtrlPressed) {
                    changeZoom(true);
                }
            }
        });
    }

    private void updateCamera() {
        for (Polygon polygon : currentTiles) {
            mapPane.getChildren().remove(polygon);
        }
        currentTiles.clear();
        for (int x = 0; x < satr; x++) {
            for (int y = 0; y < sotoon; y++) {
                Polygon polygon = allRecs[x][y];
                if (polygon.getBoundsInParent().intersects(view.getBoundsInParent())) {
                    mapPane.getChildren().add(polygon);
                    currentTiles.add(polygon);
                }
            }
        }
//        for (int x = currentTile[0]; x < currentTile[0] + cameraRow; x++) {
//            for (int y = currentTile[1]; y < currentTile[1] + cameraCol; y++) {
//                if (x >= satr) continue;
//                if (y >= sotoon) continue;
//                currentTiles.add(allRecs[x][y]);
//                mapPane.getChildren().add(allRecs[x][y]);
//            }
//        }
    }

    private void updateAllRecs() {
        for (int i = 0; i < satr; i++) {
            for (int j = 0; j < sotoon; j++) {
                Polygon thing = new Polygon(
                        ((double)(i + 1 - j)) / iDivider * tileSize, ((double)(i + 1 + j)) / jDivider * tileSize,
                        ((double)(i - j)) / iDivider * tileSize, ((double)(i + j)) / jDivider * tileSize,
                        ((double)(i - j - 1)) / iDivider * tileSize, ((double)(i + j + 1)) / jDivider * tileSize,
                        ((double)(i - j)) / iDivider * tileSize, ((double)(i + j + 2)) / jDivider * tileSize
                );
                thing.setOpacity(0.8);
                thing.setFill(imagePattern);
                allRecs[i][j] = thing;
                if (i == 2 && j == 4) thing.setFill(Color.GREEN);
                if (i == satr / 2 && j == sotoon / 2) thing.setFill(Color.GREEN);
                thing.setStroke(Color.GREEN);
//                mapPane.getChildren().add(thing);
            }
        }
    }

    private void changeZoom(boolean plus) {
        if (plus && tileSize >= 100) return;
        if (!plus && tileSize <= 20) return;
        if (plus) tileSize += tileSizeDelta;
        else tileSize -= tileSizeDelta;
//        for (Polygon polygon : currentTiles) {
//            mapPane.getChildren().remove(polygon);
//        }
        mapPane.getChildren().clear();
//        currentTiles.clear();
        allRecs = new Polygon[satr][sotoon];
        updateAllRecs();
        verticalCameraMove = 2 / jDivider * tileSize;
        horizontalCameraMove = 2 / iDivider * tileSize;
        updateCamera();
        getHex(2,  12, 12, 3, 3, "Barracks.png", mapPane);
    }
}
