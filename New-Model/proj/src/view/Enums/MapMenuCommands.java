package view.Enums;

import java.util.regex.Pattern;

public enum MapMenuCommands {
    MOVE("map ((?<verticalDir>\\w+) (?<verticalNum>\\d+))? " +
            "((?<horizontalDir>\\w+) (?<horizontalNum>\\d+))?"),
    SET_TEXTURE("settexture (?=.*-x\\s+(?<x>\\d+))" +
            "(?=.*-y\\s+(?<y>\\d+))(?=.*-type\\s+(?<type>\\S+))"),
    SET_TEXTURE_REC("settexture (?=.*-x1\\s+(?<x1>\\d+))(?=.*-x2\\s+(?<x2>\\d+))" +
            "(?=.*-y1\\s+(?<y1>\\d+))(?=.*-y2\\s+(?<y2>\\d+))(?=.*-type\\s+(?<type>\\S+))"),
    CLEAR("clear (?=.*-x\\s+(?<x>\\d+))(?=.*-y\\s+(?<y>\\d+))"),
    DROPROCK("droprock (?=.*-x\\s+(?<x>\\d+))" +
            "(?=.*-y\\s+(?<y>\\d+))(?=.*-d\\s+(?<direction>\\S+))"),
    DROPTREE("droptree (?=.*-x\\s+(?<x>\\d+))" +
            "(?=.*-y\\s+(?<y>\\d+))(?=.*-t\\s+(?<type>\\S+))"),
    DROPBUILDING("dropbuilding (?=.*-x\\s+(?<x>\\d+))" +
            "(?=.*-y\\s+(?<y>\\d+))(?=.*-t\\s+(?<type>\\S+))"),
    DROPUNIT("dropunit (?=.*-x\\s+(?<x>\\d+))" +
            "(?=.*-y\\s+(?<y>\\d+))(?=.*-t\\s+(?<type>\\S+))(?=.*-c\\s+(?<count>\\d+))"),
    ;
    private String regex;
    MapMenuCommands(String regex) {
        this.regex = regex;
    }
}
