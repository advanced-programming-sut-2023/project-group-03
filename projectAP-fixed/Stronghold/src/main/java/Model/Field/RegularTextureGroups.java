package Model.Field;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public enum RegularTextureGroups {
    NORMAL(new HashSet<>(Arrays.asList(Texture.GROUND, Texture.GRASS, Texture.GRASSLAND, Texture.DENSE_GRASSLAND, Texture.PEBBLE))),
    STONE_SLAB(new HashSet<>(List.of(Texture.STONE))),
    IRON_MINE(new HashSet<>(List.of(Texture.IRON))),
    FARM(new HashSet<>(Arrays.asList(Texture.GRASS, Texture.DENSE_GRASSLAND))),
    OIL_RIG(new HashSet<>(List.of(Texture.OIL))),
    WATER(new HashSet<>(List.of(Texture.WATER))),
    ALL_LAND(new HashSet<>(Arrays.asList(Texture.IRON, Texture.STONE_SLAB, Texture.STONE, Texture.GROUND, Texture.GRASS, Texture.GRASSLAND, Texture.DENSE_GRASSLAND, Texture.PEBBLE)));

    private final HashSet<Texture> textureHashSet;

    RegularTextureGroups(HashSet<Texture> textureHashSet) {
        this.textureHashSet = textureHashSet;
    }

    public HashSet<Texture> getTextureHashSet() {
        return textureHashSet;
    }
}
