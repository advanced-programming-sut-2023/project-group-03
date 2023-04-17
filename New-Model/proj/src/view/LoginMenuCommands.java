package view;

public enum LoginMenuCommands {

    PICK_SECURITY(""),
    LOGIN(""),
    FORGOT_PASSWORD(""),
    LOGOUT("")
    ;

    String regex;
    private LoginMenuCommands(String regex){
        this.regex = regex;
    }
}
