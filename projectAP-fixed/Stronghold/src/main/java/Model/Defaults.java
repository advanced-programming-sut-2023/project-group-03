package Model;

import javafx.stage.Stage;

public class Defaults {
    //private static Stage currentStage;
    private static User currentUser;

//    public static Stage getCurrentStage() {
//        return currentStage;
//    }
//
//    public static void setCurrentStage(Stage currentStage) {
//        Defaults.currentStage = currentStage;
//    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Defaults.currentUser = currentUser;
    }
}
