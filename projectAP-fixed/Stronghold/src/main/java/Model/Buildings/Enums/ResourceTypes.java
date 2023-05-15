package Model.Buildings.Enums;

public enum ResourceTypes {
    WEAPON("weapon"),
    FOOD("food"),
    STOCK("stock"),
    ;

    private final String name;

    ResourceTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
