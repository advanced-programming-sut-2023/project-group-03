package Model.Field;

public enum mazafaza {
    ROCK_N("rockN"),
    ROCK_E("rockE"),
    ROCK_W("rockW"),
    ROCK_S("rockS"),
    DESERT_SHRUB("desert shrub"),
    SAKURA("sakura"),
    OLIVE_TREE("oliver tree"),
    PALM_TREE("palm tree"),
    DATE_TREE("date tree")
    ;

    private String name;

    private mazafaza(String name) {
        this.name = name;
    }

    public static mazafaza getMazafazaByName (String name) {
        for (mazafaza design : mazafaza.values()) {
            if (design.name.equals(name)) return design;
        }
        return null;
    }
}
