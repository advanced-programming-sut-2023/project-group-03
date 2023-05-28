package view.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Pane;
import view.StartingMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class RegisterController implements Initializable {
    private Pane RegisterPane;

    {
        try {
            RegisterPane = FXMLLoader.load(StartingMenu.class.getResource("/fxml/RegisterMenu.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void back(ActionEvent actionEvent) {
    }

    public void next(ActionEvent actionEvent) {
    }

    public void reset(ActionEvent actionEvent) {
    }

    public void resetConfirm(ActionEvent actionEvent) {
    }

    public void backConfirm(ActionEvent actionEvent) {
    }

    public void nextCofirm(ActionEvent actionEvent) {
    }

    public void usernameChanged(InputMethodEvent inputMethodEvent) {
    }

    public Pane getRegisterPane() {
        return RegisterPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
