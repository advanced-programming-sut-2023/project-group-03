package Model.Field;

import Model.Buildings.Enums.Resources;

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

    private Texture(String name, Resources resource) {
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
}
