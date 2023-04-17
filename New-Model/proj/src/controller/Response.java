package controller;

public enum Response {
    //register
    USERNAME_EMPTY("Username field is empty!"),
    PASSWORD_EMPTY("Password field is empty!"),
    NICKNAME_EMPTY("Nickname field is empty!"),
    EMAIL_EMPTY("Email field is empty!"),
    INVALID_USERNAME_FORMAT("Username's format is wrong! You can only use English letters and underscore."),
    INVALID_NICKNAME_FORMAT("Nickname's format is wrong! You can only use English letters and underscore."),
    REPETITIVE_USERNAME("A player with this username exists!"),
    WEAK_PASSWORD("Your password is weak! Your password requires at least 6 letters including capital and small letters and digits and a random character!"),
    WRONG_CONFIRMATION_PASSWORD("Confirmation password and password are not the same!"),
    REPETITIVE_EMAIL("A player with this email exists!"),
    INVALID_EMAIL_FORMAT("Your Email's format is wrong!"),
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

    ;
    private String output;

    private Response(String output) {
        this.output = output;
    }

    public String getOutput() {
        return this.output;
    }
}
