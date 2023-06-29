package Model.Buildings.Enums;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import view.fxmlMenu.GameLayout;

import java.io.PushbackReader;
import java.util.HashSet;

public enum Resources {
    FLOUR("flour", ResourceTypes.FOOD, 10),
    IRON("iron", ResourceTypes.STOCK, 30),
    STONE("stone", ResourceTypes.STOCK, 15),
    OIL("oil", ResourceTypes.STOCK, 18),
    WOOD("wood", ResourceTypes.STOCK, 5),
    METAL_ARMOUR("metal armour", ResourceTypes.WEAPON, 40),
    SWORD("sword", ResourceTypes.WEAPON, 35),
    PIKE("pike", ResourceTypes.WEAPON, 23),
    SPEAR("spear", ResourceTypes.WEAPON, 14),
    LEATHER_ARMOUR("leather armour", ResourceTypes.WEAPON, 16),
    BOW("bow", ResourceTypes.WEAPON, 7),
    CROSSBOW("crossbow", ResourceTypes.WEAPON, 15),
    LAVA("lava", ResourceTypes.STOCK, 10),
    APPLE("apple", ResourceTypes.FOOD, 7),
    CHEESE("cheese", ResourceTypes.FOOD, 9),
    BARLEY("barley", ResourceTypes.FOOD, 10),
    MEAT("meat", ResourceTypes.FOOD, 20),
    WHEAT("wheat", ResourceTypes.FOOD, 12),
    BREAD("bread", ResourceTypes.FOOD, 17),
    WINE("wine", ResourceTypes.FOOD, 25),
    HORSE("horse", ResourceTypes.WEAPON, 30);

    private final ResourceTypes type;

    ImagePattern imagePattern;
    private final int gold;
    private final String name;
    private static final HashSet<Resources> foods = new HashSet<>();
    private final int sellPrice;

    static {
        foods.add(APPLE);
        foods.add(BREAD);
        foods.add(CHEESE);
        foods.add(MEAT);
    }

    Resources(String name, ResourceTypes type, int gold) {
        this.type = type;
        this.gold = gold;
        this.name = name;
        sellPrice = (int) Math.floor(0.8 * gold);
        //System.out.println(name);
        imagePattern = new ImagePattern(new Image(GameLayout.class.getResource("/images/menu/" + name + ".png").toExternalForm()));
    }

    public ResourceTypes getType() {
        return type;
    }

    public int getGold() {
        return gold;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public static Resources getResourceByName(String name) {
        for (Resources resource : Resources.values()) {
            if (resource.getName().equals(name)) return resource;
        }
        return null;
    }

    public static HashSet<Resources> getFoods() {
        return foods;
    }

    public ImagePattern getImagePattern() {
        return imagePattern;
    }
}
