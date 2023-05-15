package controller.gameControllers;

import Model.Buildings.*;
import Model.Buildings.Defending.*;
import Model.Buildings.Defending.Enums.*;
import Model.Buildings.Enums.*;
import Model.Field.*;
import Model.GamePlay.Player;
import Model.Units.Ox;
import Model.Units.Unit;
import Model.Units.Worker;
import controller.interfaces.BuildingInterface;
import view.Game.GameMenu;

import java.util.HashMap;
import java.util.regex.Matcher;

import static controller.Enums.InputOptions.*;
import static controller.Enums.Response.*;

public class BuildingController extends GeneralGameController implements BuildingInterface {
    GameController gameController;

    BuildingController(GameMap gameMap) {
        super(gameMap);
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
        //gates, towers, wall
        if (building instanceof Gates) {
            Gates gate = (Gates) building;
            GateTypes gateType = gate.getType();
            int lostHealth = gateType.getHP() - gate.getHP();
            int stoneCost = (gateType.getStoneCost() * lostHealth) / gateType.getHP();
            if (player.getInventory().get(Resources.STONE) < stoneCost)
                return NOT_ENOUGH_STONE_REPAIR.getOutput();
            if (!checkForEnemiesAround(building.getPosition(), player, gateType.getSize()))
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
            if (!checkForEnemiesAround(building.getPosition(), player, towerType.getSize()))
                return UNABLE_TO_REPAIR.getOutput();

            building.setHP(towerType.getHP());
            player.decreaseInventory(Resources.STONE, stoneCost);
            return SUCCESSFUL_REPAIR.getOutput();
        }

        if (building instanceof Wall) {
            WallTypes wallType = ((Wall) building).getType();
            int lostHealth = wallType.getHP() - building.getHP();
            int stoneCost = (wallType.getStoneCost() * lostHealth) / wallType.getHP();

            if ((player.getInventory().get(Resources.STONE) < stoneCost))
                return NOT_ENOUGH_STONE_REPAIR.getOutput();
            if (!checkForEnemiesAround(building.getPosition(), player, wallType.getSize()))
                return UNABLE_TO_REPAIR.getOutput();

            building.setHP(wallType.getHP());
            player.decreaseInventory(Resources.STONE, stoneCost);
            return SUCCESSFUL_REPAIR.getOutput();
        }
        //check if you have the elements and there is no enemy around
        return INVALID_BUILDING_REPAIR.getOutput();
    }

    private boolean checkForEnemiesAround(Tile currentTile, Player player, int size) {
        for (Unit unit : currentTile.getUnits()) {
            if (!unit.getOwner().equals(player)) return false;
        }

        int mapSize = gameMap.getSize();
        int xCenter = currentTile.getRowNum();
        int yCenter = currentTile.getColumnNum();
        size = size / 2 + 1;

        outer:
        for (int x = xCenter - size; x <= xCenter + size; x++) {
            for (int y = yCenter - size; y <= yCenter + size; y++) {
                if (x < 0 || x >= mapSize) continue outer;
                if (y < 0 || y >= mapSize) continue;
                for (Unit unit : gameMap.getMap()[x][y].getUnits()) {
                    if (!unit.getOwner().equals(player)) return false;
                }
            }
        }

        return true;
    }

    public String dropBuildingMatcherHandler(Matcher matcher, Player player) {
        String buildingInfo = matcher.group("buildingInfo");
        HashMap<String, String> infoMap = getOptions(DROP_BUILDING.getKeys(), buildingInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String checkCoordinatesResult = checkCoordinates(infoMap, "x", "y");
        if (checkCoordinatesResult != null) return checkCoordinatesResult;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        Tile targetTile = gameMap.getMap()[x][y];
        Texture tileTexture = targetTile.getTexture();
        if (targetTile.getMazafaza() != null && targetTile.getMazafaza().getName().contains("rock"))
            return ROCK_EXIT_DROP_BUILDING.getOutput();

        if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();

        String type = infoMap.get("t");

        //targetTile owner changes
        if (targetTile.getOwner() != null && !targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();

        BarracksType barracksType = BarracksType.getTypeByName(type);
        if (barracksType != null) {
            return buildBarracks(targetTile.getRowNum(), targetTile.getColumnNum(), barracksType, player);
        }

        GeneratorTypes generatorType = GeneratorTypes.getTypeByName(type);
        if (generatorType != null) {
            return buildGenerator(targetTile.getRowNum(), targetTile.getColumnNum(), generatorType, player);
        }

        RestTypes restType = RestTypes.getTypeByName(type);
        if (restType != null) {
            return buildRest(targetTile.getRowNum(), targetTile.getColumnNum(), restType, player);
        }

        InventoryTypes inventoryType = InventoryTypes.getTypeByName(type);
        if (inventoryType != null) {
            if (inventoryType.getName().equals("armoury"))
                return buildArmoury(targetTile.getRowNum(), targetTile.getColumnNum(), player);
            else
                return buildStockPileFoodStorage(targetTile.getRowNum(), targetTile.getColumnNum(), inventoryType, player);
        }

        GateTypes gateType = GateTypes.getTypeByName(type);
        if (gateType != null) {
            infoMap = getOptions(BUILD_STONE_GATE.getKeys(), buildingInfo);
            Direction direction = Direction.getDirectionByName(infoMap.get("d"));
            if (direction == null) return INVALID_DIRECTION_STONE_GATE.getOutput();
            return buildStoneGate(targetTile.getRowNum(), targetTile.getColumnNum(), gateType, player, direction);
        }

        TowerTypes towerType = TowerTypes.getTypeByName(type);
        if (towerType != null) {
            return buildTower(targetTile.getRowNum(), targetTile.getColumnNum(), towerType, player);
        }

        TrapsTypes trapsType = TrapsTypes.getTypeByName(type);
        if (trapsType != null) {
            if (trapsType.getName().equals("pitch ditch")) return buildPitchDitch(x, y, player);
            if (trapsType.getName().equals("caged war dogs")) return buildCagedWarDogs(x, y, player);
            if (trapsType.getName().equals("killing pit")) return buildKillingPit(x, y, player);
            return INVALID_TRAP_TYPE.getOutput();
        }

        WallTypes wallType = WallTypes.getTypeByName(type);
        if (wallType != null) {
            if (!wallType.getTextures().contains(tileTexture)) return DROP_BUILDING_TEXTURE.getOutput();
            targetTile.setBuilding(new Wall(player, targetTile, wallType));
            return SUCCESSFUL_DROP_BUILDING.getOutput();
        }

        if (type.equals("store")) {
            targetTile.setBuilding(new Store(player, targetTile));
            return STORE_DROP.getOutput();
        }

        if (type.equals("keep")) {
            if (player.getKeep() != null) return KEEP_EXIST.getOutput();
            new Keep(player, targetTile);
            return SUCCESSFUL_DROP_BUILDING.getOutput();
        }


        return INVALID_BUILDING_TYPE.getOutput();
    }

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

        if (player.getInventory().get(Resources.STONE) < wallType.getStoneCost())
            return NOT_ENOUGH_STONE_WALL.getOutput();

        if (targetTile.getOwner() != null && !targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();

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

    public String buildStairMatcherHandler(Matcher matcher, Player player) {
        String stairInfo = matcher.group("stairInfo");
        HashMap<String, String> infoMap = getOptions(BUILD_REST.getKeys(), stairInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String coordinatesError = checkCoordinates(infoMap, "x", "y");
        if (coordinatesError != null) return coordinatesError;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        return buildStair(x, y, player);
    }

    private String buildStair(int x, int y, Player player) {
        if (player.getInventory().get(Resources.STONE) < StairsTypes.STAIRS.getStoneCost())
            return NOT_ENOUGH_STONE_STONE_GATE.getOutput();

        Tile targetTile = gameMap.getMap()[x][y];
        if (targetTile.getLadder() != null) return STAIR_EXIST.getOutput();
        if (!targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();
        if (!targetTile.getHeight().equals(Height.GROUND) && !targetTile.getHeight().equals(Height.SMALL_WALL))
            return BAD_TEXTURE_STAIR.getOutput();

        targetTile.setLadder(new Stair(player, targetTile, StairsTypes.STAIRS));
        return SUCCESSFUL_DROP_STAIR.getOutput();
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

        Direction direction = Direction.getDirectionByName(infoMap.get("d"));

        if (direction == null) return INVALID_DIRECTION_STONE_GATE.getOutput();
        return buildStoneGate(x, y, gateType, player, direction);
    }

    public String buildOxTetherMatcherHandler(Matcher matcher, Player player, Generators generators) {
        if (!generators.getType().equals(GeneratorTypes.STONE_MINE) && !generators.getType().equals(GeneratorTypes.IRON_MINE))
            return BAD_BUILDING_OX_TETHER.getOutput();

        String buildingInfo = matcher.group("buildingInfo");
        HashMap<String, String> infoMap = getOptions(COORDINATES.getKeys(), buildingInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        error = checkCoordinates(infoMap, "x", "y");
        if (error != null) return error;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        return buildOxTether(x, y, player, generators);
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

        Tile targetTile = gameMap.getMap()[x][y];
        Building building = targetTile.getBuilding();
        if (!(building != null && targetTile.getBuilding() instanceof Gates && ((Gates) building).getTerminals().contains(targetTile)))
            return NOT_TERMINAL_DRAWBRIDGE.getOutput();

        return SUCCESSFUL_DROP_BUILDING.getOutput();
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

        if (trapsType.getName().equals("pitch ditch")) return buildPitchDitch(x, y, player);
        if (trapsType.getName().equals("caged war dogs")) return buildCagedWarDogs(x, y, player);
        return buildKillingPit(x, y, player);
    }

    public String buildStoreMatcherHandler(Matcher matcher, Player player) {
        String storeInfo = matcher.group("storeInfo");
        HashMap<String, String> infoMap = getOptions(BUILD_STONE_GATE.getKeys(), storeInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String coordinatesError = checkCoordinates(infoMap, "x", "y");
        if (coordinatesError != null) return coordinatesError;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;
        return buildStore(x, y, player);
    }

    private boolean checkIfFit(int x, int y, int size) {
        size = size / 2;
        return x - size >= 0 && x + size < gameMap.getSize() && y - size >= 0 && y + size < gameMap.getSize();
    }


    protected String buildTower(int xCenter, int yCenter, TowerTypes towerType, Player player) {
        if (player.getInventory().get(Resources.STONE) < towerType.getStoneCost())
            return NOT_ENOUGH_STONE_TOWER.getOutput();

        if (!checkIfFit(xCenter, yCenter, towerType.getSize())) return NOT_FIT.getOutput();

        int size = towerType.getSize() / 2;
        Tile targetTile;
        for (int x = xCenter - size; x <= xCenter + size; x++) {
            for (int y = yCenter - size; y <= yCenter + size; y++) {
                targetTile = gameMap.getMap()[x][y];
                if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
                if (!towerType.getTextures().contains(targetTile.getTexture()))
                    return DROP_BUILDING_TEXTURE.getOutput();
                if (targetTile.getOwner() != null && !targetTile.getOwner().equals(player))
                    return ACQUISITION.getOutput();
                if (targetTile.getMazafaza() != null && targetTile.getMazafaza().getName().contains("rock"))
                    return ROCK_EXIT_DROP_BUILDING.getOutput();
            }
        }

        Towers newTower = new Towers(player, gameMap.getMap()[xCenter][yCenter], towerType);

        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }


    protected String buildBarracks(int xCenter, int yCenter, BarracksType barracksType, Player player) {
        if ((player.getInventory().get(Resources.STONE) < barracksType.getStoneCost()))
            return NOT_ENOUGH_STONE_BUILDING.getOutput();
        if ((player.getInventory().get(Resources.WOOD) < barracksType.getWood()))
            return NOT_ENOUGH_WOOD_BARRACKS.getOutput();
        if ((player.getGold() < barracksType.getGold()))
            return NOT_ENOUGH_GOLD_BARRACKS.getOutput();
        if ((player.getInventory().get(Resources.OIL) < barracksType.getOil()))
            return NOT_ENOUGH_OIL_BARRACKS.getOutput();

        if (!checkIfFit(xCenter, yCenter, barracksType.getSize())) return NOT_FIT.getOutput();

        int size = barracksType.getSize() / 2;
        Tile targetTile;
        for (int x = xCenter - size; x <= xCenter + size; x++) {
            for (int y = yCenter - size; y <= yCenter + size; y++) {
                targetTile = gameMap.getMap()[x][y];
                if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
                if (!barracksType.getTextures().contains(targetTile.getTexture()))
                    return DROP_BUILDING_TEXTURE.getOutput();
                if (targetTile.getOwner() != null && !targetTile.getOwner().equals(player))
                    return ACQUISITION.getOutput();
                if (targetTile.getMazafaza() != null && targetTile.getMazafaza().getName().contains("rock"))
                    return ROCK_EXIT_DROP_BUILDING.getOutput();
            }
        }

        Barracks newBarracks = new Barracks(player, gameMap.getMap()[xCenter][yCenter], barracksType);

        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    protected String buildArmoury(int x, int y, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
        if (!InventoryTypes.ARMOURY.getTextures().contains(targetTile.getTexture()))
            return DROP_BUILDING_TEXTURE.getOutput();

        if (player.getInventory().get(Resources.STONE) < InventoryTypes.ARMOURY.getStoneCost())
            return NOT_ENOUGH_STONE_ARMOURY.getOutput();

        if (targetTile.getOwner() != null && !targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();
        if (targetTile.getMazafaza() != null && targetTile.getMazafaza().getName().contains("rock"))
            return ROCK_EXIT_DROP_BUILDING.getOutput();

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

        if ((inventoryType.equals(InventoryTypes.STOCKPILE) && player.getKeep().getStockPile() != null) ||
                (inventoryType.equals(InventoryTypes.FOOD_STORAGE) && player.getKeep().getFoodStorage() != null)) {

            boolean existInventory = false;

            if ((x + 1) < mapSize) {
                Building building = gameMap.getMap()[x + 1][y].getBuilding();
                if (building instanceof Inventory) {
                    Inventory inventory = (Inventory) building;
                    if (inventory.getType().equals(inventoryType)) existInventory = true;

                }
            }

            if ((x - 1) >= 0) {
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

            if ((y - 1) >= 0) {
                Building building = gameMap.getMap()[x][y - 1].getBuilding();
                if (building instanceof Inventory) {
                    Inventory inventory = (Inventory) building;
                    if (inventory.getType().equals(inventoryType)) existInventory = true;
                }
            }

            if (!existInventory) return NO_INVENTORY_AROUND.getOutput();
        }

        if (targetTile.getOwner() != null && !targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();
        if (targetTile.getMazafaza() != null && targetTile.getMazafaza().getName().contains("rock"))
            return ROCK_EXIT_DROP_BUILDING.getOutput();

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

        if (targetTile.getOwner() != null && !targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();
        if (targetTile.getMazafaza() != null && targetTile.getMazafaza().getName().contains("rock"))
            return ROCK_EXIT_DROP_BUILDING.getOutput();

        targetTile.setBuilding(new Rest(player, targetTile, restType));
        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    protected String buildOxTether(int x, int y, Player player, Generators generators) {
        if (player.getInventory().get(Resources.WOOD) < OxTether.getWood())
            return NOT_ENOUGH_WOOD_BUILDING.getOutput();

        Tile targetTile;

        targetTile = gameMap.getMap()[x][y];
        if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
        if (!(targetTile.getHeight().equals(Height.GROUND))) return BAD_TEXTURE_OX_TETHER.getOutput();
        if (targetTile.getOwner() != null && !targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();
        if (targetTile.getMazafaza() != null && targetTile.getMazafaza().getName().contains("rock"))
            return ROCK_EXIT_DROP_BUILDING.getOutput();

        new OxTether(player, targetTile);
        for (int i = 0; i < generators.getType().getWorker(); i++) {
            generators.addWorker(new Ox(player, targetTile, generators));
        }
        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    protected String buildGenerator(int xCenter, int yCenter, GeneratorTypes generatorType, Player player) {
        if (player.getGold() < generatorType.getGold())
            return NOT_ENOUGH_GOLD_BUILDING.getOutput();
        if (player.getInventory().get(Resources.WOOD) < generatorType.getWood())
            return NOT_ENOUGH_WOOD_BUILDING.getOutput();
        if ((player.getMaxPopulation() - player.getCurrentPopulation()) < generatorType.getWorker())
            return NOT_ENOUGH_WORKER_BUILDING.getOutput();

        if (!checkIfFit(xCenter, yCenter, generatorType.getSize())) return NOT_FIT.getOutput();

        Resources product = generatorType.getProduct();
        InventoryTypes inventoryType = null;
        for (InventoryTypes inventory : InventoryTypes.values()) {
            if (inventory.getResource().equals(product.getType())) {
                inventoryType = inventory;
                break;
            }
        }
        if (inventoryType == null) return "Weird. Couldn't find the inventory type in line 610.";
        if (inventoryType.equals(InventoryTypes.ARMOURY)) {
            if (player.getKeep().getArmoury() == null) return NOT_HAVE_REQUIRED_INVENTORY_GENERATOR.getOutput();
        } else if (inventoryType.equals(InventoryTypes.STOCKPILE)) {
            if (player.getKeep().getStockPile() == null) return NOT_HAVE_REQUIRED_INVENTORY_GENERATOR.getOutput();
        } else if (inventoryType.equals(InventoryTypes.FOOD_STORAGE)) {
            if (player.getKeep().getFoodStorage() == null) return NOT_HAVE_REQUIRED_INVENTORY_GENERATOR.getOutput();
        }

        int size = generatorType.getSize() / 2;
        Tile targetTile;
        for (int x = xCenter - size; x <= xCenter + size; x++) {
            for (int y = yCenter - size; y <= yCenter + size; y++) {
                targetTile = gameMap.getMap()[x][y];
                if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
                if (!generatorType.getTextures().contains(targetTile.getTexture()))
                    return DROP_BUILDING_TEXTURE.getOutput();
                if (targetTile.getOwner() != null && !targetTile.getOwner().equals(player))
                    return ACQUISITION.getOutput();
                if (targetTile.getMazafaza() != null && targetTile.getMazafaza().getName().contains("rock"))
                    return ROCK_EXIT_DROP_BUILDING.getOutput();
            }
        }

        targetTile = gameMap.getMap()[xCenter][yCenter];
        Generators newGenerator = new Generators(player, targetTile, generatorType);
        for (int i = 0; i < generatorType.getWorker(); i++) {
            if (!generatorType.equals(GeneratorTypes.IRON_MINE) && !generatorType.equals(GeneratorTypes.STONE_MINE)) {
                newGenerator.addWorker(new Worker(player, targetTile, newGenerator));
            }
        }

        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    protected String buildStoneGate(int xCenter, int yCenter, GateTypes gateType, Player player, Direction direction) {
        if (player.getInventory().get(Resources.STONE) < gateType.getStoneCost())
            return NOT_ENOUGH_STONE_STONE_GATE.getOutput();

        if (!checkIfFit(xCenter, yCenter, gateType.getSize())) return NOT_FIT.getOutput();

        int size = gateType.getSize() / 2;
        Tile targetTile;
        for (int x = xCenter - size; x <= xCenter + size; x++) {
            for (int y = yCenter - size; y <= yCenter + size; y++) {
                targetTile = gameMap.getMap()[x][y];
                if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
                if (!gateType.getTextures().contains(targetTile.getTexture())) return DROP_BUILDING_TEXTURE.getOutput();
                if (targetTile.getOwner() != null && !targetTile.getOwner().equals(player))
                    return ACQUISITION.getOutput();
                if (targetTile.getMazafaza() != null && targetTile.getMazafaza().getName().contains("rock"))
                    return ROCK_EXIT_DROP_BUILDING.getOutput();
            }
        }

        Tile terminalTile1;
        Tile terminalTile2;
        if (direction.equals(Direction.DOWN) || direction.equals(Direction.UP)) {
            if (xCenter + size + 1 >= gameMap.getSize() || xCenter - size - 1 < 0)
                return NOT_ENOUGH_SPACE_TERMINALS_STONE_GATE.getOutput();
            terminalTile1 = gameMap.getMap()[xCenter + size + 1][yCenter];
            terminalTile2 = gameMap.getMap()[xCenter - size - 1][yCenter];

        } else {
            if (yCenter + size + 1 >= gameMap.getSize() || yCenter - size - 1 < 0)
                return NOT_ENOUGH_SPACE_TERMINALS_STONE_GATE.getOutput();
            terminalTile1 = gameMap.getMap()[xCenter][yCenter + size + 1];
            terminalTile2 = gameMap.getMap()[xCenter][yCenter - size - 1];
        }
        if (!terminalTile2.getOwner().equals(player) || !terminalTile1.getOwner().equals(player))
            return ACQUISITION.getOutput();
        if (terminalTile2.getBuilding() != null || terminalTile1.getBuilding() != null)
            return BUILDING_ON_TERMINAL.getOutput();

        Gates newGate = new Gates(player, gameMap.getMap()[xCenter][yCenter], gateType, terminalTile1, terminalTile2);

        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    protected String checkTrapErrors(int x, int y, Player player, TrapsTypes trapsType) {
        Tile targetTile = gameMap.getMap()[x][y];
        if (!trapsType.getTextures().contains(targetTile.getTexture())) return BAD_TEXTURE_TRAP.getOutput();
        if (!checkIfFit(x, y, trapsType.getSize())) return NOT_FIT.getOutput();

        for (int xTemp = x - trapsType.getSize() / 2; xTemp <= x + trapsType.getSize() / 2; xTemp++) {
            for (int yTemp = y - trapsType.getSize() / 2; yTemp <= y + trapsType.getSize() / 2; yTemp++) {
                targetTile = gameMap.getMap()[xTemp][yTemp];
                if (targetTile.getBuilding() != null) return BUILDING_EXIST_TRAP.getOutput();
                if (targetTile.getUnits().size() > 0) return UNIT_EXIST_TRAP.getOutput();
                if (targetTile.getOwner() != null && !targetTile.getOwner().equals(player))
                    return ACQUISITION.getOutput();
                if (targetTile.getMazafaza() != null && targetTile.getMazafaza().getName().contains("rock"))
                    return ROCK_EXIT_DROP_BUILDING.getOutput();
            }
        }


        return null;
    }

    protected String buildPitchDitch(int x, int y, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        String errorCheck = checkTrapErrors(x, y, player, TrapsTypes.PITCH_DITCH);
        if (errorCheck != null) return errorCheck;

        targetTile.setBuilding(new PitchDitch(player, targetTile));
        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    protected String buildCagedWarDogs(int x, int y, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        String errorCheck = checkTrapErrors(x, y, player, TrapsTypes.CAGED_WAR_DOGS);
        if (errorCheck != null) return errorCheck;

        targetTile.setBuilding(new CagedWarDogs(player, targetTile));
        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    protected String buildKillingPit(int x, int y, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        String errorCheck = checkTrapErrors(x, y, player, TrapsTypes.KILLING_PIT);
        if (errorCheck != null) return errorCheck;

        targetTile.setBuilding(new KillingPit(player, targetTile));
        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    protected String buildStore(int xCenter, int yCenter, Player player) {
        Tile targetTile;
        int size = 3;
        if (!checkIfFit(xCenter, yCenter, size)) return NOT_FIT.getOutput();
        for (int x = xCenter - size; x <= xCenter + size; x++) {
            for (int y = yCenter - size; y <= yCenter + size; y++) {
                targetTile = gameMap.getMap()[x][y];
                if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();
                if (RegularTextureGroups.NORMAL.getTextureHashSet().contains(targetTile.getTexture()))
                    return DROP_BUILDING_TEXTURE.getOutput();
                if (targetTile.getOwner() != null && !targetTile.getOwner().equals(player))
                    return ACQUISITION.getOutput();
                if (targetTile.getMazafaza() != null && targetTile.getMazafaza().getName().contains("rock"))
                    return ROCK_EXIT_DROP_BUILDING.getOutput();
            }
        }

        targetTile = gameMap.getMap()[xCenter][yCenter];
        Store newStore = new Store(player, targetTile);

        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

}
