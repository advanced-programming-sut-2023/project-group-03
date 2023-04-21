package Model.Field;

 public enum Texture
{
    GROUND("ground"),
    PEBBLE("pebble"),
    STONE_SLAB("stone slab"),
    STONE("stone"),
    IRON("iron"),
    GRASS("grass"),
    GRASSLAND("grassland"),
    DENSE_GRASSLAND("dense grassland"),
    WATER("water"),
    OIL("oil")
    ;
    private String name;

    private Texture(String name) {
        this.name = name;
    }

    public static Texture getByName(String name) {
        for (Texture texture : Texture.values()) {
            if (texture.name.equals(name)) return texture;
        }
        return null;
    }
}
