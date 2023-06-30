package view.Network;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Network.Server.DataBase;

public class StartAsGuest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene=new Scene(LaunchInitializer.fxmlPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
