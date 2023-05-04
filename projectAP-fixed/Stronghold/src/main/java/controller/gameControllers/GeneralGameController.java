package controller.gameControllers;

import Model.Buildings.*;
import Model.Buildings.Defending.*;
import Model.Buildings.Defending.Enums.GateTypes;
import Model.Buildings.Defending.Enums.TowerTypes;
import Model.Buildings.Defending.Enums.TrapsTypes;
import Model.Buildings.Enums.*;
import Model.Field.GameMap;
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

    private boolean checkIfFit(int x, int y, int size) {
        size = size / 2;
        if (x - size < 0 || x + size > gameMap.getSize() || y - size < 0 || y + size > gameMap.getSize())
            return false;
        return true;
    }


    protected String buildTower(int xTemp, int yTemp, TowerTypes towerType, Player player) {
        if (player.getInventory().get(Resources.STONE) < towerType.getStoneCost())
            return NOT_ENOUGH_STONE_TOWER.getOutput();

        if (!checkIfFit(xTemp, yTemp, towerType.getSize())) return NOT_FIT.getOutput();

        int size = towerType.getSize() / 2;
        Tile targetTile;
        for (int x = xTemp - size; x <= xTemp + size; x++) {
            for (int y = yTemp - size; y < yTemp + size; y++) {
                targetTile = gameMap.getMap()[x][y];
                if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
                if (!towerType.getTextures().contains(targetTile.getTexture())) return DROP_BUILDING_TEXTURE.getOutput();
                if (!targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();
            }
        }

        Towers newTower = new Towers(player, gameMap.getMap()[xTemp][yTemp], towerType);

        for (int x = xTemp - size; x <= xTemp + size; x++) {
            for (int y = yTemp - size; y < yTemp + size; y++) {
                ;gameMap.getMap()[x][y].setBuilding(newTower);
            }
        }

        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }


    protected String buildBarracks(int xTemp, int yTemp, BarracksType barracksType, Player player) {
        if ((player.getInventory().get(Resources.STONE) < barracksType.getStoneCost()))
            return NOT_ENOUGH_STONE_TOWER.getOutput();
        if ((player.getInventory().get(Resources.WOOD) < barracksType.getWood()))
            return NOT_ENOUGH_WOOD_BARRACKS.getOutput();
        if ((player.getGold() < barracksType.getGold()))
            return NOT_ENOUGH_GOLD_BARRACKS.getOutput();
        if ((player.getInventory().get(Resources.OIL) < barracksType.getOil()))
            return NOT_ENOUGH_OIL_BARRACKS.getOutput();

        if (!checkIfFit(xTemp, yTemp, barracksType.getSize())) return NOT_FIT.getOutput();

        int size = barracksType.getSize() / 2;
        Tile targetTile;
        for (int x = xTemp - size; x <= xTemp + size; x++) {
            for (int y = yTemp - size; y < yTemp + size; y++) {
                targetTile = gameMap.getMap()[x][y];
                if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
                if (!barracksType.getTextures().contains(targetTile.getTexture())) return DROP_BUILDING_TEXTURE.getOutput();
                if (!targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();
            }
        }

        Barracks newBarracks = new Barracks(player, gameMap.getMap()[xTemp][yTemp], barracksType);

        for (int x = xTemp - size; x <= xTemp + size; x++) {
            for (int y = yTemp - size; y < yTemp + size; y++) {
                gameMap.getMap()[x][y].setBuilding(newBarracks);
            }
        }

        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }


    protected String buildArmoury(int x, int y, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
        if (!InventoryTypes.ARMOURY.getTextures().contains(targetTile.getTexture()))
            return DROP_BUILDING_TEXTURE.getOutput();

        if (player.getInventory().get(Resources.STONE) < InventoryTypes.ARMOURY.getStoneCost())
            return NOT_ENOUGH_STONE_ARMOURY.getOutput();

        if (!targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();

        targetTile.setBuilding(new Inventory(player, targetTile, InventoryTypes.ARMOURY));
        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    protected String buildStockPileFoodStorage(int x, int y, InventoryTypes inventoryType, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
        if (!inventoryType.getTextures().contains(targetTile.getTexture())) return DROP_BUILDING_TEXTURE.getOutput();

        if (player.getInventory().get(Resources.WOOD) < inventoryType.getWood())
            return NOT_ENOUGH_WOOD_INVENTORY.getOutput();

        int mapSize = gameMap.getSize();
        boolean existInventory = false;

        if ((x + 1) < mapSize) {
            Building building = gameMap.getMap()[x + 1][y].getBuilding();
            if (building instanceof Inventory) {
                Inventory inventory = (Inventory) building;
                if (inventory.getType().equals(inventoryType)) existInventory = true;
            }
        }

        if ((x - 1) < mapSize) {
            Building building = gameMap.getMap()[x - 1][y].getBuilding();
            if (building instanceof Inventory) {
                Inventory inventory = (Inventory) building;
                if (inventory.getType().equals(inventoryType)) existInventory = true;
            }
        }

        if ((y + 1) < mapSize) {
            Building building = gameMap.getMap()[x][y + 1].getBuilding();
            if (building instanceof Inventory) {
                Inventory inventory = (Inventory) building;
                if (inventory.getType().equals(inventoryType)) existInventory = true;
            }
        }

        if ((y - 1) < mapSize) {
            Building building = gameMap.getMap()[x][y - 1].getBuilding();
            if (building instanceof Inventory) {
                Inventory inventory = (Inventory) building;
                if (inventory.getType().equals(inventoryType)) existInventory = true;
            }
        }

        if (!existInventory) return NO_INVENTORY_AROUND.getOutput();

        if (!targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();

        targetTile.setBuilding(new Inventory(player, targetTile, inventoryType));

        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }


    protected String buildRest(int x, int y, RestTypes restType, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
        if (!restType.getTextures().contains(targetTile.getTexture())) return DROP_BUILDING_TEXTURE.getOutput();

        if (player.getInventory().get(Resources.WOOD) < restType.getWood())
            return NOT_ENOUGH_WOOD_REST.getOutput();
        if (player.getGold() < restType.getGold())
            return NOT_ENOUGH_GOLD_REST.getOutput();

        if (!targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();

        targetTile.setBuilding(new Rest(player, targetTile, restType));
        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    protected String buildGenerator(int xTemp, int yTemp, GeneratorTypes generatorType, Player player) {
        if (player.getGold() < generatorType.getGold())
            return NOT_ENOUGH_GOLD_BUILDING.getOutput();
        if (player.getInventory().get(Resources.WOOD) < generatorType.getWood())
            return NOT_ENOUGH_WOOD_BUILDING.getOutput();
        if (player.getPopularity() < generatorType.getWorker())
            return NOT_ENOUGH_WORKER_BUILDING.getOutput();

        if (!checkIfFit(xTemp, yTemp, generatorType.getSize())) return NOT_FIT.getOutput();

        int size = generatorType.getSize() / 2;
        Tile targetTile;
        for (int x = xTemp - size; x <= xTemp + size; x++) {
            for (int y = yTemp - size; y < yTemp + size; y++) {
                targetTile = gameMap.getMap()[x][y];
                if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
                if (!generatorType.getTextures().contains(targetTile.getTexture())) return DROP_BUILDING_TEXTURE.getOutput();
                if (!targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();
            }
        }

        targetTile = gameMap.getMap()[xTemp][yTemp];
        Generators newGenerator = new Generators(player, targetTile, generatorType);
//        for (int i = 0; i < generatorType.getWorker(); i++) {     TODO adding workers needed
//            targetTile.addUnit(new Worker(player, targetTile, newGenerator));
//        }

        for (int x = xTemp - size; x <= xTemp + size; x++) {
            for (int y = yTemp - size; y < yTemp + size; y++) {
                gameMap.getMap()[x][y].setBuilding(newGenerator);
            }
        }

        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    protected String buildStoneGate(int xTemp, int yTemp, GateTypes gateType, Player player) {
        if (player.getInventory().get(Resources.STONE) < gateType.getStoneCost())
            return NOT_ENOUGH_STONE_STONE_GATE.getOutput();

        if (!checkIfFit(xTemp, yTemp, gateType.getSize())) return NOT_FIT.getOutput();

        int size = gateType.getSize() / 2;
        Tile targetTile;
        for (int x = xTemp - size; x <= xTemp + size; x++) {
            for (int y = yTemp - size; y < yTemp + size; y++) {
                targetTile = gameMap.getMap()[x][y];
                if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
                if (!gateType.getTextures().contains(targetTile.getTexture())) return DROP_BUILDING_TEXTURE.getOutput();
                if (!targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();
            }
        }

        Gates newGate = new Gates(player, gameMap.getMap()[xTemp][yTemp], gateType);

        for (int x = xTemp - size; x <= xTemp + size; x++) {
            for (int y = yTemp - size; y < yTemp + size; y++) {
                gameMap.getMap()[x][y].setBuilding(newGate);
            }
        }
        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    protected String checkTrapErrors(int x, int y, Player player, TrapsTypes trapsType) {
        Tile targetTile = gameMap.getMap()[x][y];
        if (!trapsType.getTextures().contains(targetTile.getTexture())) return BAD_TEXTURE_TRAP.getOutput();

        if (targetTile.getBuilding() != null) return BUILDING_EXIST_TRAP.getOutput();
        if (targetTile.getUnits().size() > 0) return UNIT_EXIST_TRAP.getOutput();

        if (!targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();

        return null;
    }

    protected String buildPitchDitch(int x, int y, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        String errorCheck = checkTrapErrors(x, y, player, TrapsTypes.PITCH_DITCH);
        if (errorCheck != null) return errorCheck;

        targetTile.setBuilding(new PitchDitch(player, targetTile, TrapsTypes.PITCH_DITCH));
        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    protected String buildCagedWarDogs(int x, int y, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        String errorCheck = checkTrapErrors(x, y, player, TrapsTypes.CAGED_WAR_DOGS);
        if (errorCheck != null) return errorCheck;

        targetTile.setBuilding(new CagedWarDogs(player, targetTile, TrapsTypes.CAGED_WAR_DOGS));
        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    protected String buildKillingPit(int x, int y, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        String errorCheck = checkTrapErrors(x, y, player, TrapsTypes.KILLING_PIT);
        if (errorCheck != null) return errorCheck;

        targetTile.setBuilding(new KillingPit(player, targetTile, TrapsTypes.KILLING_PIT));
        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    protected String buildStore(int x, int y, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        if (!targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();
        //todo



        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }
}

