package view.Network;

import Model.UserDatabase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartAsHost extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LaunchInitializer.stage = primaryStage;
        LaunchInitializer.user = UserDatabase.getUsers().get(1);
        Scene scene=new Scene(LaunchInitializer.fxmlPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
