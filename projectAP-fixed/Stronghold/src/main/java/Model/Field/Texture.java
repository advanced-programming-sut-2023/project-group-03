package Model.Field;

import Model.Buildings.Enums.Resources;
import view.Enums.ConsoleColors;
import view.Enums.ConsoleColors;

public enum Texture
{
    GROUND("ground", null,1),
    PEBBLE("pebble", null,2),
    STONE_SLAB("stone slab", Resources.STONE,3),
    STONE("stone", null,4),
    IRON("iron", Resources.IRON,5),
    GRASS("grass", null,6),
    GRASSLAND("grassland", null,7),
    DENSE_GRASSLAND("dense grassland", null,8),
    WATER("water", null,9),
    OIL("oil", Resources.OIL, 10),
    ;
    private byte code;
    private String name;
    private Resources resource;

    Texture(String name, Resources resource,int code) {
        this.name = name;
        this.resource = resource;
        this.code = ((byte) code);
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

    public static Texture getTextureByName(String name) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].getName().equals(name)) {
                return values()[i];
            }
        }
        return null;
    }

    public static Texture getTextureByCode(byte code) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].code == code) {
                return values()[i];
            }
        }
        return null;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
