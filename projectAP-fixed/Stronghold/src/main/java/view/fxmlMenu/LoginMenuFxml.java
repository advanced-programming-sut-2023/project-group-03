package view.fxmlMenu;

import Model.User;
import Model.UserDatabase;
import controller.UserBasedMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginMenuFxml extends Application {

    public TextField userField;
    public PasswordField passField;
    public Label question;
    public Label captchaLabel;
    public TextField newPassField;
    public TextField confirmPassField;
    private Pane pane;
    private Pane forgotPane;
    private boolean isOpen;
    private User currentUser;

    @Override
    public void start(Stage stage) throws Exception {
        this.isOpen = false;
        URL url = LoginMenuFxml.class.getResource("/fxml/LoginMenu.fxml");
        pane = FXMLLoader.load(url);
        URL url1 = LoginMenuFxml.class.getResource("/fxml/ForgotPasswordPane.fxml");
        forgotPane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);

        stage.show();
    }


    public void enterGame(MouseEvent mouseEvent) {
        String username = userField.getText();
        String pass = passField.getText();

        if(UserDatabase.getUserByName(username)==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("idiot");
            alert.setContentText("no such username");
            alert.showAndWait();
            return;
        }

        UserBasedMenuController a = new UserBasedMenuController();

        if(!UserDatabase.getUserByName(username).getPassword().equals(a.getEncryptedPassword(pass))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("idiot");
            alert.setContentText("password does not match");
            alert.showAndWait();
            return;
        }

        //TODO add mainMenu
    }

    public void forgotPass(MouseEvent mouseEvent) throws IOException {
        String username = userField.getText();
        if(UserDatabase.getUserByName(username)==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("idiot");
            alert.setContentText("no such username");
            alert.showAndWait();
            return;
        }
        currentUser = UserDatabase.getUserByName(username);
        for (Node now:pane.getChildren()) {
            now.setOpacity(0.8);
        }
        question.setText(currentUser.getSecurityQuestion());
        //TODO captchaLabel.setText(Captcha);


        forgotPane.setLayoutX(300);
        forgotPane.setLayoutY(175);
        pane.getChildren().add(forgotPane);
    }

    public void backToLogin(MouseEvent mouseEvent) {
        pane.getChildren().remove(forgotPane);
        for (Node now:pane.getChildren()) {
            now.setOpacity(1);
        }
    }
    public void backToLogin() {
        pane.getChildren().remove(forgotPane);
        for (Node now:pane.getChildren()) {
            now.setOpacity(1);
        }
    }

    public void endForgot(MouseEvent mouseEvent) {
        String pass1 = newPassField.getText();
        String pass2 = confirmPassField.getText();

        if(!UserBasedMenuController.checkPassword(pass1).equals("correct")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("idiot");
            alert.setContentText(UserBasedMenuController.checkPassword(pass1));
            alert.showAndWait();
            return;
        }

        if(!pass1.equals(pass2)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("idiot");
            alert.setContentText("password does not match");
            alert.showAndWait();
            return;
        }

        backToLogin();
    }
}
