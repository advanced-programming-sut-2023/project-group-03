package controller.gameControllers;

import Model.Buildings.Barracks;
import Model.Buildings.Building;
import Model.Buildings.Defending.Enums.TowerTypes;
import Model.Buildings.Defending.Enums.WallTypes;
import Model.Buildings.Defending.Towers;
import Model.Buildings.Defending.Wall;
import Model.Buildings.Enums.BarracksType;
import Model.Buildings.Enums.InventoryTypes;
import Model.Buildings.Enums.Resources;
import Model.Buildings.Inventory;
import Model.Field.Tile;
import Model.GamePlay.Player;
import controller.Controller;
import controller.interfaces.BuildingInterface;
import static controller.Enums.InputOptions.*;
import static controller.Enums.Response.*;

import java.util.AbstractQueue;
import java.util.HashMap;
import java.util.regex.Matcher;

public class BuildingController extends GeneralGameController implements BuildingInterface {
    GameController gameController;

    BuildingController(GameController gameController) {
        super(gameController.getGameMap());
        this.gameController = gameController;
    }

    public String selectBuilding(Matcher matcher) {
        //return a proper response if there is no building
        //select if there is no problem
        return "";
    }

    public String repair(Matcher matcher) {
        //check if you have the elements and there is no enemy around
        return "";
    }

    @Override
    public String buildTowerMatcherHandler(Matcher matcher, Player player) {
        String towerInfo = matcher.group("towerInfo");
        HashMap<String, String> infoMap = getOptions(BUILD_TOWER.getKeys(), towerInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String coordinatesError = checkCoordinates(infoMap, "x", "y");
        if (coordinatesError != null) return coordinatesError;

        int x = Integer.parseInt(infoMap.get("x"));
        int y = Integer.parseInt(infoMap.get("y"));

        TowerTypes towerType = TowerTypes.getTypeByName(infoMap.get("t"));
        if (towerType == null) return INVALID_BUILDING_TYPE.getOutput();

        return buildTower(x, y, towerType, player);
    }

    private String buildTower(int x, int y, TowerTypes towerType, Player player) {
        Tile targettile = gameMap.getMap()[x][y];
        if (targettile.getBuilding() != null) return BUILDING_EXIST.getOutput();

        if (player.getInventory().get(Resources.STONE) < towerType.getStoneCost()) return NOT_ENOUGH_STONE_TOWER.getOutput();

        if (!targettile.getOwner().equals(player)) return ACQUISITION.getOutput();

        targettile.setBuilding(new Towers(player, targettile, towerType));

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

        int x = Integer.parseInt(infoMap.get("x"));
        int y = Integer.parseInt(infoMap.get("y"));

        WallTypes wallType = WallTypes.getTypeByName(infoMap.get("t"));
        if (wallType == null) return INVALID_WALL_TYPE.getOutput();

        return buildWall(x, y, wallType, player);
    }

    private String buildWall(int x, int y, WallTypes wallType, Player player) {
        Tile targettile = gameMap.getMap()[x][y];
        if (targettile.getBuilding() != null) return BUILDING_EXIST.getOutput();

        if (player.getInventory().get(Resources.STONE) < wallType.getStoneCost()) return NOT_ENOUGH_STONE_WALL.getOutput();

        if (!targettile.getOwner().equals(player)) return ACQUISITION.getOutput();

        targettile.setBuilding(new Wall(player, targettile, wallType));

        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    public String buildBarracksMatcherHandler(Matcher matcher, Player player) {
        String barracksInfo = matcher.group("barracksInfo");
        HashMap<String, String> infoMap = getOptions(BUILD_BARRACKS.getKeys(), barracksInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String coordinatesError = checkCoordinates(infoMap, "x", "y");
        if (coordinatesError != null) return coordinatesError;

        int x = Integer.parseInt(infoMap.get("x"));
        int y = Integer.parseInt(infoMap.get("y"));

        BarracksType barracksType = BarracksType.getTypeByName(infoMap.get("t"));
        if (barracksType == null) return INVALID_BARRACKS_TYPE.getOutput();

        return buildBarracks(x, y, barracksType, player);
    }

    private String buildBarracks(int x, int y, BarracksType barracksType, Player player) {
        Tile targettile = gameMap.getMap()[x][y];
        if (targettile.getBuilding() != null) return BUILDING_EXIST.getOutput();

        if ((player.getInventory().get(Resources.STONE) < barracksType.getStoneCost()))
            return NOT_ENOUGH_STONE_TOWER.getOutput();
        if ((player.getInventory().get(Resources.WOOD) < barracksType.getWood()))
            return NOT_ENOUGH_WOOD_BARRACKS.getOutput();
        if ((player.getGold() < barracksType.getGold()))
            return NOT_ENOUGH_GOLD_BARRACKS.getOutput();
        if ((player.getInventory().get(Resources.OIL) < barracksType.getOil()))
            return NOT_ENOUGH_OIL_BARRACKS.getOutput();

        targettile.setBuilding(new Barracks(player, targettile, barracksType));

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

        int x = Integer.parseInt(infoMap.get("x"));
        int y = Integer.parseInt(infoMap.get("y"));

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

        if (player.getInventory().get(Resources.STONE) < InventoryTypes.ARMOURY.getStoneCost())
            return NOT_ENOUGH_STONE_ARMOURY.getOutput();

        if (!targetTile.getOwner().equals(player)) return ACQUISITION.getOutput();

        targetTile.setBuilding(new Inventory(player, targetTile, InventoryTypes.ARMOURY));
        return SUCCESSFUL_DROP_BUILDING.getOutput();
    }

    private String buildStockPileFoodStorage(int x, int y, InventoryTypes inventoryType, Player player) {
        Tile targetTile = gameMap.getMap()[x][y];
        if (targetTile.getBuilding() != null) return BUILDING_EXIST.getOutput();

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


}
