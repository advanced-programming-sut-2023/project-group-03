package graphicsTest;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;

public class TestMap2 extends Application {
    int tileSize = 100;
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
        pane.setStyle("-fx-background-color: #c09d5e;");
        Pane mapPane = new Pane();
        ImagePattern imagePattern = new ImagePattern(new Image(TestMap2.class.getResource("/images/Plain1.jpg").toExternalForm()));
        Polygon[][] allRecs = new Polygon[satr][sotoon];
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
                if (i == 50 && j == 30) thing.setFill(Color.GREEN);
                //thing.setStroke(Color.GREEN);
//                mapPane.getChildren().add(thing);
            }
        }

        HashSet<Polygon> currentTiles = new HashSet<>();
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

        //mehran's rectangle

        Polygon view = new Polygon(
                ((double)(halfSatr)) / iDivider * tileSize, ((double)(halfSatr)) / jDivider * tileSize,
                ((double)(satr - halfSotoon)) / iDivider * tileSize, ((double)(satr + halfSotoon)) / jDivider * tileSize,
                ((double)(halfSatr - sotoon)) / iDivider * tileSize, ((double)(halfSatr + sotoon)) / jDivider * tileSize,
                ((double)( - halfSotoon)) / iDivider * tileSize, ((double)(halfSotoon)) / jDivider * tileSize
        );
        view.setFill(Color.TRANSPARENT);
        mapPane.getChildren().add(view);

        System.out.println(view.getPoints().toArray()[0]);
        double x1 = view.getPoints().get(0);
        double y1 = view.getPoints().get(1);
        mapPane.setLayoutX(x1);
        mapPane.setLayoutY(-y1);
        mapPane.setLayoutX(stage.getWidth() / 2);
        mapPane.setLayoutY(0);

        stage.show();

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.LEFT)) {
                    if (currentTile[0] > 0 && currentTile[1] < sotoon - cameraCol / 2) {
                        mapPane.setLayoutX(mapPane.getLayoutX() + horizontalCameraMove);
                        currentTile[1]++;
                        currentTile[0]--;
                    }
                } else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
                    if (currentTile[0] < satr - cameraRow / 2 && currentTile[1] > 0) {
                        mapPane.setLayoutX(mapPane.getLayoutX() - horizontalCameraMove);
                        currentTile[1]--;
                        currentTile[0]++;
                    }
                } else if (keyEvent.getCode().equals(KeyCode.UP)) {
                    if (currentTile[0] > 0 && currentTile[1] > 0) {
                        currentTile[1]--;
                        currentTile[0]--;
                        mapPane.setLayoutY(mapPane.getLayoutY() + verticalCameraMove);
                    }
                } else if (keyEvent.getCode().equals(KeyCode.DOWN)) {
                    if (currentTile[0] < satr - cameraRow / 2 && currentTile[1] < sotoon - cameraCol / 2) {
                        mapPane.setLayoutY(mapPane.getLayoutY() - verticalCameraMove);
                        currentTile[1]++;
                        currentTile[0]++;
                    }
                }

//                for (Polygon polygon : currentTiles) {
//                    mapPane.getChildren().remove(polygon);
//                }
//                currentTiles.clear();
//
                updateCamera(currentTiles, allRecs, mapPane);
            }
        });
    }

    private void updateCamera(HashSet<Polygon> currentTiles, Polygon[][] allRecs, Pane mapPane) {
        for (int x = currentTile[0]; x < currentTile[0] + cameraRow; x++) {
            for (int y = currentTile[1]; y < currentTile[1] + cameraCol; y++) {
                if (x >= satr) continue;
                if (y >= sotoon) continue;
                if (!mapPane.getChildren().contains(allRecs[x][y])) {
                    mapPane.getChildren().add(allRecs[x][y]);
                }
            }
        }
    }
}
