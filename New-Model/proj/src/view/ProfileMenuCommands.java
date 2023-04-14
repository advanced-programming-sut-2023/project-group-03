package view;

public enum ProfileMenuCommands {

    CHANGE_USERNAME(""),
    CHANGE_NICKNAME(""),
    CHANGE_PASSWORD(""),
    CHANGE_EMAIL(""),
    CHANGE_SLOGAN(""),
    REMOVE_SLOGAN(""),
    DISPLAY_HIGHSCORE(""),
    DISPLAY_RANK(""),
    DISPLAY_SLOGAN(""),
    DISPLAY_ALL("")
    ;

    String regex;
    private ProfileMenuCommands(String regex){
        this.regex = regex;
    }

}
