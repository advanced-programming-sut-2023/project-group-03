module Stronghold {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
    requires javafx.media;

    exports view;
    opens Model to com.google.gson;
    opens view to javafx.fxml;
    opens view.Controllers;

}