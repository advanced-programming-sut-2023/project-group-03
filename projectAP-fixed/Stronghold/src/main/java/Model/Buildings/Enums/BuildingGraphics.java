package Model.Buildings.Enums;

import Model.Field.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum BuildingGraphics {
    //tower
    LOOKOUT_TOWER("lookout tower", 4, 1, 1, "lookout_tower.png"),
    SQUARE_TOWER("square tower", 4, 3, 3, "")

    ;
    private String name;
    private int height;
    private int length;
    private int width;
    private String imageAddress;
    private ImageView buildingImage;

    BuildingGraphics(String name, int height, int length, int width, String imageAddress) {
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

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public ImageView getBuildingImage() {
        return buildingImage;
    }
}
