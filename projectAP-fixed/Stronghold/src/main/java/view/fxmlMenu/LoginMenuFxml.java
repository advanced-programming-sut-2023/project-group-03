package view.fxmlMenu;

import Model.UserDatabase;
import controller.UserBasedMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginMenuFxml extends Application {

    public TextField userField;
    public PasswordField passField;
    private Pane pane;
    private Pane forgotPane;

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenuFxml.class.getResource("/fxml/LoginMenu.fxml");
        pane = FXMLLoader.load(url);
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

        if(!UserDatabase.getUserByName(username).getPassword().equals(UserBasedMenuController.getEncryptedPassword(pass))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("idiot");
            alert.setContentText("password does not match");
            alert.showAndWait();
            return;
        }

        //TODO add mainMenu
    }

    public void forgotPass(MouseEvent mouseEvent) throws IOException {
        URL url = LoginMenuFxml.class.getResource("/fxml/ForgotPasswordPane.fxml");
        forgotPane = FXMLLoader.load(url);

        for (Node now:pane.getChildren()) {
            now.setOpacity(0.8);
        }

        forgotPane.setLayoutX(300);
        forgotPane.setLayoutY(175);
        pane.getChildren().add(forgotPane);
    }
}
