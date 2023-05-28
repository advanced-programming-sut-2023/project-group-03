package view.Controllers;

import Model.Defaults;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import view.fxmlMenu.EndingMenu;

public class StartingController extends Application {
    public void exit(ActionEvent actionEvent) {
        new EndingMenu().start();
    }

    public void login(ActionEvent actionEvent) {
    }

    public void register(ActionEvent actionEvent) {
    }

    @Override
    public void start(Stage stage) throws Exception {
    }
}
