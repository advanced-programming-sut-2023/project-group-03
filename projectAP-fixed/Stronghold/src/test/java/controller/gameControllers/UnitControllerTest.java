package controller.gameControllers;

import Model.Buildings.Barracks;
import static view.Enums.GameMenuCommands.*;
import static controller.ControllerFunctions.*;
import static controller.Enums.Response.*;

import Model.Buildings.Building;
import Model.Buildings.Defending.Enums.TowerTypes;
import Model.Buildings.Defending.Towers;
import Model.Buildings.Enums.BarracksType;
import Model.Buildings.Enums.InventoryTypes;
import Model.Buildings.Enums.Resources;
import Model.Buildings.Inventory;
import Model.Buildings.Keep;
import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Enums.TroopTypes;
import Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class UnitControllerTest {
    GameMap gameMap;
    UnitController unitController ;
    Player player;

    @BeforeEach
    void setup() {
        gameMap = new GameMap(200);
        unitController = new UnitController(gameMap);
        player = new Player(new User(null, null, null, null, null),null);
        Tile.setGameMap(gameMap);
    }

    @Test
    void checkInvalidUnitType() {
        assertEquals(INVALID_COMMAND.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit khar", CREATE_UNIT.toString()), null, null));
        assertEquals(INVALID_COMMAND.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t salam -x 1 -y 2 dard", CREATE_UNIT.toString()),
                        null, null));
    }

    @Test
    void checkCoordinates() {
        assertEquals(INVALID_X_MAP.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t salam -x 201 -y 2 -c 1", CREATE_UNIT.toString()),
                        null, null));
        assertEquals(INVALID_X_MAP.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t salam -x -1000 -y 2 -c 1", CREATE_UNIT.toString()),
                        null, null));
        assertEquals(INVALID_Y_MAP.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t salam -x 23 -y 201 -c 1", CREATE_UNIT.toString()),
                        null, null));
    }

    @Test
    void checkAmount() {
        assertEquals(INVALID_UNIT_AMOUNT.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t salam -x 23 -y 25 -c damn", CREATE_UNIT.toString()),
                        null, null));
    }

    @Test
    void checkInvalidType() {
        assertEquals(INVALID_UNIT_TYPE.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t salam -x 23 -y 2 -c 1", CREATE_UNIT.toString()),
                        null, null));
    }

    @Test
    void checkGoldForUnitDrop() {
        TroopTypes troopType = TroopTypes.SWORDMEN;
        int goldCost = troopType.getGold();
        player.setGold(goldCost - 1);
        assertEquals(NOT_ENOUGH_GOLD_UNIT.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t swordmen -x 23 -y 2 -c 1", CREATE_UNIT.toString()),
                        player, null));
        troopType = TroopTypes.HORSE_ARCHERS;
        goldCost = troopType.getGold();
        player.setGold(goldCost - 1);
        assertEquals(NOT_ENOUGH_GOLD_UNIT.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t \"horse archers\" -x 23 -y 2 -c 1", CREATE_UNIT.toString()),
                        player, null));

    }

    @Test
    void checkValidDropUnit() {
        TroopTypes troopType = TroopTypes.HORSE_ARCHERS;
        int goldCost = troopType.getGold() * 10;
        player.setGold(goldCost);
        assertEquals(SUCCESSFUL_DROP_UNIT.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t \"horse archers\" -x 23 -y 2 -c 10", CREATE_UNIT.toString()),
                        player, null));
    }

    @Test
    void checkAddEngineer() {
        player.setCurrentPopulation(9);
        assertEquals(NOT_ENOUGH_POPULATION_ENGINEER.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t engineer -x 23 -y 2 -c 10", CREATE_UNIT.toString()),
                        player, null));
        player.setCurrentPopulation(11);
        assertEquals(SUCCESSFUL_ADD_ENGINEER.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t engineer -x 23 -y 2 -c 10", CREATE_UNIT.toString()),
                        player, null));
    }

    @Test
    void checkAddThrowerTowerTypes() {
        assertEquals(BUILDING_NOT_EXIST_THROWER.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t \"trebuchets on tower\" -x 23 -y 2 -c 10", CREATE_UNIT.toString()),
                        player, null), "no building exist");

        Tile targetTile = gameMap.getMap()[22][4];
        player.setKeep(new Keep(player, gameMap.getMap()[50][50]));
        targetTile.setBuilding(new Inventory(player, targetTile, InventoryTypes.STOCKPILE));
        assertEquals(BUILDING_NOT_PROPER_TOWER.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t \"trebuchets on tower\" -x 23 -y 5 -c 10", CREATE_UNIT.toString()),
                        player, null), "no proper building");

        targetTile.setBuilding(new Towers(player, targetTile, TowerTypes.LOOKOUT_TOWER));
        assertEquals(BUILDING_NOT_PROPER_TOWER.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t \"trebuchets on tower\" -x 23 -y 5 -c 10", CREATE_UNIT.toString()),
                        player, null), "no proper building");
        targetTile.setBuilding(new Towers(player, targetTile, TowerTypes.ROUND_TOWER));
        assertEquals(SUCCESSFUL_DROP_UNIT.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -t \"trebuchets on tower\" -x 23 -y 5 -c 10", CREATE_UNIT.toString()),
                        player, null), "no proper building");
    }

    @Test
    void checkEquipments() {
        player.getInventory().put(Resources.SWORD, 0);
        assertEquals(NOT_ENOUGH_RESOURCES_UNIT.getOutput() + "sword",
                unitController.addUnitMatcherHandler(getMatcher("create unit -x 23 -t swordmen -y 2 -c 1", CREATE_UNIT.toString()),
                        player, null));

        player.getInventory().put(Resources.SWORD, 1);
        assertEquals(NOT_ENOUGH_RESOURCES_UNIT.getOutput() + "sword",
                unitController.addUnitMatcherHandler(getMatcher("create unit -x 23 -t swordmen -y 2 -c 2", CREATE_UNIT.toString()),
                        player, null));

        player.getInventory().put(Resources.SWORD, 2);
        assertEquals(SUCCESSFUL_DROP_UNIT.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -x 23 -t swordmen -y 2 -c 2", CREATE_UNIT.toString()),
                        player, null));
        assertEquals(player.getInventory().get(Resources.SWORD), 0);
    }

    @Test
    void checkProperBuildingAddTroop() {
        Tile targetTile = gameMap.getMap()[50][50];
        player.setKeep(new Keep(player, targetTile));
        Barracks barracks = new Barracks(player, targetTile, BarracksType.SIEGE_TENT);
        assertEquals(NOT_RIGHT_PLACE_UNIT.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -x 23 -t swordmen -y 2 -c 2", CREATE_UNIT.toString()),
                        player, barracks));
        barracks = new Barracks(player, targetTile, BarracksType.BARRACK);
        assertEquals(SUCCESSFUL_DROP_UNIT.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -x 23 -t swordmen -y 2 -c 2", CREATE_UNIT.toString()),
                        player, barracks));
        barracks = new Barracks(player, targetTile, BarracksType.MERCENARY_POST);
        assertEquals(SUCCESSFUL_DROP_UNIT.getOutput(),
                unitController.addUnitMatcherHandler(getMatcher("create unit -x 23 -t \"horse archers\" -y 2 -c 2", CREATE_UNIT.toString()),
                        player, barracks));
    }

}