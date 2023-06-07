package Model.Buildings.Enums;

import Model.Field.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum BuildingGraphics {
    //tower
    LOOKOUT_TOWER("lookout tower", 4, 1, 1, "lookout_tower.png"),
    SQUARE_TOWER("square tower", 4, 3, 3, "square_tower.png"),
    //stone gate
    SMALL_STONE_GATE_HOUSE("small stone gate", 3, 3, 3, "small_gate.png"),
    BIG_STONE_GATE_HOUSE("big stone gate", 5, 5, 5, "big_gate.png"),


    ;
    private String name;
    private  double height;
    private  double length;
    private  double width;
    private String imageAddress;
    private ImageView buildingImage;

    BuildingGraphics(String name,  double height,  double length,  double width, String imageAddress) {
        this.name = name;
        this.height = height;
        this.length = length;
        this.width = width;
        this.imageAddress = imageAddress;
        buildingImage = new ImageView(new Image(
                Tile.class.getResource("/images/buildings/" + imageAddress).toExternalForm()
        ));
    }

    public BuildingGraphics getBuildingByName(String name) {
        for (BuildingGraphics graphics : BuildingGraphics.values()) {
            if (graphics.name.equals(name)) return graphics;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public  double getHeight() {
        return height;
    }

    public  double getLength() {
        return length;
    }

    public  double getWidth() {
        return width;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public ImageView getBuildingImage() {
        return buildingImage;
    }
}
