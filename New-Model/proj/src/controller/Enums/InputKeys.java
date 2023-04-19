package controller.Enums;

import java.util.ArrayList;
import java.util.Arrays;

public enum InputKeys {
    CREATE_USER(new ArrayList<>(Arrays.asList("u", "n", "p", "e", "s"))),
    PICK_QUESTION(new ArrayList<>(Arrays.asList("q", "a", "c"))),

    ;
    private ArrayList<String> keys;

    private InputKeys(ArrayList<String> keys) {
        this.keys = keys;
    }

    public ArrayList<String> getKeys() {
        return this.keys;
    }
}
