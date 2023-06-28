package view.fxmlMenu;

import Model.Buildings.Building;
import Model.Buildings.Enums.BuildingGraphics;
import Model.Buildings.Enums.BuildingIcon;
import javafx.application.Application;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GameLayout extends Application implements Initializable {
    private static Pane FxmlRoot;

    static {
        try {
            FxmlRoot = FXMLLoader.load(GameLayout.class.getResource("/fxml/GameMap.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Image woodImg =new Image(GameLayout.class.getResource("/images/menu/wood.png").toExternalForm());
    public Image stoneImg= new Image(GameLayout.class.getResource("/images/menu/stone.png").toExternalForm());
    public Image goldImg = new Image(GameLayout.class.getResource("/images/menu/gold.png").toExternalForm());


    public BuildingIcon currentBuilding;
    public BuildingGraphics currentbuildingGraphics;
    public Rectangle currentMenu;
    public HBox upperBox;
    public HBox lowerBox;
    public Label BuildingName;
    public VBox CostList;
    private String currentMenuName = "castle";
    public Rectangle book;
    public Rectangle CastleBuildings;
    public Rectangle FarmBuildings;
    public Rectangle WeaponBuildings;
    public Rectangle Industry;
    public Label population;
    public Label honor;
    public Label gold;
    public Label date;
    public Label Menu;
    public Rectangle MenuRec;
    public Circle face;
    public Rectangle miniMap;
    public Rectangle defensive;
    public Rectangle TownBuildings;

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(FxmlRoot);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BuildingName.setText("");
        face.setFill(new ImagePattern(new Image(GameLayout.class.getResource("/images/menu/happy.png").toExternalForm())));
        CastleBuildings.setFill(new ImagePattern(new Image(GameLayout.class.getResource("/images/menu/defensive.jpg").toExternalForm())));
        currentMenu = CastleBuildings;
        Industry.setFill(new ImagePattern(new Image(GameLayout.class.getResource("/images/menu/Industry.jpg").toExternalForm())));
        FarmBuildings.setFill(new ImagePattern(new Image(GameLayout.class.getResource("/images/menu/farm.png").toExternalForm())));
        WeaponBuildings.setFill(new ImagePattern(new Image(GameLayout.class.getResource("/images/menu/sword.jpg").toExternalForm())));
        TownBuildings.setFill(new ImagePattern(new Image(GameLayout.class.getResource("/images/menu/town.jpg").toExternalForm())));
        defensive.setFill(new ImagePattern(new Image(GameLayout.class.getResource("/images/menu/tower.png").toExternalForm())));
        chooseMenu(CastleBuildings);
        setActions();
        setMenuClick();
        setMenuItems();
    }

    private void setMenuClick() {
        MenuRec.setOnMouseEntered(event -> {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.YELLOW);
            dropShadow.setSpread(0.7);
            MenuRec.setEffect(dropShadow);
        });
        MenuRec.setOnMouseExited(event -> {
            MenuRec.setEffect(null);
        });
        Menu.setOnMouseEntered(event -> {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.YELLOW);
            dropShadow.setSpread(0.7);
            MenuRec.setEffect(dropShadow);
        });
        Menu.setOnMouseExited(event -> {
            MenuRec.setEffect(null);
        });
    }

    private void setActions() {
        setAction(defensive);
        setAction(CastleBuildings);
        setAction(TownBuildings);
        setAction(FarmBuildings);
        setAction(Industry);
        setAction(WeaponBuildings);
    }

    private void setAction(Rectangle rectangle) {
        rectangle.setOnMouseClicked(event -> {
            currentMenu = rectangle;
            if (Industry.equals(currentMenu)) {
                currentMenuName = "industry";
            } else if (FarmBuildings.equals(currentMenu)) {
                currentMenuName = "farm";
            } else if (WeaponBuildings.equals(currentMenu)) {
                currentMenuName = "weapon";
            } else if (TownBuildings.equals(currentMenu)) {
                currentMenuName = "town";
            } else if (defensive.equals(currentMenu)) {
                currentMenuName = "defensive";
            } else if (CastleBuildings.equals(currentMenu)) {
                currentMenuName = "castle";
            }
            setMenuItems();
            chooseMenu(rectangle);
            rectangle.setEffect(null);
        });
        rectangle.setOnMouseEntered(event -> {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.DARKRED);
            dropShadow.setSpread(0.5);
            rectangle.setEffect(dropShadow);
        });
        rectangle.setOnMouseExited(event -> {
            if (currentMenu.equals(rectangle)) {
                rectangle.setEffect(null);
            } else {
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setSaturation(-1);
                rectangle.setEffect(colorAdjust);
            }
        });

    }

    private void chooseMenu(Rectangle rectangle) {
        BuildingName.setText("");
        currentBuilding = null;
        currentbuildingGraphics = null;
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1);
        Industry.setEffect(colorAdjust);
        CastleBuildings.setEffect(colorAdjust);
        defensive.setEffect(colorAdjust);
        FarmBuildings.setEffect(colorAdjust);
        WeaponBuildings.setEffect(colorAdjust);
        TownBuildings.setEffect(colorAdjust);
        if (rectangle != null) {
            rectangle.setEffect(null);
        }
    }

    private void setMenuItems() {
        int counter = 0;
        while (upperBox.getChildren().size() > 0) {
            upperBox.getChildren().remove(upperBox.getChildren().size() - 1);
        }
        while (lowerBox.getChildren().size() > 0) {
            lowerBox.getChildren().remove(lowerBox.getChildren().size() - 1);
        }
        for (int i = 0; i < BuildingGraphics.values().length; i++) {
            BuildingGraphics buildingGraphics = BuildingGraphics.values()[i];
            if (!buildingGraphics.getMenu().equals(currentMenuName)) {
                continue;
            }
            counter++;
            double width = 60.0 * buildingGraphics.getLength() / buildingGraphics.getHeight();
            if (width > 60) {
                width = 60;
            }
            BuildingIcon buildingIcon = new BuildingIcon(width, 60);
            buildingIcon.setBuildingGraphics(buildingGraphics);
            buildingIcon.setFill(new ImagePattern(buildingGraphics.getBuildingImage()));
            HandleSensitivity(buildingIcon);
            if (counter > 5) {
                lowerBox.getChildren().add(buildingIcon);
            } else {
                upperBox.getChildren().add(buildingIcon);
            }
        }
    }

    private void StackPaneDesigner(StackPane stackPane) {
        Stop[] stops = new Stop[]{
                new Stop(0.0, Color.WHITE),
                new Stop(0.3, Color.RED),
                new Stop(1.0, Color.DARKRED)
        };
        RadialGradient gradient = new RadialGradient(0, 0, 300, 178, 60, false, CycleMethod.NO_CYCLE, stops);
        stackPane.setStyle("-fx-border-radius: 4;-fx-border-color: yellow;-fx-border-width: 5;-fx-padding: 2");
    }

    private void HandleSensitivity(BuildingIcon rectangle) {
        rectangle.setOnMouseExited(event -> {
            if (!rectangle.equals(currentBuilding)) {
                rectangle.setEffect(null);
            }
            if (currentBuilding != null) {
                setCostVbox(currentbuildingGraphics);
                BuildingName.setText(currentBuilding.getBuildingGraphics().getName());
            }
            else {
                BuildingName.setText("");
                setCostVbox(null);
                BuildingName.setVisible(false);
            }
        });
        rectangle.setOnMouseEntered(event -> {
            setCostVbox(rectangle.getBuildingGraphics());
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.DARKRED);
            dropShadow.setSpread(0.5);
            rectangle.setEffect(dropShadow);
            BuildingName.setText(rectangle.getBuildingGraphics().getName());
            BuildingName.setVisible(true);
        });
        rectangle.setOnMouseClicked(event -> {
            setCostVbox(rectangle.getBuildingGraphics());
            if(currentBuilding!=null) currentBuilding.setEffect(null);
            currentbuildingGraphics = rectangle.getBuildingGraphics();
            currentBuilding = rectangle;
            BuildingName.setText(rectangle.getBuildingGraphics().getName());
            BuildingName.setVisible(true);
        });
    }

    private void setCostVbox(BuildingGraphics buildingGraphics) {
        CostList.setVisible(true);
        while (CostList.getChildren().size() > 0) {
            CostList.getChildren().remove(CostList.getChildren().size() - 1);
        }
        if (buildingGraphics == null) {
            return;
        }
        HashMap<String, Integer> costs = Building.findCost(buildingGraphics.getName());
        for (String key : costs.keySet()) {
            if (costs.get(key) > 0) {
                HBox hBox = new HBox();
                hBox.setSpacing(20);
                hBox.setAlignment(Pos.CENTER_LEFT);
                Rectangle rectangle = new Rectangle(30, 30);
                Image image;
                if (key.equals("gold")) {
                    image = goldImg;
                } else if (key.equals("wood")) {
                    image = woodImg;
                } else if (key.equals("stone")) {
                    image = stoneImg;
                } else {
                    image = null;
                }
                rectangle.setFill(new ImagePattern(image));
                Label label = new Label();
                label.setText(costs.get(key) + "");
                label.setFont(Font.font("cursive", FontWeight.BOLD, 15));
                label.setTextFill(Color.YELLOW);
                DropShadow dropShadow = new DropShadow();
                dropShadow.setSpread(0.3);
                dropShadow.setColor(Color.DARKRED);
                label.setEffect(dropShadow);
                hBox.getChildren().addAll(rectangle, label);
                CostList.getChildren().add(hBox);
            }
        }
    }
}
