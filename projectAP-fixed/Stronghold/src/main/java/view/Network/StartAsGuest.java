package view.Network;

import Model.UserDatabase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import view.Network.Server.DataBase;

public class StartAsGuest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LaunchInitializer.stage = primaryStage;
        LaunchInitializer.user = UserDatabase.getUsers().get(0);
        LaunchInitializer.isHost = false;
        Scene scene = new Scene(LaunchInitializer.fxmlPane);
        Label label = (Label) LaunchInitializer.fxmlPane.getChildren().get(0);
        label.setText("Guest");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
