package controller.gameControllers;

import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Combat.*;
import Model.Units.Engineer;
import Model.Units.Enums.ThrowerTypes;
import Model.Units.Enums.TroopTypes;
import Model.Units.Unit;
import Model.Units.Worker;
import controller.Controller;
import view.Enums.ConsoleColors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

import static controller.Enums.InputOptions.COORDINATES;
import static controller.Enums.Response.*;

public class GeneralGameController extends Controller {
    protected GameMap gameMap;
    private static final int gameWidth = 3;
    private static int gameLength = 3 * gameWidth;

    public static void setGameWidth(int gameWidth) {
        gameWidth = gameWidth;
        gameLength = 3 * gameWidth;
    }

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

        if (verticalDir.equals("left") || verticalDir.equals("right")) {
            String tempDir = horizontalDir;
            int tempNum = horizontalNum;
            horizontalDir = verticalDir;
            horizontalNum = verticalNum;
            verticalDir = tempDir;
            verticalNum = tempNum;
        }

        if (verticalDir != null) {
            if (!(verticalDir.equals("up") || verticalDir.equals("down")))
                return INVALID_VERTICAL_DIRECTION.getOutput();
            finalRow = centerTile.getRowNum() + (verticalDir.equals("up") ? -verticalNum : verticalNum);

        }

        if (horizontalDir != null) {
            if (!(horizontalDir.equals("right") || horizontalDir.equals("left")))
                return INVALID_HORIZONTAL_DIRECTION.getOutput();
            finalColumn = centerTile.getColumnNum() + (horizontalDir.equals("right") ? horizontalNum : -horizontalNum);

        }

        if (finalRow > gameMap.getSize() - gameLength || finalRow < gameLength)
            return INVALID_FINAL_X_VALUE.getOutput();
        if (finalColumn > gameMap.getSize() - gameWidth || finalColumn < gameWidth)
            return INVALID_FINAL_Y_VALUE.getOutput();

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
        output += "texture: " + targetTile.getTexture().name() + " " + targetTile.getOwner().getUser().getNickname() + "\n";

        int workerAmount = 0;
        for (Unit unit : targetTile.getUnits()) {
            if (unit instanceof Worker) workerAmount++;
        }
        output += "worker amount: " + workerAmount + "\n";

        if (targetTile.getTexture().getResource() != null) {
            output += "resource: " + targetTile.getTexture().getResource() + "\n";
        } else {
            output += "resource: " + "nothing\n";
        }

        if (targetTile.getBuilding() == null) {
            output += "building: nothing\n";
        } else {
            output += "building: " + targetTile.getBuilding().getName() + "HP: " + targetTile.getBuilding().getHP() + "\n";
        }

        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < gameMap.getPlayers().length; i++) {
            if (gameMap.getPlayers()[i] != null) players.add(gameMap.getPlayers()[i]);
        }

        HashMap<Player, ArrayList<Unit>> unitsAcquisition = new HashMap<>();
        for (Player player : players) unitsAcquisition.put(player, new ArrayList<>());

        for (Unit unit : targetTile.getUnits()) {
            unitsAcquisition.get(unit.getOwner()).add(unit);
        }
        boolean shouldPrintSpecial = false;

        for (TroopTypes troopType : TroopTypes.values()) {
            for (Player player : players) {
                for (Unit unit : unitsAcquisition.get(player)) {
                    if (unit instanceof Troop && ((Troop) unit).getType().equals(troopType)) {
                        shouldPrintSpecial = true;
                        break;
                    }
                }
            }
            if (shouldPrintSpecial) output += ">>> ";
            output += troopType.getName() + ": ";
            int counter = 0;
            int playerCounter = 1;
            shouldPrintSpecial = false;
            for (Player player : players) {
                for (Unit unit : unitsAcquisition.get(player)) {
                    if (unit instanceof Troop && ((Troop) unit).getType().equals(troopType)) counter++;
                }
                output += ConsoleColors.formatPrinter(player.getFlagColor().getColor(), ConsoleColors.TEXT_BG_BLACK,
                        "player number " + playerCounter + " :" + counter + ", ");
                counter = 0;
                playerCounter++;
            }
            output += '\n';
        }

        shouldPrintSpecial = false;
        for (ThrowerTypes throwerType : ThrowerTypes.values()) {
            for (Player player : players) {
                for (Unit unit : unitsAcquisition.get(player)) {
                    if (unit instanceof Throwers && ((Throwers) unit).getType().equals(throwerType)) {
                        shouldPrintSpecial = true;
                        break;
                    }
                }
            }
            if (shouldPrintSpecial) output += ">>> ";
            output += throwerType.getName() + ": ";
            int counter = 0;
            int playerCounter = 1;
            shouldPrintSpecial = false;
            for (Player player : players) {
                for (Unit unit : unitsAcquisition.get(player)) {
                    if (unit instanceof Throwers && ((Throwers) unit).getType().equals(throwerType)) counter++;
                }
                output += ConsoleColors.formatPrinter(player.getFlagColor().getColor(), ConsoleColors.TEXT_BG_BLACK,
                        "player number " + playerCounter + " :" + counter + ", ");
                counter = 0;
                playerCounter++;
            }
            output += '\n';
        }

        //siege tower
        int counter = 0;
        int playerCounter = 1;
        shouldPrintSpecial = false;
        output += "siege tower: ";
        for (Player player : players) {
            for (Unit unit : unitsAcquisition.get(player)) {
                if (unit instanceof SiegeTower) counter++;
            }
            output += ConsoleColors.formatPrinter(player.getFlagColor().getColor(), ConsoleColors.TEXT_BG_BLACK,
                    "player number " + playerCounter + " :" + counter + ", ");
            counter = 0;
            playerCounter++;
        }
        output += '\n';
        counter = 0;
        playerCounter = 1;
        shouldPrintSpecial = false;
        //battering ram
        for (Player player : players) {
            for (Unit unit : unitsAcquisition.get(player)) {
                if (unit instanceof BatteringRam) {
                    shouldPrintSpecial = true;
                    break;
                }
            }
        }
        if (shouldPrintSpecial) output += ">>> ";
        output += "battering ram";
        for (Player player : players) {
            for (Unit unit : unitsAcquisition.get(player)) {
                if (unit instanceof BatteringRam) counter++;
            }
            output += ConsoleColors.formatPrinter(player.getFlagColor().getColor(), ConsoleColors.TEXT_BG_BLACK,
                    "player number " + playerCounter + " :" + counter + ", ");
            counter = 0;
            playerCounter++;
        }
        output += '\n';
        counter = 0;
        playerCounter = 1;
        shouldPrintSpecial = false;
        //ladder man
        for (Player player : players) {
            for (Unit unit : unitsAcquisition.get(player)) {
                if (unit instanceof BatteringRam) {
                    shouldPrintSpecial = true;
                    break;
                }
            }
        }
        if (shouldPrintSpecial) output += ">>> ";
        output += "ladder man: ";
        for (Player player : players) {
            for (Unit unit : unitsAcquisition.get(player)) {
                if (unit instanceof LadderMen) counter++;
            }
            output += ConsoleColors.formatPrinter(player.getFlagColor().getColor(), ConsoleColors.TEXT_BG_BLACK,
                    "player number " + playerCounter + " :" + counter + ", ");
            counter = 0;
            playerCounter++;
        }
        output += '\n';
        counter = 0;
        playerCounter = 1;
        shouldPrintSpecial = false;
        //portable shields
        for (Player player : players) {
            for (Unit unit : unitsAcquisition.get(player)) {
                if (unit instanceof PortableShields) {
                    shouldPrintSpecial = true;
                    break;
                }
            }
        }
        if (shouldPrintSpecial) output += ">>> ";
        output += "portable shields: ";
        for (Player player : players) {
            for (Unit unit : unitsAcquisition.get(player)) {
                if (unit instanceof PortableShields) counter++;
            }
            output += ConsoleColors.formatPrinter(player.getFlagColor().getColor(), ConsoleColors.TEXT_BG_BLACK,
                    "player number " + playerCounter + " :" + counter + ", ");
            counter = 0;
            playerCounter++;
        }
        output += '\n';
        counter = 0;
        playerCounter = 1;
        shouldPrintSpecial = false;
        //wall climber
        for (Player player : players) {
            for (Unit unit : unitsAcquisition.get(player)) {
                if (unit instanceof WallClimber) {
                    shouldPrintSpecial = true;
                    break;
                }
            }
        }
        if (shouldPrintSpecial) output += ">>> ";
        output += "wall climber: ";
        for (Player player : players) {
            for (Unit unit : unitsAcquisition.get(player)) {
                if (unit instanceof WallClimber) counter++;
            }
            output += ConsoleColors.formatPrinter(player.getFlagColor().getColor(), ConsoleColors.TEXT_BG_BLACK,
                    "player number " + playerCounter + " :" + counter + ", ");
            counter = 0;
            playerCounter++;
        }
        output += '\n';
        counter = 0;
        playerCounter = 1;
        shouldPrintSpecial = false;
        //engineer
        for (Player player : players) {
            for (Unit unit : unitsAcquisition.get(player)) {
                if (unit instanceof Engineer) {
                    shouldPrintSpecial = true;
                    break;
                }
            }
        }
        if (shouldPrintSpecial) output += ">>> ";
        output += "engineer: ";
        for (Player player : players) {
            for (Unit unit : unitsAcquisition.get(player)) {
                if (unit instanceof Engineer) counter++;
            }
            output += ConsoleColors.formatPrinter(player.getFlagColor().getColor(), ConsoleColors.TEXT_BG_BLACK,
                    "player number " + playerCounter + " :" + counter + ", ");
            counter = 0;
            playerCounter++;
        }
        return output;
    }
}