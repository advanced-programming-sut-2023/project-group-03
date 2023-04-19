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
    SUCCESSFULL_REGISTER("You got signed up successfully!"),
    //login
    UNKNOWN_USERNAME("There is no player with this username!"),
    INVALID_PASSWORD("Your password is wrong!"),
    LOGIN("User logged in successfully!"),
    LOGOUT("User logged out successfully!"),
    //profile
    //most of the errors are same as login and their response are the same...
    WRONG_CURRENT_PASSWORD("Your old password is wrong!"),
    REPETITIVE_PASSWORD("New password and the old password are the same!"),
    CHANGE_USERNAME("Your username changed successfully!"),
    CHANGE_NICKNAME("Your nickname changed successfully!"),
    CHANGE_PASSWORD("Your password changed successfully!"),
    CHANGE_EMAIL("Your email changed successfully!"),
    CHANGE_SLOGAN("Your slogan changed successfully!"),
    REMOVE_SLOGAN("Your slogan got cleared!"),
    //kingdom
    INVALID_FOOD_RATE("Invalid food rate! -3 < food rate < 3"),
    INVALID_TAX_RATE("Invalid tax rate! -4 < tax rate < 9"),
    INVALID_FEAR_RATE("Invalid fear rate! -6 < fear rate < 6"),
    ////buildings
    INVALID_LOCATION("Invalid x or y for location!"),
    //drop building
    INVALID_BUILDING_TYPE("There is no building type same as the given type!"),
    BUILDING_ALREADY_EXISTS("This field already has a building!"),
    //select building
    NO_BUILDING_EXIST("There is no building here!"),
    BUILDING_OWNERSHIP_PROBLEM("You don't own this building!"),
    //making units
    NOT_ENOUGH_RESOURCES("There is not enough resources to make the given unit."),
    NOT_ENOUGH_POPULATION("You don't have enough population to make the given unit."),
    INVALID_TROOP_TYPE("There is no soldier type same as the given type!"),
    //repair
    NOT_ENOUGH_STONE("There is not enough resources to repair!"),
    UNABLE_TO_REPAIR("You can't repair right now. Enemies' soldiers are too close!"),
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
