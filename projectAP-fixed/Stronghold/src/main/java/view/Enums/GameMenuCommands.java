package view.Enums;

public enum GameMenuCommands {

    SHOW_MAP("^show map (?<coordinatesInfo>.+)"),
    MAP_MOVE("move ((?<verticalDir>\\w+)( (?<verticalNum>\\d+))?)?" +
            "( (?<horizontalDir>\\w+)( (?<horizontalNum>\\d+))?)?"),
    SHOW_DETAILS("^show details (?<coordinatesInfo>.+)"),
    SHOW_INVENTORY("show inventory"),
    SHOW_POPULARITY_FACTORS("^show popularity factors$"),
    SHOW_POPULARITY("^show popularity$"),
    SHOW_FOOD_LIST(""),
    FOOD_RATE("^food rate (?<rateInfo>.+)"),
    FOOD_RATE_SHOW("show food rate"),
    TAX_RATE("^tax rate (?<rateInfo>.+)"),
    TAX_RATE_SHOW("^tax rate show$"),
    FEAR_RATE("^fear rate (?<rateInfo>.+)"),
    FEAR_RATE_SHOW("^fear rate show$"),
    SELECT_BUILDING("^select building (?<buildingInfo>.+)"),
    DROP_BUILDING("^drop castle building (?<buildingInfo>.+)"),
    BUILD_TOWER("^build Tower (?<towerInfo>.+)"),
    BUILD_WALL("^build Wall (?<wallInfo>.+)"),
    BUILD_BARRACKS("^build Barrack (?<barracksInfo>.+)"),
    BUILD_INVENTORY("^build Inventory (?<inventoryInfo>.+)"),
    BUILD_REST("^build Rest (?<restInfo>.+)"),
    BUILD_GENERATOR("^build generator (?<generatorInfo>.+)"),
    BUILD_STONE_GATES("^build Gate (?<stoneGateInfo>.+)"),
    BUILD_DRAW_BRIDGE("^build Bridge (?<drawbridgeInfo>.+)"),
    BUILD_TRAP("^build trap (?<trapInfo>.+)"),
    BUILD_MARKET("build market (?<storeInfo>.+)"),
    CREATE_UNIT("^create unit (?<unitInfo>.+)"),
    REPAIR("^repair$"),
    SELECT_UNIT("^select unit (?<unitInfo>.+)"),
    PATROL_UNIT("^patrol unit (?<patrolInfo>.+)"),
    SET("^set -s (?<stateInfo>\\w+)$"),
    ATTACK_BUILDING("attack building (?<attackInfo>.+)"),
    ATTACK_PLACE("attack place (?<attackInfo>.+)"),
    MOVE_UNIT("move unit (?<placeInfo>.+)"),
    POUR_OIL(""),
    DIG_TUNNEL(""),
    BUILD(""),
    DISBAND_UNIT("disband unit"),
    TRADE_REQUEST("trade (?<TradeInfo>.+"),
    TRADE_ACCEPT("trade accept (?<AcceptInfo>"),
    TRADE_LIST("trade list"),
    TRADE_HISTORY("trade history"),
    BUILD_STAIR("build stair (?<stairInfo>.+)"),
    BUILD_OX_TETHER("build tether (?<buildingInfo>.+)")
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
