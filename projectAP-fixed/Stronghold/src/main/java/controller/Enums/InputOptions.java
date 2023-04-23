package controller.Enums;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public enum InputOptions {
    CREATE_USER(new ArrayList<>(Arrays.asList("u", "n", "p", "e", "s"))),
    PICK_QUESTION(new ArrayList<>(Arrays.asList("q", "a", "c"))),
    LOGIN_USER(new ArrayList<>(Arrays.asList("u", "p"))),
    CHANGE_PASSWORD(new ArrayList<>(Arrays.asList("o", "n"))),
    //map
    COORDINATES(new ArrayList<>(Arrays.asList("x", "y"))),
    SET_TEXTURE(new ArrayList<>(Arrays.asList("x", "y", "t"))),
    SET_TEXTURE_RECTANGLE(new ArrayList<>(Arrays.asList("x1", "y1", "x2", "y2", "t"))),
    DROP_ROCK(new ArrayList<>(Arrays.asList("x", "y", "d"))),
    DROP_TREE(new ArrayList<>(Arrays.asList("x", "y", "t"))),
    DROP_UNIT(new ArrayList<>(Arrays.asList("x", "y", "t", "c"))),

    ;
    private ArrayList<String> keys;

    private InputOptions(ArrayList<String> keys) {
        this.keys = keys;
    }

    public ArrayList<String> getKeys() {
        return this.keys;
    }
}
