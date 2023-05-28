package view.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import view.Captcha;
import view.SignUpMenu;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterConfirmationController implements Initializable {
    @FXML
    public TextField CaptchaAnswer;
    public Label Captcha;
    public TextField confirmAnswer;
    public TextField answer;
    public ChoiceBox securityQuestion;

    public static Pane confirmationPane;

    static {
        try {
            confirmationPane = FXMLLoader.load(SignUpMenu.class.getResource("/fxml/RegisterConfirmation.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetConfirm(ActionEvent actionEvent) {
    }

    public void backConfirm(ActionEvent actionEvent) {
    }

    public void nextCofirm(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Captcha.setText(view.Captcha.makeCapchaInGraphic());
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(0, Color.BLUE),
                new Stop(0.4, Color.BLACK),
                new Stop(0.8, Color.BLACK),
                new Stop(2, Color.BLUE)});
        Captcha.setTextFill(gradient);
    }
}
