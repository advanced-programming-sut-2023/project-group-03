package Model.Field;

public enum mazafaza {
    ROCK_N("rockN", 1),
    ROCK_E("rockE", 2),
    ROCK_W("rockW",3),
    ROCK_S("rockS",4),
    DESERT_SHRUB("desert shrub",5),
    SAKURA("sakura",6),
    OLIVE_TREE("oliver tree",7),
    PALM_TREE("palm tree",8),
    DATE_TREE("date tree",9)
    ;

    private String name;
    private byte code;
    private mazafaza(String name,int code) {
        this.name = name;
        this.code= ((byte) code);
    }

    public static mazafaza getMazafazaByName (String name) {
        for (mazafaza design : mazafaza.values()) {
            if (design.name.equals(name)) return design;
        }
        return null;
    }

    public static mazafaza getMazafazaBycode (byte code) {
        for (mazafaza design : mazafaza.values()) {
            if (design.code == code) {
                return design;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public byte getCode() {
        return code;
    }
}
