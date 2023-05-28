module Stronghold {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
    requires javafx.media;

    exports view;
    exports view.fxmlMenu;
    exports view.Controllers;
    exports Model;
    opens Model to com.google.gson;
    opens view to javafx.fxml;
    opens view.fxmlMenu to javafx.fxml;
    opens view.Controllers to javafx.fxml;
}