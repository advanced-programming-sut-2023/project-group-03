package view.fxmlMenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.Controllers.RegisterController;

public class RegisterMenu extends Application {
    RegisterController registerController = new RegisterController();

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = registerController.getRegisterPane();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
