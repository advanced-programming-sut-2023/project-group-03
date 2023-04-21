package controller.gameControllers;

import Model.Field.Texture;
import controller.Controller;

import java.util.HashMap;
import java.util.regex.Matcher;
import static controller.Enums.InputOptions.*;
import static controller.Enums.Response.*;
import Model.Field.GameMap;

public class MapController extends Controller {
    GameMap gameMap;
    public MapController(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    private String checkCoordinates(HashMap<String, String> coordinates, String xString, String yString) {
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

    public String showMap(Matcher matcher) {
        String mapCoordinates = matcher.group("mapCoordinates");
        HashMap<String, String> coordinates = getOptions(SHOW_MAP.getKeys(), mapCoordinates);
        String error = coordinates.get("error");
        if (error != null) return error;

        String checkCoordinatesResult = checkCoordinates(coordinates, "x", "y");
        if (checkCoordinatesResult != null) return checkCoordinatesResult;

        int x = Integer.parseInt(coordinates.get("x")) - 1;
        int y = Integer.parseInt(coordinates.get("y")) - 1;

        //show the given map
        return SUCCESSFUL_SHOW_MAP.getOutput();
    }
    public void moveMap(Matcher matcher) {

    }
    public void showDetails(Matcher matcher) {

    }

    public String checkSetTextureRequirements(int x, int y) {
        return "";
    }

    public String setTexture(Matcher matcher) {
        String textureInfo = matcher.group("setTextureInfo");
        HashMap<String, String> infoMap = getOptions(SET_TEXTURE.getKeys(), textureInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String checkCoordinates = checkCoordinates(infoMap, "x", "y");
        if (checkCoordinates != null) return  checkCoordinates;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        Texture texture = Texture.getByName(infoMap.get("t"));
        if (texture == null) return INVALID_TEXTURE.getOutput();


        //any kind of texture including oil and water ...
        return "";
    }

    public void clearField(Matcher matcher) {

    }

    public String checkDropRockRequirements(int x, int y) {
        //check direction and position
        return "";
    }

    public String dropRock(Matcher matcher) {
        return "";
    }

    public String checkDropTreeRequirements(int x, int y) {
        //check type of tree
        //check texture of the ground
        return "";
    }

    public String dropTree(Matcher matcher) {
        return "";
    }
}
