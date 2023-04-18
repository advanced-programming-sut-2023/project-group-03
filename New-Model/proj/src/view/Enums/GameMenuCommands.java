package view.Enums;

public enum GameMenuCommands {

    SHOW_MAP(""),
    MAP_MOVE(""),
    SHOW_DETAILS(""),
    SHOW_POPULARITY_FACTORS(""),
    SHOW_POPULARITY(""),
    SHOW_FOOD_LIST(""),
    FOOD_RATE(""),
    FOOD_RATE_SHOW(""),
    TAX_RATE(""),
    TAX_RATE_SHOW(""),
    FEAR_RATE(""),
    FEAR_RATE_SHOW(""),
    DROP_BUILDING(""),
    SELECT_BUILDING(""),
    CREATE_UNIT(""),
    REPAIR(""),
    SELECT_UNIT(""),
    MOVE_UNIT(""),
    PATROL_UNIT(""),
    SET(""),
    ATTACK_ENEMY(""),
    ATTACK_PLACE(""),
    POUR_OIL(""),
    DIG_TUNNEL(""),
    BUILD(""),
    DISBAND_UNIT(""),
    SET_TEXTURE(""),
    SET_MASS_TEXTURE(""),
    ;

    String regex;
    private GameMenuCommands(String regex){
        this.regex = regex;
    }

    @Override

    public String toString(){
        return this.regex;
    }
}
