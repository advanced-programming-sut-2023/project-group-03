package controller.Enums;

import java.util.ArrayList;
import java.util.Arrays;

public enum InputOptions {
    CREATE_USER(new ArrayList<>(Arrays.asList("u", "n", "p", "e", "s"))),
    PICK_QUESTION(new ArrayList<>(Arrays.asList("q", "a", "c"))),
    LOGIN_USER(new ArrayList<>(Arrays.asList("u", "p"))),
    CHANGE_PASSWORD(new ArrayList<>(Arrays.asList("o", "n"))),
    //map
    COORDINATES(new ArrayList<>(Arrays.asList("x", "y"))),
    COORDINATES_RECTANGULAR(new ArrayList<>(Arrays.asList("x1", "y1", "x2", "y2"))),
    SET_TEXTURE(new ArrayList<>(Arrays.asList("x", "y", "t"))),
    SET_TEXTURE_RECTANGLE(new ArrayList<>(Arrays.asList("x1", "y1", "x2", "y2", "t"))),
    DROP_ROCK(new ArrayList<>(Arrays.asList("x", "y", "d"))),
    DROP_TREE(new ArrayList<>(Arrays.asList("x", "y", "t"))),
    DROP_UNIT(new ArrayList<>(Arrays.asList("x", "y", "t", "c"))),
    DROP_BUILDING(new ArrayList<>(Arrays.asList("x", "y", "t"))),
    BUY_SELL(new ArrayList<>(Arrays.asList("i", "a"))),
    //building
    BUILD_TOWER(new ArrayList<>(Arrays.asList("x", "y", "t"))),
    BUILD_WALL(new ArrayList<>(Arrays.asList("x", "y", "t"))),
    BUILD_BARRACKS(new ArrayList<>(Arrays.asList("x", "y", "t"))),
    BUILD_INVENTORY(new ArrayList<>(Arrays.asList("x", "y", "t"))),
    BUILD_REST(new ArrayList<>(Arrays.asList("x", "y", "t"))),
    BUILD_GENERATOR(new ArrayList<>(Arrays.asList("x", "y", "t"))),
    BUILD_STONE_GATE(new ArrayList<>(Arrays.asList("x", "y", "t"))),
    BUILD_DRAWBRIDGE(new ArrayList<>(Arrays.asList("x", "y", "d"))),
    //unit
    ADD_TROOP(new ArrayList<>(Arrays.asList("t", "a"))),
    //kingdom
    CHANGE_RATE(new ArrayList<>(Arrays.asList("r"))),
    TRADE_REQUEST(new ArrayList<>(Arrays.asList("t", "a", "p", "m"))),
    TRADE_ACCEPT(new ArrayList<>(Arrays.asList("i", "m"))),
    ;
    private ArrayList<String> keys;

    private InputOptions(ArrayList<String> keys) {
        this.keys = keys;
    }

    public ArrayList<String> getKeys() {
        return this.keys;
    }
}
