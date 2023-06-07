package Model.Buildings.Enums;

import Model.Field.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum BuildingGraphics {
    //tower
    LOOKOUT_TOWER("lookout tower", 4, 1, 1, "lookout_tower.png"),
    SQUARE_TOWER("square tower", 4, 3, 3, "square_tower.png"),
    ROUND_TOWER("round tower", 5, 3, 3, "round_tower.png"),
    PERIMETER_TOWER("perimeter tower", 4, 3, 3, "perimeter_tower.png"),
    //stone gate
    SMALL_STONE_GATE_HOUSE("small stone gate", 3, 3, 3, "small_gate.png"),
    BIG_STONE_GATE_HOUSE("big stone gate", 5, 5, 5, "big_gate.png"),
    //keep
    KEEP("keep", 4, 5, 5, "keep.png"),
    //trap
    //todo
    //barracks
    BARRACK("barrack", 2 * 5f / 3, 5, 5, "barrack.jpg"),
    ENGINEER_GUILD("engineer_guild", 2 * 5f / 3, 5, 5, "engineer_guild.jpg"),
    MERCENARY_POST("mercenary_post", 0.8, 5, 5, "mercenary_post.png"),
    TUNNELER_GUILD("tunneler guild", 2 * 5f / 3, 5, 5, "tunneller_guild.png"),
    //rest
    STABLE("stable", 1, RestTypes.STABLE.getSize(), RestTypes.STABLE.getSize(), "stable.jpg"),
    HOVEL("hovel", 1, RestTypes.HOVEL.getSize(), RestTypes.HOVEL.getSize(), "hovel.png"),
    OX_TETHER("ox tether", 1, RestTypes.OX_TETHER.getSize(), RestTypes.OX_TETHER.getSize(), "ox_tether.png"),
    //store
    STORE("store", 1.7, 3, 3, "store.jpg"),
    //generators
    CHURCH("church", 1.4, 3, 3, "church.png"),
    CATHEDRAL("cathedral", 2, 3, 3, "cathedral.png"),
    INN("inn", 1, 3, 3, "stable.jpg"),
    IRON_MINE("iron mine", 1, 3, 3, "iron_mine.png"),
    PITCH_RIG("pitch rig", 1, 3, 3, "pitch_rig.png"),





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
