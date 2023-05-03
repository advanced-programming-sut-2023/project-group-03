package controller.gameControllers;

import Model.Buildings.*;
import Model.Buildings.Defending.*;
import Model.Buildings.Defending.Enums.GateTypes;
import Model.Buildings.Defending.Enums.TowerTypes;
import Model.Buildings.Defending.Enums.TrapsTypes;
import Model.Buildings.Defending.Enums.WallTypes;
import Model.Buildings.Enums.*;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Unit;
import controller.interfaces.BuildingInterface;
import view.Game.GameMenu;

import java.util.HashMap;
import java.util.regex.Matcher;

import static controller.Enums.InputOptions.*;
import static controller.Enums.Response.*;

public class BuildingController extends GeneralGameController implements BuildingInterface {
    GameController gameController;

    BuildingController(GameController gameController) {
        super(gameController.getGameMap());
        this.gameController = gameController;
    }

    public String selectBuilding(Matcher matcher, Player player, GameMenu gameMenu) {
        String buildingInfo = matcher.group("buildingInfo");
        HashMap<String, String> infoMap = getOptions(SELECT_BUILDING.getKeys(), buildingInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String coordinatesError = checkCoordinates(infoMap, "x", "y");
        if (coordinatesError != null) return coordinatesError;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        Tile targetTile = gameMap.getMap()[x][y];
        if (targetTile.getBuilding() == null) return BUILDING_NOT_EXIST_SELECT.getOutput();

        if (!targetTile.getOwner().equals(player)) return ACQUISITION_SELECT.getOutput();
        gameMenu.getSelectedUnits().clear();
        gameMenu.setSelected(targetTile.getBuilding());
        return SUCCESSFUL_SELECT.getOutput();
    }

    public String repair(Building building, Player player) {
        //gates, towers, barracks
        if (building instanceof Gates) {
            Gates gate = (Gates) building;
            GateTypes gateType = gate.getType();
            int lostHealth = gateType.getHP() - gate.getHP();
            int stoneCost = (gateType.getStoneCost() * lostHealth) / gateType.getHP();
            if (player.getInventory().get(Resources.STONE) < stoneCost)
                return NOT_ENOUGH_STONE_REPAIR.getOutput();
            if (!checkForEnemiesAround(building.getPosition(), player))
                return UNABLE_TO_REPAIR.getOutput();

            building.setHP(gateType.getHP());
            player.decreaseInventory(Resources.STONE, stoneCost);
            return SUCCESSFUL_REPAIR.getOutput();

        }

        if (building instanceof Towers) {
            TowerTypes towerType = ((Towers) building).getType();
            int lostHealth = towerType.getHP() - building.getHP();
            int stoneCost = (towerType.getStoneCost() * lostHealth) / towerType.getHP();
            if (player.getInventory().get(Resources.STONE) < stoneCost)
                return NOT_ENOUGH_STONE_REPAIR.getOutput();
            if (!checkForEnemiesAround(building.getPosition(), player))
                return UNABLE_TO_REPAIR.getOutput();

            building.setHP(towerType.getHP());
            player.decreaseInventory(Resources.STONE, stoneCost);
            return SUCCESSFUL_REPAIR.getOutput();
        }

        if (building instanceof  Barracks) {
            BarracksType barracksType = ((Barracks) building).getType();
            int lostHealth = barracksType.getHP() - building.getHP();
            int stoneCost = (barracksType.getStoneCost() * lostHealth) / barracksType.getHP();
            int woodCost = (barracksType.getWood() * lostHealth) / barracksType.getHP();
            int goldCost = (barracksType.getGold() * lostHealth) / barracksType.getHP();
            if ((player.getInventory().get(Resources.STONE) < stoneCost) ||
                (player.getInventory().get(Resources.WOOD) < woodCost)) return NOT_ENOUGH_STONE_REPAIR.getOutput();
            if (player.getGold() < goldCost) return NOT_ENOUGH_GOLD_REPAIR.getOutput();
            if (!checkForEnemiesAround(building.getPosition(), player)) return UNABLE_TO_REPAIR.getOutput();

            building.setHP(barracksType.getHP());
            player.decreaseGold(goldCost);
            player.decreaseInventory(Resources.STONE, stoneCost);
            player.decreaseInventory(Resources.WOOD, woodCost);
            return SUCCESSFUL_REPAIR.getOutput();
        }
        //check if you have the elements and there is no enemy around
        return INVALID_BUILDING_REPAIR.getOutput();
    }

    private boolean checkForEnemiesAround(Tile currentTile, Player player) {
        for (Unit unit : currentTile.getUnits()) {
            if (!unit.getOwner().equals(player)) return false;
        }

        int mapSize = gameMap.getSize();
        int x = currentTile.getRowNum();
        int y = currentTile.getColumnNum();

        if ((x + 1) < mapSize) {
            Tile neighbourTile = gameMap.getMap()[x + 1][y];
            for (Unit unit : neighbourTile.getUnits()) {
                if (!unit.getOwner().equals(player)) return false;
            }
        }

        if ((x - 1) < mapSize) {
            Tile neighbourTile = gameMap.getMap()[x - 1][y];
            for (Unit unit : neighbourTile.getUnits()) {
                if (!unit.getOwner().equals(player)) return false;
            }
        }

        if ((y + 1) < mapSize) {
            Tile neighbourTile = gameMap.getMap()[x][y + 1];
            for (Unit unit : neighbourTile.getUnits()) {
                if (!unit.getOwner().equals(player)) return false;
            }
        }

        if ((y - 1) < mapSize) {
            Tile neighbourTile = gameMap.getMap()[x][y - 1];
            for (Unit unit : neighbourTile.getUnits()) {
                if (!unit.getOwner().equals(player)) return false;
            }
        }

        return true;
    }

    private boolean checkIfFit(int x, int y, int size) {
        size = size / 2;
        if (x - size < 0 || x + size > gameMap.getSize() || y - size < 0 || y + size > gameMap.getSize())
            return false;
        return true;
    }

    @Override
    public String buildTowerMatcherHandler(Matcher matcher, Player player) {
        String towerInfo = matcher.group("towerInfo");
        HashMap<String, String> infoMap = getOptions(BUILD_TOWER.getKeys(), towerInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String coordinatesError = checkCoordinates(infoMap, "x", "y");
        if (coordinatesError != null) return coordinatesError;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        TowerTypes towerType = TowerTypes.getTypeByName(infoMap.get("t"));
        if (towerType == null) return INVALID_BUILDING_TYPE.getOutput();

        return buildTower(x, y, towerType, player);
    }

    private String buildTower(int xTemp, int yTemp, TowerTypes towerType, Player player) {
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

    @Override
    public String buildWallMatcherHandler(Matcher matcher, Player player) {
        String wallInfo = matcher.group("wallInfo");
        HashMap<String, String> infoMap = getOptions(BUILD_WALL.getKeys(), wallInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String coordinatesError = checkCoordinates(infoMap, "x", "y");
        if (coordinatesError != null) return coordinatesError;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        WallTypes wallType = WallTypes.getTypeByName(infoMap.get("t"));
        if (wallType == null) return INVALID_WALL_TYPE.getOutput();

        return buildWall(x, y, wallType, player);
    }

    private String buildWall(int x, int y, WallTypes wallType, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
        if (!wallType.getTextures().contains(targetTile.getTexture())) return DROP_BUILDING_TEXTURE.getOutput();

        if (player.getInventory().get(Resources.STONE) < wallType.getStoneCost()) return NOT_ENOUGH_STONE_WALL.getOutput();

        if (!targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();

        targetTile.setBuilding(new Wall(player, targetTile, wallType));

        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    public String buildBarracksMatcherHandler(Matcher matcher, Player player) {
        String barracksInfo = matcher.group("barracksInfo");
        HashMap<String, String> infoMap = getOptions(BUILD_BARRACKS.getKeys(), barracksInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String coordinatesError = checkCoordinates(infoMap, "x", "y");
        if (coordinatesError != null) return coordinatesError;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        BarracksType barracksType = BarracksType.getTypeByName(infoMap.get("t"));
        if (barracksType == null) return INVALID_BARRACKS_TYPE.getOutput();

        return buildBarracks(x, y, barracksType, player);
    }

    private String buildBarracks(int xTemp, int yTemp, BarracksType barracksType, Player player) {
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

    @Override
    public String buildInventoryMatcherHandler(Matcher matcher, Player player) {
        String inventoryInfo = matcher.group("inventoryInfo");
        HashMap<String, String> infoMap = getOptions(BUILD_INVENTORY.getKeys(), inventoryInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String coordinatesError = checkCoordinates(infoMap, "x", "y");
        if (coordinatesError != null) return coordinatesError;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        InventoryTypes inventoryType = InventoryTypes.getTypeByName(infoMap.get("t"));
        if (inventoryType == null) return INVALID_INVENTORY_TYPE.getOutput();

        if (inventoryType.equals(InventoryTypes.ARMOURY)) {
            return buildArmoury(x, y, player);
        } else {
            return buildStockPileFoodStorage(x, y, inventoryType, player);
        }
    }

    private String buildArmoury(int x, int y, Player player) {
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

    private String buildStockPileFoodStorage(int x, int y, InventoryTypes inventoryType, Player player) {
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

    @Override
    public String buildRestMatcherHandler(Matcher matcher, Player player) {
        String restInfo = matcher.group("restInfo");
        HashMap<String, String> infoMap = getOptions(BUILD_REST.getKeys(), restInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String coordinatesError = checkCoordinates(infoMap, "x", "y");
        if (coordinatesError != null) return coordinatesError;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        RestTypes restType = RestTypes.getTypeByName(infoMap.get("t"));
        if (restType == null) return INVALID_REST_TYPE.getOutput();

        return buildRest(x, y, restType, player);
    }

    private String buildRest(int x, int y, RestTypes restType, Player player) {
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

    public String buildGeneratorMatcherHandler(Matcher matcher, Player player) {
        String generatorInfo = matcher.group("generatorInfo");
        HashMap<String, String> infoMap = getOptions(BUILD_REST.getKeys(), generatorInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String coordinatesError = checkCoordinates(infoMap, "x", "y");
        if (coordinatesError != null) return coordinatesError;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        GeneratorTypes generatorType = GeneratorTypes.getTypeByName(infoMap.get("t"));
        if (generatorType == null) return INVALID_GENERATOR_TYPE.getOutput();

        return buildGenerator(x, y, generatorType, player);
    }

    private String buildGenerator(int xTemp, int yTemp, GeneratorTypes generatorType, Player player) {
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

    public String buildStoneGatesMatcherHandler(Matcher matcher, Player player) {
        String stoneGateInfo = matcher.group("stoneGateInfo");
        HashMap<String, String> infoMap = getOptions(BUILD_STONE_GATE.getKeys(), stoneGateInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String coordinatesError = checkCoordinates(infoMap, "x", "y");
        if (coordinatesError != null) return coordinatesError;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        GateTypes gateType = GateTypes.getTypeByName(infoMap.get("t"));
        if (gateType == null) return INVALID_STONE_GATE_TYPE.getOutput();
        if (gateType.equals(GateTypes.DRAWBRIDGE)) return INVALID_STONE_GATE_TYPE.getOutput();

        return buildStoneGate(x, y, gateType, player);
    }

    private String buildStoneGate(int xTemp, int yTemp, GateTypes gateType, Player player) {
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

    public String buildDrawbridgeMatcherHandler(Matcher matcher, Player player) {
        String drawbridgeInfo = matcher.group("drawbridgeInfo");
        HashMap<String, String> infoMap = getOptions(BUILD_DRAWBRIDGE.getKeys(), drawbridgeInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String coordinatesError = checkCoordinates(infoMap, "x", "y");
        if (coordinatesError != null) return coordinatesError;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        String direction = infoMap.get("d");

        return buildDrawbridge(x, y, direction);
    }

    private String buildDrawbridge(int x, int y, String direction) {
        if (!(direction.equals("down") || direction.equals("up") || direction.equals("right") || direction.equals("left"))) {
            return INVALID_DIRECTION_DRAWBRIDGE.getOutput();
        }
        return null;
    }

    public String buildTrapMatcherHandler(Matcher matcher, Player player) {
        String trapInfo = matcher.group("trapInfo");
        HashMap<String, String> infoMap = getOptions(BUILD_STONE_GATE.getKeys(), trapInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String coordinatesError = checkCoordinates(infoMap, "x", "y");
        if (coordinatesError != null) return coordinatesError;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        TrapsTypes trapsType = TrapsTypes.getTypeByName(infoMap.get("t"));
        if (trapsType == null) return INVALID_TRAP_TYPE.getOutput();

        if (trapsType.equals("pitch ditch")) return buildPitchDitch(x, y, player);
        if (trapsType.equals("caged war dogs")) return buildCagedWarDogs(x, y, player);
        return buildKillingPit(x, y, player);
    }

    private String checkTrapErrors(int x, int y, Player player, TrapsTypes trapsType) {
        Tile targetTile = gameMap.getMap()[x][y];
        if (!trapsType.getTextures().contains(targetTile.getTexture())) return BAD_TEXTURE_TRAP.getOutput();

        if (targetTile.getBuilding() != null) return BUILDING_EXIST_TRAP.getOutput();
        if (targetTile.getUnits().size() > 0) return UNIT_EXIST_TRAP.getOutput();

        if (!targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();

        return null;
    }

    private String buildPitchDitch(int x, int y, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        String errorCheck = checkTrapErrors(x, y, player, TrapsTypes.CAGED_WAR_DOGS);
        if (errorCheck != null) return errorCheck;

        targetTile.setBuilding(new PitchDitch(player, targetTile, TrapsTypes.PITCH_DITCH));
        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    private String buildCagedWarDogs(int x, int y, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        String errorCheck = checkTrapErrors(x, y, player, TrapsTypes.CAGED_WAR_DOGS);
        if (errorCheck != null) return errorCheck;

        targetTile.setBuilding(new CagedWarDogs(player, targetTile, TrapsTypes.CAGED_WAR_DOGS));
        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    private String buildKillingPit(int x, int y, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        String errorCheck = checkTrapErrors(x, y, player, TrapsTypes.CAGED_WAR_DOGS);
        if (errorCheck != null) return errorCheck;

        targetTile.setBuilding(new KillingPit(player, targetTile, TrapsTypes.KILLING_PIT));
        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }
}
