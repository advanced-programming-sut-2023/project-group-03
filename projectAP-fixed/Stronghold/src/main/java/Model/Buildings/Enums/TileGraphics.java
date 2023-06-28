package Model.Buildings.Enums;

import javafx.scene.image.Image;

public enum TileGraphics {
    GROUND("ground", "ground.jpg"),
    PEBBLE("pebble", "pebble.png"),
    STONE_SLAB("stone slab", "stone.png"),//added, can be better
    STONE("stone", "stone.png"),
    IRON("iron", "iron.png"),
    GRASS("grass", "grass.jpg"),
    GRASSLAND("grassland", "grass.jpg"),//added, can be better
    DENSE_GRASSLAND("dense grassland", "grass.jpg"),//added, can be better
    WATER("water", "sea.jpg"),
    OIL("oil", "oil.png"),

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
