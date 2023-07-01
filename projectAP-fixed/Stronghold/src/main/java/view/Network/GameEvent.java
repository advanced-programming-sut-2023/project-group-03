package view.Network;

import Model.GamePlay.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;

import static controller.ControllerFunctions.getMatcher;
import static controller.ControllerFunctions.unwrapQuotation;
import static controller.Enums.ControllerCommands.MULTI_OPTION_FIELD_FORMAT;
import static controller.Enums.ControllerCommands.OPTION_FIELD;
import static controller.Enums.Response.*;

public enum GameEvent {
    ANNOUNCE_LIST("list of players", new ArrayList<>(Arrays.asList("m", "1", "2", "3", "4"))),
    MAKE_GAME("make game", new ArrayList<>(Arrays.asList("m", "u"))),
    INTRODUCE("introduce", new ArrayList<>(Arrays.asList("u"))),
    START_GAME("start game", null),
    JOIN_T0_GAME("join to game", new ArrayList<>(Arrays.asList("u"))),
    USER_JOINED_TO_NETWORK("user joined to net work", null),

    USER_SUCCESSFULLY_CREATED("user successfully created", null),
    DROP_BUILDING("drop building", new ArrayList<>(Arrays.asList("x", "y", "t"))),
    DROP_UNIT("drop unit", new ArrayList<>(Arrays.asList("x", "y", "t", "c"))),
    REJECT_JOIN("reject join", null),
    DELETE_BUILDING("delete building", new ArrayList<>(Arrays.asList("x", "y"))),
    ;

    GameEvent(String name, ArrayList<String> keys) {
        this.keys = keys;
        this.name = name;
    }

    private final ArrayList<String> keys;

    public void fixMessage(String... args) {
        message = new String();
        message += name;
        if (keys == null) {
            return;
        }
        int counter = 0;
        for (String key : keys) {
            if (args.length <= counter) {
                return;
            }
            message += " -" + key + " " + args[counter];
            counter++;
        }
    }

    String name;
    String message;
    int code;

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<String> getKeys() {
        return keys;
    }

    public static GameEvent getEvent(String content) {
        return GameEvent.JOIN_T0_GAME;
    }
    protected static HashMap<String, String> setHashMapKeys(ArrayList<String> keys) {
        HashMap<String, String> infoMap = new HashMap<>();
        for (String key : keys) infoMap.put(key, null);
        infoMap.put("error", null);
        return infoMap;
    }

    public static String makeClean(String input) {
        String output = new String();
        boolean flag = false;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '-') {
                flag = true;
            }
            if (flag == true) {
                output += input.charAt(i);
            }
        }
        return output;
    }

    public static HashMap<String, String> getOptions(ArrayList<String> keys, String input) {
        HashMap<String, String> infoMap = setHashMapKeys(keys);
        Matcher matcher = getMatcher(input, MULTI_OPTION_FIELD_FORMAT.getRegex());
        //check the format
        if (matcher == null) {
            infoMap.put("error", INVALID_COMMAND.getOutput());
            return infoMap;
        }

        String option, optionInfo;
        matcher = getMatcher(input, OPTION_FIELD.getRegex());

        do {
            option = matcher.group("option");
            optionInfo = matcher.group("optionInfo");
            if (!keys.contains(option)) {
                infoMap.put("error", INVALID_KEY.getOutput() + option);
                return infoMap;
            } //invalid key
            if (infoMap.get(option) != null) {
                infoMap.put("error", REPETITIVE_OPTION.getOutput() + option);
            }//repetitive key
            if (optionInfo == null) {
                infoMap.put("error", FIELD_EMPTY.getOutput() + option);
                return infoMap;
            }//empty field

            optionInfo = unwrapQuotation(optionInfo);//fixing option info if it has ""
            infoMap.put(option, optionInfo);
        } while (matcher.find());

        for (String key : keys) {
            if (infoMap.get(key) == null) {
                infoMap.put("error", FIELD_FORGOTTEN.getOutput() + key);
                return infoMap;
            }
        }//forgot to enter a field

        return infoMap;
    }
}