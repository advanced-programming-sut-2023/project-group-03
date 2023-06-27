package graphicsTest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class TestMap3 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(300);
        stage.setHeight(300);

        URL address = TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/");
        Image image1 = new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim1.png").toExternalForm());
        Image image2 = new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim4.png").toExternalForm());
        UnitMovementTemp unitAnimation = new UnitMovementTemp(0, stage.getHeight() / 2, 3000, image1, image2);
        unitAnimation.shape.setLayoutX(stage.getWidth() / 2 - 20);
        unitAnimation.shape.setLayoutY(stage.getHeight() / 2 - 30);

        Pane pane = new Pane();
        pane.getChildren().add(unitAnimation.shape);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        unitAnimation.startAnimations();
    }
}
