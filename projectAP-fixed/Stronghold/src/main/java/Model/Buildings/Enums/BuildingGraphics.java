package Model.Buildings.Enums;

import Model.Buildings.Defending.Towers;
import Model.Field.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum BuildingGraphics {
    //tower
    LOOKOUT_TOWER("lookout tower", 4, 1, 1, "lookout_tower.png","castle"),
    SQUARE_TOWER("square tower", 4, 3, 3, "square_tower.png","castle"),
    ROUND_TOWER("round tower", 5, 3, 3, "round_tower.png","castle"),
    PERIMETER_TOWER("perimeter tower", 4, 3, 3, "perimeter_tower.png","castle"),
    //stone gate
    SMALL_STONE_GATE_HOUSE("small stone gate", 3, 3, 3, "small_gate.png","castle"),
    BIG_STONE_GATE_HOUSE("big stone gate", 5, 5, 5, "big_gate.png","castle"),
    //keep
    KEEP("keep", 4, 5, 5, "keep.png","defensive"),
    //trap
    //todo
    //barracks
    BARRACK("barrack", 2 * 5f / 3, 5, 5, "barrack.jpg","defensive"),
    ENGINEER_GUILD("engineer_guild", 2 * 5f / 3, 5, 5, "engineer_guild.jpg","defensive"),
    MERCENARY_POST("mercenary_post", 0.8, 5, 5, "mercenary_post.png","defensive"),
    TUNNELER_GUILD("tunneler guild", 2.5, 5, 5, "tunneller_guild.png","defensive"),
    //rest
    STABLE("stable", 1, RestTypes.STABLE.getSize(), RestTypes.STABLE.getSize(), "stable.jpg","town"),
    HOVEL("hovel", 1, RestTypes.HOVEL.getSize(), RestTypes.HOVEL.getSize(), "hovel.jpg","town"),
    OX_TETHER("ox tether", 1, RestTypes.OX_TETHER.getSize(), RestTypes.OX_TETHER.getSize(), "ox_tether.png","industry"),
    //store
    STORE("store", 1.7, 3, 3, "store.jpg","town"),
    //generators
    CHURCH("church", 1.4, 3, 3, "church.png","town"),
    CATHEDRAL("cathedral", 2, 3, 3, "cathedral.png","town"),
    INN("inn", 2, 3, 3, "inn.png","industry"),
    IRON_MINE("iron mine", 1, 3, 3, "iron_mine.png","industry"),
    PITCH_RIG("pitch rig", 1, 3, 3, "pitch_rig.png","industry"),
    STONE_MINE("stone mine", 2, 3, 3, "quarry.png","industry"),
    WOODCUTTER("woodcutter", 1, 3, 3, "woodCutter.png","industry"),
    ARMOURER("armourer", 2, 3, 3, "armourer.png","weapon"),
    TANNER("tanner", 2, 3, 3, "tanner.png","weapon"),
    ORCHARD("apple orchard", 2, 3, 3, "apple_orchard.png","farm"),
    DAIRY_FARM("dairy farm", 2, 3, 3, "dairy_farm.png","farm"),
    WHEAT_FARM("wheat farm", 2, 3, 3, "wheat_farm.png","farm"),
    BAKERY("bakery", 2, 3, 3, "bakery.png","town"),
    MILL("mill", 4, 3, 3, "mill1.png","town"),
    HUNTERS_HUT("hunter's hut", 1.3, 3, 3, "hunter.png","farm"),
    BOW_MAKER("bow maker", 2, 3, 3, "bow_maker.png","weapon"),
    SWORD_MAKER("sword maker", 2, 3, 3, "sword_maker.png","weapon"),
    SPEAR_MAKER("spear_maker", 2, 3, 3, "spear_maker.png","weapon"),
    PIKE_MAKER("pike_maker", 2, 3, 3, "spear_maker.png","weapon"),
    BREWERY("brewery", 3, 3, 3, "brewery.png","weapon"),
    //inventory
    STOCKPILE("stockpile", 0, 1, 1, "stockpile.png","industry"),
    ARMOURY("armoury", 0.66, 1, 1, "armoury.png","weapon"),
    FOOD_STORAGE("food storage", 0.66, 1, 1, "food_storage.png","farm"),

    ;
    private String name;
    private  double height;
    private  double length;
    private  double width;
    private String imageAddress;
    private Image buildingImage;
    private String menu;

    BuildingGraphics(String name,  double height,  double length,  double width, String imageAddress,String menu) {
        this.menu = menu;
        this.name = name;
        this.height = height;
        this.length = length;
        this.width = width;
        this.imageAddress = imageAddress;
        buildingImage = new Image(
                Tile.class.getResource("/images/buildings/" + imageAddress).toExternalForm()
        );
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

    public Image getBuildingImage() {
        return buildingImage;
    }

    public String getMenu() {
        return menu;
    }
}
