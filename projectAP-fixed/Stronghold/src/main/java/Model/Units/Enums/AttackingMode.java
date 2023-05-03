package Model.Units.Enums;

public enum AttackingMode {
    STANDING("standing"),
    DEFENSIVE("defensive"),
    AGGRESSIVE("aggressive"),
    ;
    private String name;

    AttackingMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AttackingMode getAttackingModeByName(String name) {
        for (AttackingMode state : AttackingMode.values()) {
            if (state.getName().equals(name)) return state;
        }
        return null;
    }
}
