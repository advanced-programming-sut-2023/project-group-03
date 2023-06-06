package graphicsTest;

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
    int tileSize = 45;
    int satr = 60;
    int sotoon = 60;
    int halfSatr = satr / 2;
    int halfSotoon = sotoon / 2;
    double iDivider = 2;
    double jDivider = 3.5;

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
        Pane mapPane = new Pane();
        ImagePattern imagePattern = new ImagePattern(new Image(TestMap2.class.getResource("/images/Plain1.jpg").toExternalForm()));
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
                //thing.setStroke(Color.GREEN);
                mapPane.getChildren().add(thing);
            }
        }

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

        stage.show();
    }
}
