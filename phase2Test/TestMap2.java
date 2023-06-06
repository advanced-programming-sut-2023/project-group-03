package projectTest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class TestMap2 extends Application {
    int tileSize = 50;

    double iDivider = 2;
    double jDivider = 2.5;

    public Polygon getHex(int height, int i, int j, int iSize, int jSize, String image, Pane pane) {
        Polygon thing = new Polygon(
                ((double)(i + iSize - j)) / iDivider * tileSize, ((double)(i + iSize + j)) / jDivider * tileSize,
                ((double)(i - j + iSize - jSize)) / iDivider * tileSize, ((double)(i + j + iSize + jSize)) / jDivider * tileSize,
                ((double)(i - j - jSize)) / iDivider * tileSize, ((double)(i + j + jSize)) / jDivider * tileSize,
                ((double)(i - j - jSize)) / iDivider * tileSize, ((double)(i + j + jSize)) / jDivider * tileSize - height * tileSize,
                ((double)(i - j)) / iDivider * tileSize, ((double)(i + j)) / jDivider * tileSize - height * tileSize,
                ((double)(i + iSize - j)) / iDivider * tileSize, ((double)(i + iSize + j)) / jDivider * tileSize - height * tileSize
                );
        thing.setFill(new ImagePattern(new Image(TestMap.class.getResource("/images/" + image).toExternalForm())
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
        Pane pane = new Pane();
        Pane mapPane = new Pane();
        ImagePattern imagePattern = new ImagePattern(new Image(TestMap.class.getResource("/images/Plain1.jpg").toExternalForm()));
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Polygon thing = new Polygon(
                        ((double)(i + 1 - j)) / iDivider * tileSize, ((double)(i + 1 + j)) / jDivider * tileSize,
                        ((double)(i - j)) / iDivider * tileSize, ((double)(i + j)) / jDivider * tileSize,
                        ((double)(i - j - 1)) / iDivider * tileSize, ((double)(i + j + 1)) / jDivider * tileSize,
                        ((double)(i - j)) / iDivider * tileSize, ((double)(i + j + 2)) / jDivider * tileSize
                );
                thing.setOpacity(0.8);
                thing.setFill(imagePattern);
                thing.setStroke(Color.GREEN);
                mapPane.getChildren().add(thing);
            }
        }

        mapPane.setLayoutX(700);

        getHex(4, 10, 10, 1, 1, "tower1.png", mapPane);
//        ImageView imageView = new ImageView(TestMap.class.getResource("/images/tower1.png").toExternalForm());
//        imageView.setLayoutX(1 * tileSize / iDivider);
//        imageView.setLayoutY(5 * tileSize / jDivider);
//        imageView.setFitHeight(5 * tileSize);
//        mapPane.getChildren().add(imageView);



        getHex(1, 12, 6, 3, 3, "wine.png", mapPane);

        getHex(2,  9, 15, 3, 3, "Barracks.png", mapPane);
        getHex(2,  12, 12, 3, 3, "Barracks.png", mapPane);
        getHex(2, 12, 15, 3, 3, "Barracks.png", mapPane);
        pane.getChildren().add(mapPane);
        stage.setScene(new Scene(pane));

        stage.show();
    }
}
