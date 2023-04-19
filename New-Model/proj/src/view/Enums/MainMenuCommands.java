package view.Enums;

public enum MainMenuCommands {
    START_GAME("start game"),
    MAP_MENU("map menu"),
    PROFILE_MENU("profile menu"),
    BACK("back to start menu"),
    ;
    private String regex;

    MainMenuCommands(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
