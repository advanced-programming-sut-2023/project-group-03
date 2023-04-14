package Model.Buildings.Enums;

public enum Resources
{
    FLOUR("flour", TypeOfResource.FOOD, 10);
//    IRON,
//    STONE,
//    OIL,
//    WOOD,
//    METAL_ARMOUR,
//    SWORD,
//    PIKE,
//    SPEAR,
//    LEATHER_ARMOUR,
//    BOW,
//    CROSSBOW,
//    LAVA,
//    APPLE,
//    CHEESE,
//    BARLEY,
//    MEAT,
//    WHEAT,
//    BREAD,
//    WINE,
//    HORSE;
    TypeOfResource type;
    int gold;
    String name;
    Resources(String name,TypeOfResource type, int gold) {
        this.type = type;
        this.gold = gold;
        this.name = name;
    }
}
