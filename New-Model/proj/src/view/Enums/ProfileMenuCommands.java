package view.Enums;

public enum ProfileMenuCommands {

    CHANGE_USERNAME(""),
    CHANGE_NICKNAME(""),
    CHANGE_PASSWORD("^profile change password (?<passwordInfo>.+)$"),
    CHANGE_EMAIL(""),
    CHANGE_SLOGAN(""),
    REMOVE_SLOGAN(""),
    DISPLAY_HIGHSCORE(""),
    DISPLAY_RANK(""),
    DISPLAY_SLOGAN(""),
    DISPLAY_ALL(""),
    START_GAME("")
    ;

    String regex;
    private ProfileMenuCommands(String regex){
        this.regex = regex;
    }

    public String getRegex() {
        return this.regex;
    }
}
