package Model.Field;

import view.Enums.ConsoleColors;

public enum flagColors {
    RED(ConsoleColors.TEXT_RED),
    BLUE(ConsoleColors.TEXT_BLUE),
    GREEN(ConsoleColors.TEXT_GREEN),
    BLACK(ConsoleColors.TEXT_BLACK);
    private String color;

    flagColors(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
