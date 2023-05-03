package Model.Buildings.Enums;

import Model.Field.RegularTextureGroups;
import Model.Field.Texture;

import java.util.HashSet;

public enum GeneratorTypes {
    MILL("mill", 100,10,10,0,100,Resources.FLOUR,Resources.WHEAT,20,0,3, RegularTextureGroups.NORMAL.getTextures()),
    INN("inn",300, 0, 5,0,60,null,Resources.WINE,20,100,1,RegularTextureGroups.NORMAL.getTextures()),
    IRON_MINE("iron mine", 100,5,0,0,80,Resources.IRON,null,20,0,2,RegularTextureGroups.IRON_MINE.getTextures()),
    STONE_MINE("stone mine", 100,8,0,0,80,Resources.STONE,null,20,0,2,RegularTextureGroups.IRON_MINE.getTextures()),
    //OX_TETHER,
    PITCH_RIG("pitch rig", 40,10,0,0,0,Resources.OIL,null,20,0,1,RegularTextureGroups.NORMAL.getTextures()),
    QUARRY("quarry", 150, 15,0,0,0,Resources.STONE,null,20,0,3,RegularTextureGroups.STONE_SLAB.getTextures()),
    WOODCUTTER("woodcutter", 40,15,0,0,0,Resources.WOOD,null,3,0,1,RegularTextureGroups.NORMAL.getTextures()),
    ARMOURER("armourer",80,2,3,0,0,Resources.METAL_ARMOUR,Resources.IRON,20,100,1,RegularTextureGroups.NORMAL.getTextures()),
    TANNER("tanner",80,2,3,0,0,Resources.METAL_ARMOUR,null,20,50,1,RegularTextureGroups.NORMAL.getTextures()),
    //TANNER,
    SWORD_MAKER("swordMaker", 120,2,3,0,0,Resources.SWORD,Resources.IRON,20,100,1,RegularTextureGroups.NORMAL.getTextures()),
    BOW_MAKER("bowMaker", 100, 4, 8,0,0,Resources.BOW,Resources.WOOD,20,100,1,RegularTextureGroups.NORMAL.getTextures()),
    SPEAR_MAKER("SpearMaker",110,2,3,0,0,Resources.SPEAR,Resources.IRON,20,100,1,RegularTextureGroups.NORMAL.getTextures()),
    PIKE_MAKER("pikeMaker",110,2,3,0,0,Resources.PIKE,Resources.IRON,20,100,1,RegularTextureGroups.NORMAL.getTextures()),
    //OIL_SMELTER,
    ORCHARD("apple orchard", 30, 10,0,0,0,Resources.APPLE,null,5,0,1,RegularTextureGroups.NORMAL.getTextures()),
    DAIRY_FARM("dairy farm", 50,7,0,0,0,Resources.CHEESE,null,10,0,1,RegularTextureGroups.NORMAL.getTextures()),
    BARLEY_FARM("barley farm",30,10,0,0,0,Resources.BARLEY,null,15,0,1,RegularTextureGroups.FARM.getTextures()),
    HUNTERS_HUT("hunter's hut", 80,4,0,0,0,Resources.MEAT,null,5,0,1,RegularTextureGroups.NORMAL.getTextures()),
    WHEAT_FARM("wheat farm",30,10,0,0,0,Resources.WHEAT,null,15,0,1,RegularTextureGroups.FARM.getTextures()),
    BAKERY("bakery", 40,10,10,0,0,Resources.BREAD,Resources.FLOUR,10,0,1,RegularTextureGroups.NORMAL.getTextures()),
    BREWERY("brewery",60,10,10,0,0,Resources.WINE,Resources.BARLEY,10,0,1,RegularTextureGroups.NORMAL.getTextures()),
    STABLE("stable",20,1,0,0,4,Resources.HORSE,null,20,400,0,RegularTextureGroups.NORMAL.getTextures()),
    HOVEL("hovel",20,0,0,0,8,null,null,15,0,0,RegularTextureGroups.NORMAL.getTextures()),
    CHURCH("church",150,0,0,0,0,null,null,0,250,0,RegularTextureGroups.NORMAL.getTextures()),
    CATHEDRAL("cathedral",250,0,0,0,0,null,null,0,250,0,RegularTextureGroups.NORMAL.getTextures())
    ;
    private String name;
    private int HP;
    private int produceRate;
    private int useRate;
    private int inventory;
    private int capacity;
    private Resources product;
    private Resources use;
    private int wood;
    private int gold;
    private int worker;
    private HashSet<Texture> textures;

    GeneratorTypes(String name, int HP, int produceRate, int useRate, int inventory, int capacity, Resources product, Resources use, int wood, int gold, int worker, HashSet<Texture> textures) {
        this.name = name;
        this.HP = HP;
        this.produceRate = produceRate;
        this.useRate = useRate;
        this.inventory = inventory;
        this.capacity = capacity;
        this.product = product;
        this.use = use;
        this.wood = wood;
        this.gold = gold;
        this.worker = worker;
        this.textures = textures;
    }

    public int getInventory() {
        return inventory;
    }

    public int getCapacity() {
        return capacity;
    }

    public Resources getProduct() {
        return product;
    }

    public Resources getUse() {
        return use;
    }

    public int getWood() {
        return wood;
    }

    public int getGold() {
        return gold;
    }

    public int getProduceRate() {
        return produceRate;
    }

    public int getUseRate() {
        return useRate;
    }

    public int getWorker() {
        return worker;
    }

    public int getHP() {
        return HP;
    }

    public HashSet<Texture> getTextures() {
        return textures;
    }

    public static GeneratorTypes getTypeByName(String name) {
        for (GeneratorTypes type : GeneratorTypes.values()) {
            if (type.name.equals(name)) return type;
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
