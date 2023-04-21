package view.Enums;

public enum LoginMenuCommands {
    BACK("back to starting menu"),
    USER_LOGIN("user login(?=.*-u\\s+(?<username>\\S+))(?=.*-p\\s+(?<password>\\S+))(?<stay>\\s+--stay-logged-in)?"),
    PICK_SECURITY(""),
    LOGIN("^user login (?<loginInfo>.+)$"),
    FORGOT_PASSWORD("forgom my password -u (?<username>\\S+)"),
    LOGOUT("user logged out"),
    ;

    private String regex;
    private LoginMenuCommands(String regex){
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
