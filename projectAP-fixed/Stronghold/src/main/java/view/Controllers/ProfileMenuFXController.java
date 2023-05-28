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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
        showChangeUsername(true, false);
    }

    public void changePassword(ActionEvent actionEvent) {
        showChangePassword();
    }

    public void changeEmail(ActionEvent actionEvent) {
        showChangeUsername(false, true);
    }

    public void changeAvatar(ActionEvent actionEvent) {
        //todo
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Defaults.setCurrentUser(new User("", "", "", "", ""));//todo
        if (Defaults.getCurrentUser().getSlogan() != null) slogan.setText(Defaults.getCurrentUser().getSlogan());
        else slogan.setText("");
    }

    private static VBox getVBox() {
        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: #ee00ff;");
        vBox.setLayoutX((gamePane.getPrefWidth() - 400) / 2);
        vBox.setLayoutY((gamePane.getPrefHeight() - 300) / 2);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefHeight(300);
        vBox.setPrefWidth(400);
        return vBox;
    }

    public static void showChangeUsername(boolean isUsername, boolean isEmail) {
        VBox vBox = getVBox();
        Label label;
        if (!isEmail) label = new Label("Enter your new " + (isUsername ? "username:" : "nickname:"));
        else label = new Label("Enter your new email:");
        TextField usernameField = new TextField();
        if (isUsername && !isEmail) usernameField.setPromptText("username");
        else if(!isEmail) usernameField.setPromptText("nickname");
        else usernameField.setPromptText("email");

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {gamePane.getChildren().remove(vBox);});

        HBox buttons = new HBox();
        Button saveButton = new Button("Save");
        Text error = new Text("");
        buttons.getChildren().addAll(saveButton, cancelButton);

        saveButton.setOnAction(e -> {
            String usernameString = usernameField.getText();
            String output;
            if (!isEmail) output = UserBasedMenuController.checkUsername(usernameString);
            else output = UserBasedMenuController.checkEmail(usernameString);
            System.out.println(output);
            if (!output.equals("correct")) {
                error.setText(output);
                return;
            }
            if (isUsername && !isEmail) Defaults.getCurrentUser().setUsername(usernameString);
            else if (!isEmail) Defaults.getCurrentUser().setNickname(usernameString);
            else Defaults.getCurrentUser().setEmail(usernameString);
        });

        vBox.getChildren().addAll(label, usernameField, error, buttons);
        gamePane.getChildren().add(vBox);
    }

    public static void showChangePassword() {
        VBox vBox = getVBox();
        Label label = new Label("Enter your new password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("password");
        PasswordField passwordConfirmation = new PasswordField();
        passwordConfirmation.setPromptText("password");

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {gamePane.getChildren().remove(vBox);});

        HBox buttons = new HBox();
        Button saveButton = new Button("Save");
        Text error = new Text("");

        saveButton.setOnAction(e -> {
            UserBasedMenuController controller = new UserBasedMenuController();
            String passwordString = controller.getEncryptedPassword(passwordField.getText());
            String output = UserBasedMenuController.checkPassword(passwordString);
            if (!passwordField.getText().equals(passwordConfirmation.getText())) {
                error.setText("Confirmation and password are not equals.");
                System.out.println("There was a problem");
                return;
            }
            if (output.equals("correct")) {
                User user = Defaults.getCurrentUser();
                user.setPassword(passwordString);
                gamePane.getChildren().remove(vBox);
            }
            else {
                error.setText(output);
            }
        });


        buttons.getChildren().addAll(saveButton, cancelButton);
        buttons.setSpacing(30);

        vBox.getChildren().addAll(label, passwordField, passwordConfirmation, error, buttons);

        gamePane.getChildren().add(vBox);
    }

    public void changeSlogan(ActionEvent actionEvent) {

    }

    public void clearSlogan(ActionEvent actionEvent) {
        Defaults.getCurrentUser().setSlogan(null);
    }

    public void changeNickname(ActionEvent actionEvent) {
        showChangeUsername(false, false);
    }
}
