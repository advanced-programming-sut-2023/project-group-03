package controller.Enums;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public enum InputOptions {
    CREATE_USER(new ArrayList<>(Arrays.asList("u", "n", "p", "e", "s"))),
    PICK_QUESTION(new ArrayList<>(Arrays.asList("q", "a", "c"))),
    LOGIN_USER(new ArrayList<>(Arrays.asList("u", "p"))),
    CHANGE_PASSWORD(new ArrayList<>(Arrays.asList("o", "n"))),

    ;
    private ArrayList<String> keys;

    private InputOptions(ArrayList<String> keys) {
        this.keys = keys;
    }

    public ArrayList<String> getKeys() {
        return this.keys;
    }
}