package controller.gameControllers;

import Model.Buildings.Building;
import Model.Field.*;
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
    private int gameWidth = 10;
    private int gameLength = gameWidth * 2;
    
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

        if (x > gameMap.getSize() - 10 || x < 10) return INVALID_X_MAP.getOutput();
        if (y > gameMap.getSize() - 10 || y < 10) return INVALID_Y_MAP.getOutput();

        if (x > gameMap.getSize() - gameLength || x < gameLength) return INVALID_X_MAP.getOutput();
        if (y > gameMap.getSize() - gameWidth || y < gameWidth) return INVALID_Y_MAP.getOutput();

        gameMap.setCenter(gameMap.getMap()[x][y]);
        return SUCCESSFUL_SHOW_MAP.getOutput();
    }
    public String moveMap(Matcher matcher) {
        String verticalDir = matcher.group("verticalDir");
        int verticalNum = Integer.parseInt(matcher.group("verticalNum"));
        String horizontalDir = matcher.group("horizontalDir");
        int horizontalNum = Integer.parseInt(matcher.group("horizontalNum"));

        if (!(verticalDir.equals("up") || verticalDir.equals("down"))) return INVALID_VERTICAL_DIRECTION.getOutput();
        if (!(horizontalDir.equals("right") || horizontalDir.equals("left"))) return INVALID_HORIZONTAL_DIRECTION.getOutput();

        Tile centerTile = gameMap.getCenter();

        int finalX = centerTile.getRowNum() + (verticalDir.equals("up") ? verticalNum : -verticalNum);
        int finalY = centerTile.getColumnNum() + (horizontalDir.equals("right") ? horizontalNum : -horizontalNum);

        if (finalX > gameMap.getSize() - gameLength || finalX < gameLength) return INVALID_FINAL_X_VALUE.getOutput();
        if (finalY > gameMap.getSize() - gameWidth || finalY < gameWidth) return INVALID_FINAL_Y_VALUE.getOutput();

        gameMap.setCenter(gameMap.getMap()[finalX][finalY]);
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

        gameMap.getMap()[x][y] = new Tile(height.GROUND, Texture.GROUND);
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
}
