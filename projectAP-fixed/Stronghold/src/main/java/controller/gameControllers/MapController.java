package controller.gameControllers;

import Model.Buildings.Building;
import Model.Field.*;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;
import Model.Units.Enums.TroopTypes;
import Model.Units.Unit;
import controller.Controller;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import static controller.Enums.InputOptions.*;
import static controller.Enums.Response.*;

public class MapController extends Controller {
    private final int gameWidth = 3;
    private final int gameLength = gameWidth * 2;
    
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

        Tile targetTile = gameMap.getMap()[x][y];
        if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();

        targetTile.setTexture(texture);
        return SUCCESSFUL_SETTEXTURE.getOutput();
    }

    public String setTextureRectangle(Matcher matcher) {
        String textureInfo = matcher.group("setTextureInfo");
        HashMap<String, String> infoMap = getOptions(SET_TEXTURE_RECTANGLE.getKeys(), textureInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String checkCoordinates = checkCoordinates(infoMap, "x1", "y1");
        if (checkCoordinates != null) return  checkCoordinates;
        String checkCoordinates2 = checkCoordinates(infoMap, "x2", "y2");
        if (checkCoordinates2 != null) return  checkCoordinates2;

        int row1 = Integer.parseInt(infoMap.get("x1")) - 1;
        int col1 = Integer.parseInt(infoMap.get("y1")) - 1;
        int row2 = Integer.parseInt(infoMap.get("x2")) - 1;
        int col2 = Integer.parseInt(infoMap.get("y2")) - 1;

        if (row1 > row2 || col1 > col2) return INVALID_RECTANGLE.getOutput();

        for (int row = row1; row < row2; row++) {
            for (int col = col1; col < col2; col++) {
                if (gameMap.getMap()[row][col].getBuilding() != null) return BUILDING_EXIST_RECTANGLE.getOutput();
            }
        }

        Texture targetTexture = Texture.getByName(infoMap.get("t"));
        if (targetTexture == null) return INVALID_TEXTURE.getOutput();

        for (int row = row1; row < row2; row++) {
            for (int col = col1; col < col2; col++) {
                gameMap.getMap()[row][col].setTexture(targetTexture);
            }
        }

        return SUCCESSFUL_SETTEXTURE.getOutput();
    }

    public String clearField(Matcher matcher) {
        String mapCoordinates = matcher.group("coordinatesInfo");
        HashMap<String, String> coordinates = getOptions(COORDINATES.getKeys(), mapCoordinates);
        String error = coordinates.get("error");
        if (error != null) return error;

        String checkCoordinatesResult = checkCoordinates(coordinates, "x", "y");
        if (checkCoordinatesResult != null) return checkCoordinatesResult;

        int x = Integer.parseInt(coordinates.get("x")) - 1;
        int y = Integer.parseInt(coordinates.get("y")) - 1;

        Tile targetTile = gameMap.getMap()[x][y];

        for (Unit unit : targetTile.getUnits()) unit.remove();
        Building building = targetTile.getBuilding();
        if (building != null) building.remove();

        Tile newTile = new Tile(height.GROUND, Texture.GROUND);
        gameMap.getMap()[x][y] = newTile;
        newTile.setRowNum(x);
        newTile.setColumnNum(y);

        return SUCCESSFUL_CLEAR_TILE.getOutput();
    }

    public String dropRock(Matcher matcher) {
        String dropRockInfo = matcher.group("dropRockInfo");
        HashMap<String, String> infoMap = getOptions(DROP_ROCK.getKeys(), dropRockInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String checkCoordinatesResult = checkCoordinates(infoMap, "x", "y");
        if (checkCoordinatesResult != null) return checkCoordinatesResult;
        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        String direction = infoMap.get("d");
        String allDirections = "news";
        if (direction.equals("r")) infoMap.put("r", String.valueOf(allDirections.charAt(new Random().nextInt(allDirections.length()))));
        else if (direction.length() > 1 || !allDirections.contains(direction)) return INVALID_ROCK_DIRECTION.getOutput();

        Tile targetTile = gameMap.getMap()[x][y];

        String checkDropMazafazaResult = checkDropMazafaza(targetTile);
        if (checkDropMazafazaResult != null) return checkDropMazafazaResult;

        targetTile.setMazafaza(mazafaza.getMazafazaByName("rock" + direction.toUpperCase()));
        return SUCCESSFUL_DROP_ROCK.getOutput();
    }

    public String dropTree(Matcher matcher) {
        String dropRockInfo = matcher.group("dropTreeInfo");
        HashMap<String, String> infoMap = getOptions(DROP_TREE.getKeys(), dropRockInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String checkCoordinatesResult = checkCoordinates(infoMap, "x", "y");
        if (checkCoordinatesResult != null) return checkCoordinatesResult;
        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        mazafaza mazafazaTarget = mazafaza.getMazafazaByName(infoMap.get("t"));
        if (mazafazaTarget == null) return INVALID_TREE.getOutput();

        Tile targetTile = gameMap.getMap()[x][y];

        String checkDropMazafazaResult = checkDropMazafaza(targetTile);
        if (checkDropMazafazaResult != null) return checkDropMazafazaResult;

        targetTile.setMazafaza(mazafazaTarget);

        return SUCCESSFUL_DROP_TREE.getOutput();
    }

    private String checkDropMazafaza(Tile targetTile) {
        if (targetTile.getMazafaza() != null) return ROCK_EXIST.getOutput();
        if (targetTile.getUnits().size() > 0) return UNIT_EXIST.getOutput();
        if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
        return null;
    }

    public String setOwner(Matcher matcher, Player player) {
        return null;
    }
}
