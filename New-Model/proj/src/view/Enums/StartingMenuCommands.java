package view.Enums;

public enum StartingMenuCommands {
    LOGIN_MENU("login menu"),
    SIGNUP_MENU("signup menu"),
    EXIT("exit"),
    ;
    private String regex;

    StartingMenuCommands(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
