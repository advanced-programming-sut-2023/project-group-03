package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import static controller.ControllerFunctions.*;
import static controller.Enums.ControllerCommands.*;
import static controller.Enums.Response.*;

public class Controller {
    protected HashMap<String, String> setHashMapKeys(ArrayList<String> keys) {
        HashMap<String, String> infoMap = new HashMap<>();
        for (String key : keys) infoMap.put(key, null);
        infoMap.put("error", null);
        return infoMap;
    }

    protected HashMap<String, String> getOptions(ArrayList<String> keys, String input) {
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
        } while(matcher.find());

        for (String key : keys) {
            if (infoMap.get(key) == null) {
                infoMap.put("error", FIELD_FORGOTTEN.getOutput() + key);
                return infoMap;
            }
        }//forgot to enter a field

        return infoMap;
    }
}
