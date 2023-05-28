package view.Controllers;

import controller.UserBasedMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import view.Captcha;
import view.StartingMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class RegisterController implements Initializable {
    private static Pane RegisterPane;
    private static Pane ConfirmMenu;
    public Text SloganAns;
    public Text emailAns;
    public Text nickAns;
    public Text PassAns;
    public Text usernameAns;
    public TextField CaptchaAnswer;
    public Label Captcha;
    public TextField slogan;
    public CheckBox showSlogan;
    public TextField username;
    public PasswordField password;
    public TextField nickname;
    public TextField email;

    public static void setRegisterPane() {
        try {
            RegisterPane = FXMLLoader.load(StartingMenu.class.getResource("/fxml/RegisterMenu.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ConfirmMenu = FXMLLoader.load(StartingMenu.class.getResource("/fxml/RegisterConfirmation.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void back(ActionEvent actionEvent) {
    }

    public void next(ActionEvent actionEvent) {
        RegisterPane.getChildren().add(ConfirmMenu);
        Captcha.setText("salam");
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
        String output = UserBasedMenuController.checkPassword(password.getText());
        if (output.equals("correct")) {
            PassAns.setText(output);
            PassAns.setFill(Color.GREEN);
        } else {
            PassAns.setText(output);
            PassAns.setFill(Color.RED);
        }
    }

    public void nicknameResponse(KeyEvent keyEvent) {
        String output = UserBasedMenuController.checkUsername(nickname.getText());
        if (output.equals("correct")) {
            nickAns.setText(output);
            nickAns.setFill(Color.GREEN);
        } else {
            PassAns.setText(output);
            PassAns.setFill(Color.RED);
        }
    }

    public void emailResponse(KeyEvent keyEvent) {
        String output = UserBasedMenuController.checkEmail(email.getText());
        if (output.equals("correct")) {
            emailAns.setText(output);
            emailAns.setFill(Color.GREEN);
        } else {
            emailAns.setText(output);
            emailAns.setFill(Color.RED);
        }
    }

    public void showSlagan(ActionEvent actionEvent) {
        if (showSlogan.isSelected()) {
            slogan.setVisible(true);
            SloganAns.setVisible(true);
        } else {
            slogan.setVisible(false);
            SloganAns.setVisible(false);
        }
    }

    public void sloganRespose(KeyEvent keyEvent) {

    }

    public void usernameResponse(KeyEvent keyEvent) {
        String output = UserBasedMenuController.checkUsername(username.getText());
        if (output.equals("correct")) {
            usernameAns.setText(output);
            usernameAns.setFill(Color.GREEN);
        } else {
            usernameAns.setText(output);
            usernameAns.setFill(Color.RED);
        }
    }
}
