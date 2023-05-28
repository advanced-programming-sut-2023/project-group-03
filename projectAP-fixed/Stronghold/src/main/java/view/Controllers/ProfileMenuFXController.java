package view.Controllers;

import Model.Defaults;
import Model.User;
import controller.UserBasedMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileMenuFXController implements Initializable {
    private static Pane gamePane;
    public HBox profile;
    public Label slogan;

    public static Pane getGamePane() {
        return gamePane;
    }

    public static void setGamePane(Pane gamePane) {
        ProfileMenuFXController.gamePane = gamePane;
    }

    public void changeUsername(ActionEvent actionEvent) {
    }

    public void changePassword(ActionEvent actionEvent) {
        showChangePassword();
    }

    public void changeEmail(ActionEvent actionEvent) {
    }

    public void changeAvatar(ActionEvent actionEvent) {
        //todo
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Defaults.getCurrentUser().getSlogan() != null) slogan.setText(Defaults.getCurrentUser().getSlogan());
        else slogan.setText("");
    }

    public static void showChangePassword() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefHeight(300);
        vBox.setPrefWidth(400);
        Label label = new Label("Enter your new password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("password");
        PasswordField passwordConfirmation = new PasswordField();
        passwordConfirmation.setPromptText("password");

        HBox buttons = new HBox();
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            if (!passwordField.getText().equals(passwordConfirmation.getText())) return;
            UserBasedMenuController controller = new UserBasedMenuController();
            String passwordString = controller.getEncryptedPassword(passwordField.getText());
            if (UserBasedMenuController.checkPassword(passwordString).equals("correct")) {
                User user = Defaults.getCurrentUser();
                user.setPassword(passwordString);
                gamePane.getChildren().remove(vBox);
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {gamePane.getChildren().remove(vBox);});
        buttons.setSpacing(30);

        vBox.getChildren().addAll(label, passwordField, passwordConfirmation, buttons);

        gamePane.getChildren().add(vBox);
    }

    public void changeSlogan(ActionEvent actionEvent) {
    }

    public void clearSlogan(ActionEvent actionEvent) {
        Defaults.getCurrentUser().setSlogan(null);
    }
}
