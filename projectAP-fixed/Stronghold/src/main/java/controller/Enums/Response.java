package controller.Enums;

public enum Response {
    //general
    INVALID_COMMAND("Invalid command!"),
    INVALID_KEY("You entered an invalid key: "),
    FIELD_EMPTY("This field is empty: "),
    FIELD_FORGOTTEN("You forgot this field: "),
    //register
    USERNAME_EMPTY("Username field is empty!"),
    PASSWORD_EMPTY("Password field is empty!"),
    NICKNAME_EMPTY("Nickname field is empty!"),
    EMAIL_EMPTY("Email field is empty!"),
    SLOGAN_EMPTY("You entered -s but didn't provide any slogan!"),
    INVALID_USERNAME_FORMAT("Username's format is wrong! You can only use English letters and underscore."),
    INVALID_NICKNAME_FORMAT("Nickname's format is wrong! You can only use English letters and underscore."),
    REPETITIVE_USERNAME("A player with this username exists!"),
    WEAK_PASSWORD("Your password is weak! Your password requires at least 6 letters including capital and small letters and digits and a random character!"),
    WRONG_PASSWORD_CONFIRMATION("Confirmation password and password are not the same!"),
    PASSWORD_CONFIRMATION_EMPTY("You forgot to write password confirmation! Please write it exactly after the password."),
    REPETITIVE_EMAIL("A player with this email exists!"),
    INVALID_EMAIL_FORMAT("Your Email's format is wrong!"),
    INVALID_OPTION("You entered an invalid option!"),
    REPETITIVE_OPTION("You entered this option twice: "),
    WRONG_ANSWER_CONFIRMATION("Answer and confirmation answer are not the same!"),
    QUESTION_OUT_OF_RANGE("Question's number is invalid."),
    SUCCESSFUL_REGISTER("You got signed up successfully!"),
    //login
    UNKNOWN_USERNAME("There is no player with this username!"),
    WRONG_PASSWORD_LOGIN("Your password is wrong! You have to wait for: "),
    ENTER_PASSWORD("Enter your password or enter 'back' if you forgot your password."),
    BACK_TO_LOGIN_MENU("You are back in the login menu!"),
    SUCCESSFUL_LOGIN("User logged in successfully!"),
    GET_ANSWER_LOGIN("Please enter the answer of this question or write 'back' to go to login menu."),
    ENTER_NEW_PASSWORD("Please enter a new password:"),
    WRONG_ANSWER_SECURITY_QUESTION("Wrong answer!"),
    LOGOUT("User logged out successfully!"),
    //profile
    //most of the errors are same as login and their response are the same...
    WRONG_CURRENT_PASSWORD("Your old password is wrong!"),
    REPETITIVE_PASSWORD("New password and the old password are the same!"),
    SUCCESSFUL_CHANGE_USERNAME("Your username changed successfully!"),
    SUCCESSFUL_CHANGE_NICKNAME("Your nickname changed successfully!"),
    SUCCESSFUL_CHANGE_PASSWORD("Your password changed successfully!"),
    SUCCESSFUL_CHANGE_EMAIL("Your email changed successfully!"),
    SUCCESSFUL_CHANGE_SLOGAN("Your slogan changed successfully!"),
    SUCCESSFUL_REMOVE_SLOGAN("Your slogan got cleared!"),
    DISPLAY_NULL_SLOGAN("You haven't set your slogan yet!"),
    DISPLAY_RANK("Your rank is: "),
    DISPLAY_HIGHSCORE("Your highscore is: "),
    //map
    INVALID_X_MAP("Invalid x value!"),
    INVALID_Y_MAP("Invalid y value!"),
    INVALID_VERTICAL_DIRECTION("Invalid direction for vertical move!"),
    INVALID_HORIZONTAL_DIRECTION("Invalid direction for horizontal move!"),
    INVALID_FINAL_X_VALUE("Invalid final x value!"),
    INVALID_FINAL_Y_VALUE("Invalid final y value!"),
    INVALID_RECTANGLE("Given coordinates are not valid. Enter top-left and bottom-right of the rectangle."),
    SUCCESSFUL_SHOW_MAP("game's map:"),
    SUCCESSFUL_MOVE_MAP("game's map:"),
    //set environment
    INVALID_TEXTURE("Invalid texture."),
    BUILDING_EXIST("There is a building here..."),
    BUILDING_EXIST_RECTANGLE("There is a building in this location: "),
    SUCCESSFUL_SETTEXTURE("successful set texture."),
    SUCCESSFUL_CLEAR_TILE("successful clear tile"),
    ////drop rock
    INVALID_ROCK_DIRECTION("The given direction is invalid. Enter one of n, e, w, s, r."),
    ROCK_EXIST("There is a rock or tree in this place already."),
    UNIT_EXIST("There is a unit here..."),
    SUCCESSFUL_DROP_ROCK("successful drop rock"),
    SUCCESSFUL_DROP_TREE("successful_drop_tree"),
    INVALID_TREE("There is no such a tree in the game."),
    ////drop unit
    UNIT_NOT_ACCESS("You don't have access to this tile."),
    INVALID_UNIT_AMOUNT("Invalid number for amount of units."),
    SUCCESSFUL_DROP_UNIT("Successfully drop unit!"),
    INVALID_TILE_DROP_UNIT("You can't drop your unit on a tile with this texture."),
    INVALID_UNIT_TYPE("Invalid unit type."),
    ////drop building
    DROP_BUILDING_TEXTURE("You can't build the given building on a tile with this texture."),
    SUCCESSFUL_DROP_BUILDING("Successfully dropped building!"),
    STORE_DROP("You don't have permission to make a store."),
    KEEP_EXIST("This flag already has a keep."),
    ////set owner
    SUCCESSFUL_SET_OWNER("Successfully set owner."),
    //market
    INVALID_RESOURCE_TYPE("Invalid resource type."),
    INVALID_RESOURCE_AMOUNT("Please enter an integer for 'amount'."),
    ////buy
    NOT_ENOUGH_GOLD_PURCHASE("You don't have enough gold to purchase."),
    SUCCESSFUL_PURCHASE("Successfully purchased."),
    ////sell
    NOT_ENOUGH_RESOURCE_SELL("You don't have that much resource to sell."),
    SUCCESSFUL_SELL("Successfully sold."),
    //kingdom
    INVALID_INTEGER_KINGDOM("Enter an integer for rate field."),
    INVALID_FOOD_RATE("Invalid food rate! -3 < food rate < 3"),
    SUCCESSFUL_CHANGE_FOOD_RATE("Food rate successfully changed."),
    INVALID_TAX_RATE("Invalid tax rate! -4 < tax rate < 9"),
    SUCCESSFUL_CHANGE_TAX_RATE("Tax rate successfully changed."),
    INVALID_FEAR_RATE("Invalid fear rate! -6 < fear rate < 6"),
    SUCCESSFUL_CHANGE_FEAR_RATE("Fear rate successfully changed."),
    ////buildings
    INVALID_LOCATION("Invalid x or y for location!"),
    //drop building
    INVALID_BUILDING_TYPE("There is no building type same as the given type!"),
    BUILDING_ALREADY_EXISTS("This field already has a building!"),
    ACQUISITION("You don't own this tile."),

    NOT_ENOUGH_WOOD_BUILDING("Not enough wood to build this building."),
    NOT_ENOUGH_GOLD_BUILDING("Not enough gold to build this building."),
    NOT_ENOUGH_OIL_BUILDING("Not enough oil to build this building."),
    NOT_ENOUGH_STONE_BUILDING("Not enough stone to build this building."),
    NOT_ENOUGH_WORKER_BUILDING("Not enough population to get workers for this building."),
    ////tower
    NOT_ENOUGH_STONE_TOWER("Not enough stone to build this tower."),
    ////wall
    INVALID_WALL_TYPE("There is no wall with this type."),
    NOT_ENOUGH_STONE_WALL("Not enough stone to build this wall."),
    ////barracks
    INVALID_BARRACKS_TYPE("There is no barracks building with this type."),
    NOT_ENOUGH_WOOD_BARRACKS("Not enough wood to build this barracks building."),
    NOT_ENOUGH_GOLD_BARRACKS("Not enough gold to build this barracks building."),
    NOT_ENOUGH_OIL_BARRACKS("Not enough oil to build this barracks building."),
    ////inventory
    INVALID_INVENTORY_TYPE("There is no inventory like this."),
    NOT_ENOUGH_STONE_ARMOURY("Not enough stone to build armoury."),
    NOT_ENOUGH_WOOD_INVENTORY("Not enough wood to build this storage."),
    NO_INVENTORY_AROUND("There was no inventory like this around this tile."),
    ////rest
    INVALID_REST_TYPE("Given type is neither stable nor hovel."),
    NOT_ENOUGH_WOOD_REST("You don't have enough wood to build this."),
    NOT_ENOUGH_GOLD_REST("You don't have enough gold to build this."),
    ////generator
    INVALID_GENERATOR_TYPE("There is no generator with this type. Pay more attention:("),
    ////gates
    INVALID_STONE_GATE_TYPE("The given type is neither big stone gate nor small stone gate."),
    NOT_ENOUGH_STONE_STONE_GATE("You don't have enough stone to build this stone gate."),
    ////drawbridge
    INVALID_DIRECTION_DRAWBRIDGE("Invalid direction."),
    //unit
    INVALID_AMOUNT_UNIT("Enter an integer for amount field."),
    ////troop
    INVALID_TROOP_TYPE_UNIT("Invalid troop type."),
    NOT_ENOUGH_GOLD_UNIT("You don't have enough gold to make this amount of this troop."),
    NOT_ENOUGH_RESOURCES_UNIT("You don't have enough of this resource: "),
    NOT_RIGHT_PLACE_UNIT("You can't make this at this kinda building."),
    ////engineer
    NOT_ENOUGH_GOLD_ENGINEER("You don't have enough gold to make this amount of engineers."),
    NOT_ENOUGH_POPULATION_ENGINEER("You don't have enough population."),
    SUCCESSFUL_ADD_ENGINEER("Successfully added that amount of engineers."),
    //select building
    NO_BUILDING_EXIST("There is no building here!"),
    BUILDING_OWNERSHIP_PROBLEM("You don't own this building!"),
    //making units
    NOT_ENOUGH_RESOURCES("There is not enough resources to make the given unit."),
    NOT_ENOUGH_POPULATION("You don't have enough population to make the given unit."),
    INVALID_TROOP_TYPE("There is no soldier type same as the given type!"),
    //repair
    NOT_ENOUGH_STONE_REPAIR("There is not enough resources to repair!"),
    NOT_ENOUGH_GOLD_REPAIR("You don't have enough gold to repair this building."),
    UNABLE_TO_REPAIR("You can't repair right now. Enemies' soldiers are too close!"),
    INVALID_BUILDING_REPAIR("You can't repair this kinda building."),
    SUCCESSFUL_REPAIR("Your building got repaired successfully!"),
    ////units
    NO_UNIT_EXIST("There is no unit here!"),
    INVALID_DESTINATION("You can't go to that place. It is too high or too deap!"),
    LONG_DESTINATION("Please choose a closer destination."),
    INVALID_UNIT_STATE("Invalid unit state! You can only choose : standing, defensive, offensive."),
    //attack
    NOT_ARCHER("Selected unit is not Archer!"),
    NOT_ENGINEER("Selected unit is not Engineer!"),
    NOT_TUNNELLER("Selected unit is not Tunneller!"),
    ;
    private String output;

    private Response(String output) {
        this.output = output;
    }

    public String getOutput() {
        return this.output;
    }
}
