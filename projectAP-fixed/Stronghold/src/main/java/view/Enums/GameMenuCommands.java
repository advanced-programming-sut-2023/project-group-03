package view.Enums;

public enum GameMenuCommands {

    SHOW_MAP(""),
    MAP_MOVE(""),
    SHOW_DETAILS(""),
    SHOW_POPULARITY_FACTORS(""),
    SHOW_POPULARITY("^show popularity$"),
    SHOW_FOOD_LIST(""),
    FOOD_RATE("^food rate (?<rateInfo>.+)"),
    FOOD_RATE_SHOW(""),
    TAX_RATE("^tax rate (?<rateInfo>.+)"),
    TAX_RATE_SHOW("^tax rate show$"),
    FEAR_RATE("^fear rate (?<rateInfo>.+)"),
    FEAR_RATE_SHOW("^fear rate show$"),
    SELECT_BUILDING("^select building (?<buildingInfo>.+)"),
    //build building
    BUILD_TOWER("^build Tower (?<towerInfo>.+)"),
    BUILD_WALL("^build Wall (?<wallInfo>.+)"),
    BUILD_BARRACKS("^build Barrack (?<barracksInfo>.+)"),
    BUILD_INVENTORY("^build Inventory (?<inventoryInfo>.+)"),
    BUILD_REST("^build Rest (?<restInfo>.+)"),
    BUILD_GENERATOR("^build (?<generatorInfo>.+)"),
    BUILD_STONE_GATES("^build Gate (?<stoneGateInfo>.+)"),
    BUILD_DRAW_BRIDGE("^build Bridge (?<drawbridgeInfo>.+)"),
    BUILD_TRAP("^build trap (?<trapInfo>.+)"),
    BUILD_MARKET(""),
    CREATE_UNIT(""),
    REPAIR("^repair$"),
    SELECT_UNIT(""),
    MOVE_UNIT(""),
    PATROL_UNIT(""),
    SET("^set -s (?<stateInfo>\\w+)$"),
    ATTACK_ENEMY(""),
    ATTACK_PLACE(""),
    POUR_OIL(""),
    DIG_TUNNEL(""),
    BUILD(""),
    DISBAND_UNIT(""),
    SET_TEXTURE(""),
    SET_MASS_TEXTURE(""),
    TRADE_REQUEST("trade (?<TradeInfo>.+"),
    TRADE_ACCEPT("trade accept (?<AcceptInfo>"),
    TRADE_LIST("trade list"),
    TRADE_HISTORY("trade history"),
    ;

    String regex;

    private GameMenuCommands(String regex) {
        this.regex = regex;
    }

    @Override

    public String toString() {
        return this.regex;
    }
}
