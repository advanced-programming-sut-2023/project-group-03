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
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.Units.Combat.*;
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

    public String addUnitMatcherHandler(Matcher matcher, Player player, Barracks barracks) {
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
            return addThrower(x, y, throwerType, player, barracks);
        }

        if (type.equals("battering ram")) {
            return addBatteringRam(x, y, player, barracks);
        }

        if (type.equals("siege tower")) {
            return addSiegeTower(x, y, player, barracks);
        }

        TroopTypes troopType = TroopTypes.getTroopTypeByName(type);
        if (troopType != null) {
            return addTroop(troopType, amount, player, gameMap.getMap()[x][y], barracks);
        }

        if (type.equals("wall climber")) {
            for (int i = 0; i < amount; i++) {
                new WallClimber(player, targetTile);
            }
            return SUCCESSFUL_DROP_UNIT.getOutput();
        }

        if (type.equals("engineer")) {
            return addEngineer(player, amount, gameMap.getMap()[x][y], barracks);
        }
        if (type.equals("ladder man")) {

        }
        if (type.equals("assassin")) {
            //todo
        }
        return INVALID_UNIT_TYPE.getOutput();
    }

    private String addBatteringRam(int x, int y, Player player, Barracks barracks) {
        if (!barracks.getType().equals(BarracksType.SIEGE_TENT) && barracks != null) return NOT_RIGHT_PLACE_UNIT.getOutput();

        if (player.getGold() < BatteringRam.getGoldCost())
            return NOT_ENOUGH_GOLD_UNIT.getOutput();
        if (player.getInventory().get(Resources.STONE) < BatteringRam.getStoneCost())
            return NOT_ENOUGH_RESOURCES_UNIT.getOutput() + " stone";
        if (player.getInventory().get(Resources.WOOD) < BatteringRam.getWoodCost())
            return NOT_ENOUGH_RESOURCES_UNIT.getOutput() + " wood";

        new BatteringRam(player, gameMap.getMap()[x][y]);
        return SUCCESSFUL_DROP_UNIT.getOutput();
    }

    private String addSiegeTower(int x, int y, Player player, Barracks barracks) {
        if (!barracks.getType().equals(BarracksType.SIEGE_TENT) && barracks != null) return NOT_RIGHT_PLACE_UNIT.getOutput();

        if (player.getGold() < SiegeTower.getGoldCost())
            return NOT_ENOUGH_GOLD_UNIT.getOutput();
        if (player.getInventory().get(Resources.STONE) < SiegeTower.getStoneCost())
            return NOT_ENOUGH_RESOURCES_UNIT.getOutput() + " stone";
        if (player.getInventory().get(Resources.WOOD) < SiegeTower.getWoodCost())
            return NOT_ENOUGH_RESOURCES_UNIT.getOutput() + " wood";

        new SiegeTower(player, gameMap.getMap()[x][y]);
        return SUCCESSFUL_DROP_UNIT.getOutput();
    }

    private String addTroop(TroopTypes troopType, int amount, Player player, Tile tile, Barracks barracks) {
        if (barracks != null && !barracks.getProducts().contains(troopType)) return NOT_RIGHT_PLACE_UNIT.getOutput();

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

    public String addEngineer(Player player, int amount, Tile tile, Barracks barracks) {
        if (barracks != null && !barracks.getType().equals(BarracksType.ENGINEER_GUILD))
            return NOT_RIGHT_PLACE_UNIT.getOutput();

        if (player.getGold() < amount * Engineer.price) return NOT_ENOUGH_GOLD_ENGINEER.getOutput();
        if (player.getCurrentPopulation() < amount) return NOT_ENOUGH_POPULATION_ENGINEER.getOutput();//todo engineer amount

        for (int i = 0; i < amount; i++) {
            new Engineer(player, tile);
        }

        return SUCCESSFUL_ADD_ENGINEER.getOutput();
    }
    private String addThrower(int x, int y, ThrowerTypes throwerType, Player player, Barracks barracks) {
        if (barracks != null && !barracks.getType().equals(BarracksType.SIEGE_TENT))
            return NOT_RIGHT_PLACE_UNIT.getOutput();

        if (player.getGold() < throwerType.getGold()) return NOT_ENOUGH_GOLD_THROWER.getOutput();

        Tile targetTile = gameMap.getMap()[x][y];

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

    public String moveUnit(Matcher matcher, GameMenu gameMenu) {
        String patrolInfo = matcher.group("patrolInfo");
        HashMap<String, String> infoMap = getOptions(PATROL_UNIT.getKeys(), patrolInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        error = checkCoordinates(infoMap, "x", "y");
        if (error != null) return error;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        Tile targetTile = gameMap.getMap()[x][y];
        if (targetTile.getMazafaza() != null && targetTile.getMazafaza().getName().contains("rock")) return ROCK_EXIST_MOVE_UNIT.getOutput();
        Texture targetTexture = targetTile.getTexture();
        if (targetTexture.equals(Texture.WATER) || targetTexture.equals(Texture.STONE_SLAB)) return BAD_TEXTURE_MOVE_UNIT.getOutput();

        for (Unit unit : gameMenu.getSelectedUnits()) {
            if (MoveUnitController.findPath(unit.getPosition(), targetTile, gameMap,gameMenu.getGame().getCurrentPlayer()).size() <= 1) {
                return UNABLE_MOVE_UNIT.getOutput();
            }
        }

        for (Unit unit : gameMenu.getSelectedUnits()) {
            unit.setPatrol(false);
            unit.setCurrentTarget(targetTile);
        }
        return SUCCESSFUL_MOVE_UNIT.getOutput();
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
        gameMenu.setSelected(null);
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

    public String attackMatcherHandler(Matcher matcher, GameMenu gameMenu) {
        String attackInfo = matcher.group("attackInfo");
        HashMap<String, String> infoMap = getOptions(COORDINATES.getKeys(), attackInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String coordinatesCheck = checkCoordinates(infoMap, "x", "y");
        if (coordinatesCheck != null) return coordinatesCheck;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        return normalAttack(x, y, gameMenu);
    }

    private String normalAttack(int x, int y, GameMenu gameMenu) {
        if (gameMenu.getSelectedUnits().size() == 0) return NO_UNIT_SELECTED.getOutput();
        if (!(gameMenu.getSelectedUnits().get(0) instanceof CombatUnit)) return NO_COMBAT_UNIT_SELECTED.getOutput();

        Tile targetTile = gameMap.getMap()[x][y];

        CombatUnit combatUnit;
        for (Unit unit : gameMenu.getSelectedUnits()) {
            unit.setPatrol(false);
            combatUnit = (CombatUnit) unit;
            combatUnit.setCurrentTarget(targetTile);
        }
        return SUCCESSFUL_ATTACK.getOutput();
    }

    public String attackToBuildingMatcherHandler(Matcher matcher, GameMenu gameMenu) {
        String attackInfo = matcher.group("attackInfo");
        HashMap<String, String> infoMap = getOptions(COORDINATES.getKeys(), attackInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        error = checkCoordinates(infoMap, "x", "y");
        if (error != null) return error;
        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        return attackToBuilding(x, y, gameMenu);
    }

    private String attackToBuilding(int x, int y, GameMenu gameMenu) {
        if (gameMenu.getSelectedUnits().size() == 0) return NO_UNIT_SELECTED.getOutput();
        if (!(gameMenu.getSelectedUnits().get(0) instanceof CombatUnit)) return NO_COMBAT_UNIT_SELECTED.getOutput();

        if (gameMap.getMap()[x][y].getBuilding() == null) return NO_BUILDING_TO_ATTACK.getOutput();

        CombatUnit combatUnit = (CombatUnit) gameMenu.getSelectedUnits().get(0);
        Material unitMaterial = combatUnit.getTargets().iterator().next();

        Building building = gameMap.getMap()[x][y].getBuilding();
        if (building.getMaterial().getValue() > unitMaterial.getValue()) return UNABLE_TO_ATTACK_BUILDING.getOutput();

        for (Unit unit : gameMenu.getSelectedUnits()) {
            unit.setPatrol(false);
            combatUnit = (CombatUnit) unit;
            combatUnit.setEnemyTarget(building);
        }
        return SUCCESSFUL_ATTACK_BUILDING.getOutput();
    }

    public void disbandUnit(GameMenu gameMenu) {
        for (Unit unit : gameMenu.getSelectedUnits()) {
            unit.setPatrol(false);
            unit.setCurrentTarget(unit.getOwner().getKeep().getPosition());
        }
    }
}
