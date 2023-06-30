package view.fxmlMenu;

import Model.Buildings.Building;
import Model.Buildings.Enums.*;
import Model.Units.Enums.TroopIcon;
import Model.Units.Enums.TroopTypes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import view.Game.Phase2Test.GameGraphic;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GameLayout extends Application implements Initializable {
    private static GameGraphic gameGraphic = GameGraphic.getGameGraphic();
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

    public Resources CurrentResourceToDeal;
    public Rectangle currentRecResources;
    public ResourceTypes currentType = null;

    public BuildingIcon currentBuilding;

    public Rectangle currentRecTroop;
    public TroopTypes currentUnit;

    public BuildingGraphics currentbuildingGraphics;
    public Rectangle currentMenu;
    public HBox upperBox;
    public HBox lowerBox;
    public Label BuildingName;
    public VBox CostList;
    public Button Buy;
    public Button Sell;
    public javafx.scene.control.Slider Slider;
    public Label GoldCostShop;
    public Rectangle GoldCostImg;
    public Label Count;
    public Label ToDeal;
    public VBox ShopVbox;
    public Label pop;
    public Label UnitCount;
    public Label unitName;
    public HBox UnitsHbox;
    public javafx.scene.control.Slider BarrackSlider;
    public HBox UnitsCost;
    public Pane MapPane;
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
        FxmlRoot.setPrefWidth(1240);
        FxmlRoot.setPrefHeight(720);
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
        HandleShop();
        setUpBarrackMenu();
        MapPane = new Pane();
        //changeMenuToBarracks(BarracksType.BARRACK);
        changeMenuToFood(ResourceTypes.FOOD);
        //changeMenuToFood(ResourceTypes.STOCK);
        //changeMenuToFood(ResourceTypes.WEAPON);
    }

    private void setUpBarrackMenu() {
        BarrackSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            BarrackSlider.setValue(Math.floor(BarrackSlider.getValue()));
            UnitCount.setText(((int) BarrackSlider.getValue())+"");
            UpdateCostOfUnits(currentUnit);
        });
    }

    private void UpdateCostOfUnits(TroopTypes currentUnit) {
        if (currentUnit != null) {
            while (UnitsCost.getChildren().size() > 0) {
                UnitsCost.getChildren().remove(UnitsCost.getChildren().size() - 1);
            }
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(3);
            Label label = new Label();
            Rectangle rectangle = new Rectangle(30,30);
            vBox.getChildren().addAll(rectangle, label);
            label.setText((int)currentUnit.getGold() * BarrackSlider.getValue() + "");
            rectangle.setFill(new ImagePattern(goldImg));
            if(currentUnit.getGold()>0)
                UnitsCost.getChildren().add(vBox);
            for (Resources equipment : currentUnit.getEquipment()) {
                VBox vBox1 = new VBox();
                vBox1.setAlignment(Pos.CENTER);
                vBox1.setSpacing(3);
                Label label1 = new Label();
                Rectangle rectangle1 = new Rectangle(30, 30);
                vBox1.getChildren().addAll(rectangle1, label1);
                rectangle1.setFill(equipment.getImagePattern());
                label1.setText((int) BarrackSlider.getValue() + "");
                UnitsCost.getChildren().add(vBox1);
            }
        }
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

    private void HandleShop() {
        GoldCostImg.setFill(new ImagePattern(goldImg));
        Slider.setValue(1);
        Slider.setBlockIncrement(1);
        Slider.setMajorTickUnit(1);
        Slider.setMinorTickCount(1);
        Slider.valueProperty().addListener((obs, oldValue, newValue) -> {
            Color imageColor = Color.RED.interpolate(Color.ORANGE,
                    Slider.getValue() / 100);
            Slider.setStyle("-fx-custom-color : " + colorToHex(imageColor) + ";");
            Slider.setValue(Math.floor(Slider.getValue()));
            Count.setText(((int) Slider.getValue())+"");
            if(CurrentResourceToDeal!=null)
                GoldCostShop.setText((int)Slider.getValue()*CurrentResourceToDeal.getGold()+"");
        });
    }
    public static String colorToHex(Color color) {
        return String.format("#%02X%02X%02X", (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
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
            if (currentMenu!=null &&currentMenu.equals(rectangle)) {
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
        ShopVbox.setVisible(false);
        UnitsHbox.setVisible(false);
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
            gameGraphic.setMenuBarAction(true);
            gameGraphic.setDropBuilding(true);
            currentBuilding = rectangle;
            BuildingName.setText(rectangle.getBuildingGraphics().getName());
            BuildingName.setVisible(true);
        });
    }

    private void setCostVbox(BuildingGraphics buildingGraphics) {
        UnitsHbox.setVisible(false);
        ShopVbox.setVisible(false);
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

    private void changeMenuToFood(ResourceTypes resourceTypes) {
        UnitsHbox.setVisible(false);
        CostList.setVisible(false);
        currentMenu = null;
        currentMenuName = null;
        int counter = 0;
        while (upperBox.getChildren().size() > 0) {
            upperBox.getChildren().remove(upperBox.getChildren().size() - 1);
        }
        while (lowerBox.getChildren().size() > 0) {
            lowerBox.getChildren().remove(lowerBox.getChildren().size() - 1);
        }
        for (Resources value : Resources.values()) {
            if (value.getType().equals(resourceTypes)) {
                ResourceIcon resourceIcon = new ResourceIcon(50,50,value);
                resourceIcon.setFill(value.getImagePattern());
                HandleResourceSensitivity(resourceIcon);
                if (counter < 5) {
                    upperBox.getChildren().add(resourceIcon);
                } else {
                    lowerBox.getChildren().add(resourceIcon);
                }
                counter++;
            }
        }
    }

    private void HandleResourceSensitivity(ResourceIcon rectangle) {
        rectangle.setOnMouseExited(event -> {
            if (!rectangle.equals(currentRecResources)) {
                rectangle.setEffect(null);
            }
            if (currentRecResources != null) {
                HandleShopVbox(CurrentResourceToDeal);
            }
            else {
                ShopVbox.setVisible(false);
            }
        });
        rectangle.setOnMouseEntered(event -> {
            HandleShopVbox(rectangle.getResources());
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.DARKRED);
            dropShadow.setSpread(0.5);
            rectangle.setEffect(dropShadow);
        });
        rectangle.setOnMouseClicked(event -> {
            HandleShopVbox(rectangle.getResources());
            if(currentRecResources!=null)
            currentRecResources.setEffect(null);
            CurrentResourceToDeal = rectangle.getResources();
            currentRecResources = rectangle;
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.DARKRED);
            dropShadow.setSpread(0.5);
            rectangle.setEffect(dropShadow);
        });
    }

    private void HandleShopVbox(Resources resources) {
        ShopVbox.setVisible(true);
        ToDeal.setText(resources.getName());
        Count.setText(1 + "");
        GoldCostShop.setText(resources.getGold() + "");
        Slider.setValue(1);
    }

    private void changeMenuToBarracks(BarracksType barracksType) {
        UnitsHbox.setVisible(true);
        ShopVbox.setVisible(false);
        CostList.setVisible(false);
        currentMenu = null;
        currentMenuName = null;
        int counter = 0;
        while (upperBox.getChildren().size() > 0) {
            upperBox.getChildren().remove(upperBox.getChildren().size() - 1);
        }
        while (lowerBox.getChildren().size() > 0) {
            lowerBox.getChildren().remove(lowerBox.getChildren().size() - 1);
        }
        for (TroopTypes value : TroopTypes.values()) {
            if (value.getBarracksType() != null && value.getBarracksType().equals(barracksType)) {
                TroopIcon troopIcon = new TroopIcon(60, 60, value);
                troopIcon.setFill(value.getImagePattern());
                UnitSensitivity(troopIcon);
                if (counter < 5) {
                    upperBox.getChildren().add(troopIcon);
                } else {
                    lowerBox.getChildren().add(troopIcon);
                }
                counter++;
            }
        }
    }

    private void UnitSensitivity(TroopIcon rectangle) {
        rectangle.setOnMouseExited(event -> {
            if (!rectangle.equals(currentRecTroop)) {
                rectangle.setEffect(null);
            }
            if (currentRecTroop != null) {
                HandleUnitHbox(currentUnit);
                UpdateCostOfUnits(currentUnit);
            }
            else {
                UnitsHbox.setVisible(false);
            }
        });
        rectangle.setOnMouseEntered(event -> {
            UnitsHbox.setVisible(true);
            HandleUnitHbox(rectangle.getTroopTypes());
            UpdateCostOfUnits(rectangle.getTroopTypes());
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.DARKRED);
            dropShadow.setSpread(0.5);
            rectangle.setEffect(dropShadow);
        });
        rectangle.setOnMouseClicked(event -> {
            UpdateCostOfUnits(rectangle.getTroopTypes());
            UnitsHbox.setVisible(true);
            HandleUnitHbox(rectangle.getTroopTypes());
            if(currentRecTroop!=null)
                currentRecTroop.setEffect(null);
            currentUnit = rectangle.getTroopTypes();
            currentRecTroop = rectangle;
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.DARKRED);
            dropShadow.setSpread(0.5);
            rectangle.setEffect(dropShadow);
        });
    }

    public void HandleUnitHbox(TroopTypes troopTypes) {
        unitName.setText(troopTypes.getName());
        UnitCount.setText("1");
        BarrackSlider.setValue(1);
    }

    public static Pane getFxmlRoot() {
        return FxmlRoot;
    }

    public Pane getMapPane() {
        return MapPane;
    }

    public void setMapPane(Pane mapPane) {
        MapPane = mapPane;
    }

    public GameGraphic getGameGraphic() {
        return gameGraphic;
    }

    public void setGameGraphic(GameGraphic gameGraphic) {
        this.gameGraphic = gameGraphic;
    }
    public BuildingGraphics getCurrentbuildingGraphics() {
        return currentbuildingGraphics;
    }

}
