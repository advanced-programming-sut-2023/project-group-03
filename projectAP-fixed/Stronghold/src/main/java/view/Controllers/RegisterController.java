package view.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import view.StartingMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class RegisterController implements Initializable {
    private static Pane RegisterPane;

    static {
        try {
            RegisterPane = FXMLLoader.load(StartingMenu.class.getResource("/fxml/RegisterMenu.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Text SloganAns;
    public Text emailAns;
    public Text nickAns;
    public Text PassAns;
    public Text usernameAns;

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
    public static Pane getRegisterPane() {
        return RegisterPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void usernameChanged(InputMethodEvent inputMethodEvent) {
    }

    public void passwordResponse(KeyEvent keyEvent) {
    }

    public void nicknameResponse(KeyEvent keyEvent) {
    }

    public void emailResponse(KeyEvent keyEvent) {
    }

    public void showSlagan(ActionEvent actionEvent) {
    }

    public void sloganRespose(KeyEvent keyEvent) {
    }

    public void usernameResponse(KeyEvent keyEvent) {
    }
}
