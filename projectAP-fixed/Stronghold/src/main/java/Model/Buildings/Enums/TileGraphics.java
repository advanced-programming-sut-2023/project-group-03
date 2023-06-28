package Model.Buildings.Enums;

import javafx.scene.image.Image;

public enum TileGraphics {
    GROUND("ground", "ground.jpg"),
    PEBBLE("pebble", "ground.jpg"),
    STONE_SLAB("stone slab", "ground.jpg"),
    STONE("stone", "ground.jpg"),
    IRON("iron", "ground.jpg"),
    GRASS("grass", "ground.jpg"),
    GRASSLAND("grassland", "ground.jpg"),
    DENSE_GRASSLAND("dense grassland", "ground.jpg"),
    WATER("water", "ground.jpg"),
    OIL("oil", "ground.jpg"),

    ;
    private String name;
    private String imageAddress;
    private Image tileImage;

    TileGraphics (String name, String imageAddress) {
        this.name = name;
        this.imageAddress = imageAddress;
    }

    public static TileGraphics getTileGraphicByName(String name) {
        for (TileGraphics tileGraphic : TileGraphics.values()) {
            if (tileGraphic.name.equals(name)) return tileGraphic;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public Image getTileImage() {
        if (tileImage == null) tileImage = new Image(TileGraphics.class.getResource("/images/tiles/" + imageAddress).toExternalForm());
        return tileImage;
    }
}
