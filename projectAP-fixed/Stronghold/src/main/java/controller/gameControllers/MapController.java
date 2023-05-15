package controller.gameControllers;

import Model.Buildings.Building;
import Model.Field.*;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;
import Model.Units.Unit;
import Model.buffers.MapBuffer;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;

import static controller.Enums.InputOptions.*;
import static controller.Enums.Response.*;

public class MapController extends GeneralGameController {
    private final int gameWidth = 3;
    private final int gameLength = gameWidth * 2;
    private final BuildingController buildingController = new BuildingController(gameMap);
    private final UnitController unitController = new UnitController(gameMap);

    public MapController(GameMap gameMap) {
        super(gameMap);
    }

    public String setTexture(Matcher matcher) {
        String textureInfo = matcher.group("setTextureInfo");
        HashMap<String, String> infoMap = getOptions(SET_TEXTURE.getKeys(), textureInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String checkCoordinates = checkCoordinates(infoMap, "x", "y");
        if (checkCoordinates != null) return checkCoordinates;

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
        if (checkCoordinates != null) return checkCoordinates;
        String checkCoordinates2 = checkCoordinates(infoMap, "x2", "y2");
        if (checkCoordinates2 != null) return checkCoordinates2;

        int row1 = Integer.parseInt(infoMap.get("x1")) - 1;
        int col1 = Integer.parseInt(infoMap.get("y1")) - 1;
        int row2 = Integer.parseInt(infoMap.get("x2")) - 1;
        int col2 = Integer.parseInt(infoMap.get("y2")) - 1;

        if (row1 > row2 || col1 > col2) return INVALID_RECTANGLE.getOutput();

        for (int row = row1; row <= row2; row++) {
            for (int col = col1; col <= col2; col++) {
                if (gameMap.getMap()[row][col].getBuilding() != null) return BUILDING_EXIST_RECTANGLE.getOutput();
            }
        }

        Texture targetTexture = Texture.getByName(infoMap.get("t"));
        if (targetTexture == null) return INVALID_TEXTURE.getOutput();

        for (int row = row1; row <= row2; row++) {
            for (int col = col1; col <= col2; col++) {
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

        Tile newTile = new Tile(Height.GROUND, Texture.GROUND);
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
        if (direction.equals("r"))
            infoMap.put("r", String.valueOf(allDirections.charAt(new Random().nextInt(allDirections.length()))));
        else if (direction.length() > 1 || !allDirections.contains(direction))
            return INVALID_ROCK_DIRECTION.getOutput();

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

    public String dropUnit(Matcher matcher, Player player) {
        return unitController.addUnitMatcherHandler(matcher, player, null);
    }

    public String dropBuilding(Matcher matcher, Player player) {
        return buildingController.dropBuildingMatcherHandler(matcher, player);
    }

    public String setOwner(Matcher matcher, Player player) {
        String setOwnerInfo = matcher.group("setOwnerInfo");
        HashMap<String, String> infoMap = getOptions(COORDINATES_RECTANGULAR.getKeys(), setOwnerInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String checkCoordinates = checkCoordinates(infoMap, "x1", "y1");
        if (checkCoordinates != null) return checkCoordinates;
        String checkCoordinates2 = checkCoordinates(infoMap, "x2", "y2");
        if (checkCoordinates2 != null) return checkCoordinates2;

        int row1 = Integer.parseInt(infoMap.get("x1")) - 1;
        int col1 = Integer.parseInt(infoMap.get("y1")) - 1;
        int row2 = Integer.parseInt(infoMap.get("x2")) - 1;
        int col2 = Integer.parseInt(infoMap.get("y2")) - 1;

        if (row1 > row2 || col1 > col2) return INVALID_RECTANGLE.getOutput();

        for (int row = row1; row <= row2; row++) {
            for (int col = col1; col <= col2; col++) {
                if (gameMap.getMap()[row][col].getBuilding() != null) return BUILDING_EXIST_RECTANGLE.getOutput();
            }
        }

        for (int row = row1; row <= row2; row++) {
            for (int col = col1; col <= col2; col++) {
                gameMap.getMap()[row][col].setOwner(player);
            }
        }

        return SUCCESSFUL_SET_OWNER.getOutput();
    }

    public String saveMap(GameMap map) {
        File file = new File("src/main/resources/maps/" + map.getName() + ".json");
//        file.mkdirs();//todo
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(new Gson().toJson(map));
            fileWriter.close();
            return "map saved";
        } catch (IOException e) {
            return "save error";
        }
    }

    public String saveBufferMap(GameMap map) {
        File file = new File("src/main/resources/maps/" + map.getName() + ".json");
        MapBuffer mapBuffer = new MapBuffer(map);
//        file.mkdirs();//todo
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(new Gson().toJson(mapBuffer));
            fileWriter.close();
            return "map saved";
        } catch (IOException e) {
            return "save error";
        }
    }

    public static GameMap loadMap(File file) {
        GameMap gameMap = null;
        try {
            String json = new String(Files.readAllBytes(Paths.get(file.getPath())));
            gameMap = new Gson().fromJson(json, GameMap.class);
        } catch (IOException e) {
            System.out.println("erroorrr");
        }
        return gameMap;
    }

    public static GameMap loadbufferMap(File file) {
        GameMap gameMap = null;
        MapBuffer mapBuffer = null;
        try {
            String json = new String(Files.readAllBytes(Paths.get(file.getPath())));
            mapBuffer = new Gson().fromJson(json, MapBuffer.class);
            gameMap = mapBuffer.HandleMap();
        } catch (IOException e) {
            System.out.println("erroorrr");
        }
        return gameMap;
    }

    public static void gsonHandlerMap(GameMap map) {
        for (int i = 0; i < map.getMap().length; i++) {
            for (int j = 0; j < map.getMap().length; j++) {

                findOwner(map.getMap()[i][j], map);
            }
        }
        for (int i = 0; i < map.getDrawables().size(); i++) {
            findOwner(map.getDrawables().get(i), map);
        }

    }

    public static void findOwner(Tile tile, GameMap map) {
        for (int i = 0; i < map.getNumberOfPlayers(); i++) {
            if (map.getPlayers()[i] == null) {
                return;
            }
            if (tile.getOwner().equals(map.getPlayers()[i])) {
                tile.setOwner(map.getPlayers()[i]);
            }
        }
    }

    public static void findOwner(Drawable drawable, GameMap map) {
        for (int i = 0; i < map.getPlayers().length; i++) {
            if (map.getPlayers()[i] == null) {
                return;
            }
            if (drawable.getOwner().equals(map.getPlayers()[i])) {
                drawable.setOwner(map.getPlayers()[i]);
            }
        }
    }

}
