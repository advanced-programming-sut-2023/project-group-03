package view.Controllers;

import Model.Defaults;
import Model.GamePlay.Game;
import controller.UserBasedMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import view.Captcha;
import view.StartingMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class RegisterController implements Initializable {
    private static Pane RegisterPane;
    public Text SloganAns;
    public Text emailAns;
    public Text nickAns;
    public Text PassAns;
    public Text usernameAns;
    @FXML
    public TextField slogan;
    public CheckBox showSlogan;
    public TextField username;
    public PasswordField password;
    public TextField nickname;
    public TextField email;
    public ScrollPane scroller;
    private ImagePattern avatar;
    private HBox menuHBox;

    public static void setRegisterPane() {
        try {
            RegisterPane = FXMLLoader.load(StartingMenu.class.getResource("/fxml/RegisterMenu.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void back(ActionEvent actionEvent) throws Exception {
        new StartingController().start(Defaults.getCurrentStage());
    }

    public void next(ActionEvent actionEvent) {
        Pane confirmation = RegisterConfirmationController.confirmationPane;
        RegisterPane.getChildren().add(confirmation);
    }

    public void reset(ActionEvent actionEvent) {
    }
    public static Pane getRegisterPane() {
        return RegisterPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Rectangle> rectangles=new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            Image image = new Image(Game.class.getResource("/images/avatars/" + i + ".png").toExternalForm());
            Rectangle rectangle = new Rectangle(100,100);
            rectangle.setFill(new ImagePattern(image));
            rectangles.add(rectangle);
            rectangle.setOnMouseEntered(event -> {
                if (avatar == null || !avatar.equals(((ImagePattern) rectangle.getFill()))) {
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setColor(Color.GRAY);
                    dropShadow.setSpread(1);
                    rectangle.setEffect(dropShadow);
                }
            });
            rectangle.setOnMouseExited(event -> {
                if(avatar==null || !avatar.equals(((ImagePattern) rectangle.getFill())))
                    rectangle.setEffect(null);
            });
            rectangle.setOnMouseClicked(event -> {
                for (Rectangle rectangleI : rectangles) {
                    rectangleI.setEffect(null);
                }
                DropShadow dropShadow = new DropShadow();
                dropShadow.setColor(Color.YELLOW);
                dropShadow.setSpread(1);
                rectangle.setEffect(dropShadow);
                avatar = ((ImagePattern) rectangle.getFill());
            });
        }
        HBox menuHBox = new HBox();
        menuHBox.getChildren().addAll(rectangles);
        this.menuHBox = menuHBox;
        scroller.setContent(menuHBox);
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
        String output = UserBasedMenuController.checkUsername(slogan.getText());
        if (output.equals("correct")) {
            SloganAns.setText(output);
            SloganAns.setFill(Color.GREEN);
        } else {
            SloganAns.setText(output);
            SloganAns.setFill(Color.RED);
        }
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
