package Model.Field;

import java.util.Arrays;
import java.util.HashSet;

public enum RegularTextureGroups {
    NORMAL(new HashSet<>(Arrays.asList(Texture.GROUND,Texture.GRASS,Texture.GRASSLAND,Texture.DENSE_GRASSLAND,Texture.PEBBLE))),
    STONE_SLAB(new HashSet<>(Arrays.asList(Texture.STONE_SLAB))),
    IRON_MINE(new HashSet<>(Arrays.asList(Texture.IRON))),
    FARM(new HashSet<>(Arrays.asList(Texture.GRASS,Texture.DENSE_GRASSLAND))),
    OIL_RIG(new HashSet<>(Arrays.asList(Texture.OIL))),
    WATER(new HashSet<>(Arrays.asList(Texture.WATER))),
    ALL_LAND(new HashSet<>(Arrays.asList(Texture.IRON,Texture.STONE_SLAB,Texture.STONE,Texture.GROUND,Texture.GRASS,Texture.GRASSLAND,Texture.DENSE_GRASSLAND,Texture.PEBBLE)))
    ;

    HashSet<Texture> textures;

    RegularTextureGroups(HashSet<Texture> textures) {
        this.textures = textures;
    }

    public HashSet<Texture> getTextures() {
        return textures;
    }
}
