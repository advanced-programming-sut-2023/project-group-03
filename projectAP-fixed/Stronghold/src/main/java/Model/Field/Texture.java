package Model.Field;

import Model.Buildings.Enums.Resources;
import view.Enums.ConsoleColors;
import view.Enums.ConsoleColors;

public enum Texture
{
    GROUND("ground", null),
    PEBBLE("pebble", null),
    STONE_SLAB("stone slab", Resources.STONE),
    STONE("stone", null),
    IRON("iron", Resources.IRON),
    GRASS("grass", null),
    GRASSLAND("grassland", null),
    DENSE_GRASSLAND("dense grassland", null),
    WATER("water", null),
    OIL("oil", Resources.OIL)
    ;
    private String name;
    private Resources resource;

    Texture(String name, Resources resource) {
        this.name = name;
        this.resource = resource;
    }

    public static Texture getByName(String name) {
        for (Texture texture : Texture.values()) {
            if (texture.name.equals(name)) return texture;
        }
        return null;
    }

    public Resources getResource() {
        return this.resource;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        if (name.equals("ground")) {
            return (ConsoleColors.TEXT_BRIGHT_BG_BLACK);
        }
        if (name.equals("pebble")) {
            return (ConsoleColors.TEXT_BG_CYAN);
        }
        if (name.equals("stone slab")) {
            return (ConsoleColors.TEXT_BRIGHT_BG_WHITE);
        }
        if (name.equals("stone")) {
            return (ConsoleColors.TEXT_BG_CYAN);
        }
        if (name.equals("Iron")) {
            return (ConsoleColors.TEXT_BG_YELLOW);
        }
        if (name.equals("grass")||name.equals("grassland")) {
            return (ConsoleColors.TEXT_BRIGHT_BG_GREEN);
        }
        if (name.equals("water")) {
            return (ConsoleColors.TEXT_BRIGHT_BG_BLUE);
        }
        if (name.equals("oil")) {
            return (ConsoleColors.TEXT_BG_PURPLE);
        }
        if (name.equals("dense grassland")) {
            return (ConsoleColors.TEXT_BG_GREEN);
        }
        return "";
    }
}
