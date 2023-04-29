package controller.gameControllers;

import Model.Field.GameMap;
import controller.Controller;

import java.util.HashMap;

import static controller.Enums.Response.INVALID_X_MAP;
import static controller.Enums.Response.INVALID_Y_MAP;

public class GeneralGameController extends Controller {
    protected GameMap gameMap;

    public GeneralGameController(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    protected String checkCoordinates(HashMap<String, String> coordinates, String xString, String yString) {
        int x, y;

        try {
            x = Integer.parseInt(coordinates.get(xString)) - 1;
        } catch (NumberFormatException e) {
            return INVALID_X_MAP.getOutput();
        }
        try {
            y = Integer.parseInt(coordinates.get(yString)) - 1;
        } catch (NumberFormatException e) {
            return INVALID_Y_MAP.getOutput();
        }

        if (x > gameMap.getSize() || x < 0) return INVALID_X_MAP.getOutput();
        if (y > gameMap.getSize() || y < 0) return INVALID_Y_MAP.getOutput();

        return null;
    }

}

