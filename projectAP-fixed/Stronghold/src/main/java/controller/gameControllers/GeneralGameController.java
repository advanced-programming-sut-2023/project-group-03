package controller.gameControllers;

import Model.Buildings.*;
import Model.Buildings.Defending.*;
import Model.Buildings.Defending.Enums.GateTypes;
import Model.Buildings.Defending.Enums.TowerTypes;
import Model.Buildings.Defending.Enums.TrapsTypes;
import Model.Buildings.Enums.*;
import Model.Field.Direction;
import Model.Field.GameMap;
import Model.Field.RegularTextureGroups;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;
import Model.Units.Enums.TroopTypes;
import Model.Units.Unit;
import controller.Controller;

import java.util.HashMap;
import java.util.regex.Matcher;

import static controller.Enums.InputOptions.COORDINATES;
import static controller.Enums.Response.*;
import static controller.Enums.Response.SUCCESSFUL_MOVE_MAP;

public class GeneralGameController extends Controller {
    protected GameMap gameMap;
    private final int gameWidth = 3;
    private final int gameLength = gameWidth * 2;

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

        if (x >= gameMap.getSize() || x < 0) return INVALID_X_MAP.getOutput();
        if (y >= gameMap.getSize() || y < 0) return INVALID_Y_MAP.getOutput();

        return null;
    }

    public String showMap(Matcher matcher) {
        String mapCoordinates = matcher.group("coordinatesInfo");
        HashMap<String, String> coordinates = getOptions(COORDINATES.getKeys(), mapCoordinates);
        String error = coordinates.get("error");
        if (error != null) return error;

        String checkCoordinatesResult = checkCoordinates(coordinates, "x", "y");
        if (checkCoordinatesResult != null) return checkCoordinatesResult;

        int x = Integer.parseInt(coordinates.get("x")) - 1;
        int y = Integer.parseInt(coordinates.get("y")) - 1;

        if (x > gameMap.getSize() - gameLength || x < gameLength) return INVALID_X_MAP.getOutput();
        if (y > gameMap.getSize() - gameWidth || y < gameWidth) return INVALID_Y_MAP.getOutput();

        gameMap.setCenter(gameMap.getMap()[x][y]);
        return SUCCESSFUL_SHOW_MAP.getOutput();
    }

    public String moveMap(Matcher matcher) {
        String verticalDir = matcher.group("verticalDir");
        String verticalNumString = matcher.group("verticalNum");
        String horizontalDir = matcher.group("horizontalDir");
        String horizontalNumString = matcher.group("horizontalNum");

        int verticalNum = 1;
        int horizontalNum = 1;
        if (verticalNumString != null) {
            if (!verticalNumString.matches("\\d+")) {
                return INVALID_Y_MAP.getOutput();
            }
            verticalNum = Integer.parseInt(verticalNumString);
        }

        if (horizontalNumString != null) {
            if (!horizontalNumString.matches("\\d+")) {
                return INVALID_X_MAP.getOutput();
            }
            horizontalNum = Integer.parseInt(horizontalNumString);
        }

        Tile centerTile = gameMap.getCenter();
        int finalRow = centerTile.getRowNum(), finalColumn = centerTile.getColumnNum();

        if (verticalDir != null) {
            if (!(verticalDir.equals("up") || verticalDir.equals("down"))) return INVALID_VERTICAL_DIRECTION.getOutput();
            finalRow = centerTile.getRowNum() + (verticalDir.equals("up") ? verticalNum : -verticalNum);

        }

        if (horizontalDir != null) {
            if (!(horizontalDir.equals("right") || horizontalDir.equals("left"))) return INVALID_HORIZONTAL_DIRECTION.getOutput();
            finalColumn = centerTile.getColumnNum() + (horizontalDir.equals("right") ? horizontalNum : -horizontalNum);

        }

        if (finalRow > gameMap.getSize() - gameLength || finalRow < gameLength) return INVALID_FINAL_X_VALUE.getOutput();
        if (finalColumn > gameMap.getSize() - gameWidth || finalColumn < gameWidth) return INVALID_FINAL_Y_VALUE.getOutput();

        gameMap.setCenter(gameMap.getMap()[finalRow][finalColumn]);
        return SUCCESSFUL_MOVE_MAP.getOutput();
    }

    public String showDetails(Matcher matcher) {
        String mapCoordinates = matcher.group("coordinatesInfo");
        HashMap<String, String> coordinates = getOptions(COORDINATES.getKeys(), mapCoordinates);
        String error = coordinates.get("error");
        if (error != null) return error;

        String checkCoordinatesResult = checkCoordinates(coordinates, "x", "y");
        if (checkCoordinatesResult != null) return checkCoordinatesResult;

        int x = Integer.parseInt(coordinates.get("x")) - 1;
        int y = Integer.parseInt(coordinates.get("y")) - 1;
        Tile targetTile = gameMap.getMap()[x][y];
        String output = "";
        output += "texture: " + targetTile.getTexture().name() + "\n";
        if (targetTile.getTexture().getResource() != null)
            output += "resource: " + targetTile.getTexture().getResource() + "\n";

        HashMap<TroopTypes, Integer> troopTypesCounter = new HashMap<>();
        for (TroopTypes troopType : TroopTypes.values()) troopTypesCounter.put(troopType, 0);
        for (Unit unit : targetTile.getUnits()) {
            if (unit instanceof Troop) {
                Troop troop = (Troop) unit;
                troopTypesCounter.put(troop.getType(), troopTypesCounter.get(troop.getType()) + 1);
            }
        }

        for (TroopTypes troopType : TroopTypes.values()) {
            output += troopType.getName() + ": " + troopTypesCounter.get(troopType) + "\n";
        }

        //need to add building too
        return output;
    }
}