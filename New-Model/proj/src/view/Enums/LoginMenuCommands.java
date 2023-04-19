package view.Enums;

public enum LoginMenuCommands {

    PICK_SECURITY(""),
    LOGIN("^user login (?<loginInfo>.+)$"),
    FORGOT_PASSWORD(""),
    LOGOUT("")
    ;

    private String regex;
    private LoginMenuCommands(String regex){
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
