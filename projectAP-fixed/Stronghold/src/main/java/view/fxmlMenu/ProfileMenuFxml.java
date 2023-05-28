package view.fxmlMenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileMenuFxml extends Application {
    Pane pane = FXMLLoader.load(ProfileMenuFxml.class.getResource("/fxml/ProfileMenuPane.fxml"));

    public ProfileMenuFxml() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
