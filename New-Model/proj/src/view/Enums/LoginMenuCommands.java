package view.Enums;

public enum LoginMenuCommands {

    PICK_SECURITY(""),
    LOGIN(""),
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
