package controller.gameControllers;

import Model.Buildings.Barracks;
import Model.Buildings.Building;
import Model.Buildings.Defending.Enums.TowerTypes;
import Model.Buildings.Defending.Towers;
import Model.Buildings.Enums.BarracksType;
import Model.Buildings.Enums.Resources;
import Model.Field.GameMap;
import Model.Field.Texture;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Combat.CombatUnit;
import Model.Units.Combat.Throwers;
import Model.Units.Combat.Troop;
import Model.Units.Combat.WallClimber;
import Model.Units.Engineer;
import Model.Units.Enums.AttackingMode;
import Model.Units.Enums.ThrowerTypes;
import Model.Units.Enums.TroopTypes;
import Model.Units.Enums.WallClimberTypes;
import Model.Units.Unit;
import controller.interfaces.UnitInterface;
import view.Game.GameMenu;

import static controller.Enums.InputOptions.*;
import static controller.Enums.Response.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class UnitController extends GeneralGameController implements UnitInterface {

    UnitController(GameMap gameMap) {
        super(gameMap);
    }

    public String setState(Matcher matcher, Player player, GameMenu gameMenu) {
        ArrayList<Unit> units = gameMenu.getSelectedUnits();
        if (units.size() == 0) return SET_STATE_NO_SELECTED_UNIT.getOutput();
        String stateString = matcher.group("stateInfo");
        AttackingMode state = AttackingMode.getAttackingModeByName(stateString);
        if (state == null) return INVALID_UNIT_STATE.getOutput();

        for (Unit unit : units) {
            if (unit instanceof Troop) {
                Troop troop = (Troop) unit;
                troop.setMode(state);
            }
        }

        return SUCCESSFUL_SET_STATE.getOutput();
    }

    public String addUnitMatcherHandler(Matcher matcher, Player player) {
        String unitInfo = matcher.group("unitInfo");
        HashMap<String, String> infoMap = getOptions(DROP_UNIT.getKeys(), unitInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String checkCoordinatesResult = checkCoordinates(infoMap, "x", "y");
        if (checkCoordinatesResult != null) return checkCoordinatesResult;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        Tile targetTile = gameMap.getMap()[x][y];
        Texture targetTileTexture = targetTile.getTexture();

        if (targetTileTexture.getName().equals("water"))
            return INVALID_TILE_DROP_UNIT.getOutput();

        if (!infoMap.get("c").matches("\\d+")) return INVALID_UNIT_AMOUNT.getOutput();
        int amount = Integer.parseInt(infoMap.get("c"));

        String type = infoMap.get("t");

        ThrowerTypes throwerType = ThrowerTypes.getThrowerTypeByName(type);
        if (throwerType != null) {
            return addThrower(x, y, throwerType, player);
        }

        TroopTypes troopType = TroopTypes.getTroopTypeByName(type);
        if (troopType != null) {
            return addTroop(troopType, amount, player, gameMap.getMap()[x][y]);
        }

        if (type.equals("wall climber")) {
            for (int i = 0; i < amount; i++) {
                new WallClimber(player, targetTile);
            }
            return SUCCESSFUL_DROP_UNIT.getOutput();
        }

        if (type.equals("engineer")) {
            return addEngineer(player, amount, gameMap.getMap()[x][y]);
        }
        if (type.equals("ladder man")) {

        }
        if (type.equals("assassin")) {

        }
        if (type.equals("tunneller")) {

        }
        return INVALID_UNIT_TYPE.getOutput();
    }

    public String addTroopMatcherHandler(Matcher matcher, Player player, Tile tile) {
        String troopInfo = matcher.group("troopInfo");
        HashMap<String, String> infoMap = getOptions(ADD_TROOP.getKeys(), troopInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        if (!infoMap.get("a").matches("\\d+")) return INVALID_AMOUNT_UNIT.getOutput();

        int amount = Integer.parseInt(infoMap.get("a"));

        TroopTypes troopType = TroopTypes.getTroopTypeByName(infoMap.get("t"));
        if (troopType == null) return INVALID_TROOP_TYPE_UNIT.getOutput();

        return addTroop(troopType, amount, player, tile);
    }
    private String addTroop(TroopTypes troopType, int amount, Player player, Tile tile) {
        Barracks building = (Barracks) tile.getBuilding();
        if (!building.getProducts().contains(troopType)) return NOT_RIGHT_PLACE_UNIT.getOutput();

        //should i check our popularity?

        int allGold = amount * troopType.getGold();
        if (player.getGold() < allGold) return NOT_ENOUGH_GOLD_UNIT.getOutput();

        for (Resources equipment : troopType.getEquipment()) {
            if (player.getInventory().get(equipment) < amount)
                return NOT_ENOUGH_RESOURCES_UNIT.getOutput() + equipment.getName();
        }

        for (int i = 0; i < amount; i++) {
            new Troop(player, tile, troopType);
        }

        return SUCCESSFUL_DROP_UNIT.getOutput();
    }

    public String addEngineer(Player player, int amount, Tile tile) {
        Building building = tile.getBuilding();
        if (!(building instanceof Barracks && ((Barracks) building).getType().equals(BarracksType.ENGINEER_GUILD)))
            return NOT_RIGHT_PLACE_UNIT.getOutput();

        if (player.getGold() < amount * Engineer.price) return NOT_ENOUGH_GOLD_ENGINEER.getOutput();
        if (player.getPopularity() < amount) return NOT_ENOUGH_POPULATION_ENGINEER.getOutput();

        for (int i = 0; i < amount; i++) {
            new Engineer(player, tile);
        }

        return SUCCESSFUL_ADD_ENGINEER.getOutput();
    }

    public String addThrowerMatcherHandler(Matcher matcher, Player player) {
        String throwerInfo = matcher.group("throwerInfo");
        HashMap<String, String> infoMap = getOptions(SELECT_BUILDING.getKeys(), throwerInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String checkCoordinatesError = checkCoordinates(infoMap, "x", "y");
        if (checkCoordinatesError != null) return checkCoordinatesError;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        ThrowerTypes throwerType = ThrowerTypes.getThrowerTypeByName(infoMap.get("t"));
        if (throwerType == null) return INVALID_THROWER_TYPE.getOutput();

        return addThrower(x, y, throwerType, player);
    }

    private String addThrower(int x, int y, ThrowerTypes throwerType, Player player) {
        if (player.getInventory().get(Resources.WOOD) < throwerType.getWoodCost())
            return NOT_ENOUGH_RESOURCES_UNIT.getOutput() + "wood";

        if (player.getInventory().get(Resources.STONE) < throwerType.getStoneCost())
            return NOT_ENOUGH_RESOURCES_UNIT.getOutput() + "stone";

        if (player.getGold() < throwerType.getGold()) return NOT_ENOUGH_GOLD_THROWER.getOutput();

        Tile targetTile = gameMap.getMap()[x][y];
        if (!targetTile.getOwner().equals(player) && !(targetTile.getOwner() == null)) return ACQUISITION.getOutput();

        if (!throwerType.getName().contains("tower")) {
            targetTile.addUnit(new Throwers(player, targetTile, throwerType));
            return SUCCESSFUL_DROP_UNIT.getOutput();
        } else {
            Building building = targetTile.getBuilding();
            if (building == null) return BUILDING_NOT_EXIST_THROWER.getOutput();
            if (!building.getOwner().equals(player)) return ACQUISITION_THROWER.getOutput();
            if (!(building instanceof Towers)) return BUILDING_NOT_PROPER_TOWER.getOutput();

            Towers tower = (Towers) building;
            if (!(tower.getType().equals(TowerTypes.SQUARE_TOWER) || tower.getType().equals(TowerTypes.ROUND_TOWER)))
                return BUILDING_NOT_PROPER_TOWER.getOutput();

            Throwers newThrower = new Throwers(player, targetTile, throwerType);
            targetTile.addUnit(newThrower);
            tower.setThrower(newThrower);
        }

        return SUCCESSFUL_DROP_UNIT.getOutput();
    }

    public String patrol(Matcher matcher, GameMenu gameMenu) {
        String patrolInfo = matcher.group("patrolInfo");
        HashMap<String, String> infoMap = getOptions(PATROL_UNIT.getKeys(), patrolInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String checkCoordinatesError = checkCoordinates(infoMap, "x1", "y1");
        String checkCoordinatesError2 = checkCoordinates(infoMap, "x2", "y2");
        if (checkCoordinatesError != null || checkCoordinatesError2 != null)
            return INVALID_COORDINATES_PATROL.getOutput();

        int x1 = Integer.parseInt(infoMap.get("x1")) - 1;
        int y1 = Integer.parseInt(infoMap.get("y1")) - 1;
        int x2 = Integer.parseInt(infoMap.get("x2")) - 1;
        int y2 = Integer.parseInt(infoMap.get("y2")) - 1;

        Tile endTile = gameMap.getMap()[x2][y2];
        if (endTile.getTexture().equals(Texture.WATER)
         || endTile.getTexture().equals(Texture.STONE_SLAB)) return BAD_TEXTURE_END.getOutput();

        Tile startTile = gameMap.getMap()[x2][y2];
        if (startTile.getTexture().equals(Texture.WATER)
                || startTile.getTexture().equals(Texture.STONE_SLAB)) return BAD_TEXTURE_START.getOutput();

        ArrayList<Unit> units = gameMenu.getSelectedUnits();
        for (Unit unit : units) {
            unit.setPatrol(true);
            unit.setStart(gameMap.getMap()[x1][y1]);
            unit.setEnd(gameMap.getMap()[x2][y2]);
        }

        return SUCCESSFUL_PATROL.getOutput();
    }

    public String selectUnitMatcherHandler(Matcher matcher, Player player, GameMenu gameMenu) {
        String unitInfo = matcher.group("unitInfo");
        HashMap<String, String> infoMap = getOptions(SELECT_BUILDING.getKeys(), unitInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String checkCoordinatesError = checkCoordinates(infoMap, "x", "y");
        if (checkCoordinatesError != null) return checkCoordinatesError;

        if (!infoMap.get("c").matches("\\d+")) return INVALID_AMOUNT_SELECT_UNIT.getOutput();
        int count = Integer.parseInt(infoMap.get("c"));

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        TroopTypes troopType = TroopTypes.getTroopTypeByName(infoMap.get("t"));
        if (troopType != null) {
            return selectUnitTroop(x, y, troopType, count, player, gameMenu);
        }

        if (infoMap.get("t").equals("engineer")) {
            return selectEngineer(x, y, count, player, gameMenu);
        }

        return INVALID_UNIT_TYPE_SELECT_UNIT.getOutput();
    }

    private String selectUnitTroop(int x, int y, TroopTypes troopType, int amount, Player player, GameMenu gameMenu) {
        Tile targetTile = gameMap.getMap()[x][y];
        ArrayList<Unit> units = targetTile.getUnits();
        int counter = 0;
        for (Unit unit : units) {
            if (unit.getOwner().equals(player) && unit instanceof Troop) {
                Troop troop = (Troop) unit;
                if (troop.getType().equals(troopType)) counter++;
            }
        }
        if (counter < amount) return NOT_ENOUGH_UNIT.getOutput();
        ArrayList<Unit> gameMenuUnits = gameMenu.getSelectedUnits();
        gameMenuUnits.clear();
        for (Unit unit : units) {
            if (counter <= 0) break;
            if (unit.getOwner().equals(player) && unit instanceof Troop) {
                Troop troop = (Troop) unit;
                if (troop.getType().equals(troopType)) {
                    gameMenuUnits.add(troop);
                    counter--;
                }
            }
        }

        return SUCCESSFUL_SELECT_UNIT.getOutput();
    }

    private String selectEngineer(int x, int y, int amount, Player player, GameMenu gameMenu) {
        Tile targetTile = gameMap.getMap()[x][y];
        ArrayList<Unit> units = targetTile.getUnits();
        int counter = 0;
        for (Unit unit : units) {
            if (unit.getOwner().equals(player) && unit instanceof Engineer) {
                counter++;
            }
        }
        if (counter < amount) return NOT_ENOUGH_UNIT.getOutput();
        ArrayList<Unit> gameMenuUnits = gameMenu.getSelectedUnits();
        gameMenuUnits.clear();
        for (Unit unit : units) {
            if (counter <= 0) break;
            if (unit.getOwner().equals(player) && unit instanceof Engineer) {
                gameMenuUnits.add(unit);
                counter--;
            }
        }
        return SUCCESSFUL_SELECT_UNIT.getOutput();
    }
}
