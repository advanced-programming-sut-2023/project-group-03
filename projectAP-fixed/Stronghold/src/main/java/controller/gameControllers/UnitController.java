package controller.gameControllers;

import Model.Buildings.Barracks;
import Model.Buildings.Building;
import Model.Buildings.Defending.Enums.TowerTypes;
import Model.Buildings.Defending.Gates;
import Model.Buildings.Defending.Towers;
import Model.Buildings.Defending.Wall;
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
import Model.Units.Unit;
import controller.interfaces.UnitInterface;
import view.Game.GameMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

import static controller.Enums.InputOptions.*;
import static controller.Enums.Response.*;

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
            return addThrower(x, y, throwerType, amount, player, barracks);
        } else if (type.equals("battering ram")) {
            return addBatteringRam(x, y, amount, player, barracks);
        } else if (type.equals("siege tower")) {
            return addSiegeTower(x, y, amount, player, barracks);
        }

        TroopTypes troopType = TroopTypes.getTroopTypeByName(type);
        if (troopType != null) {
            return addTroop(troopType, amount, player, gameMap.getMap()[x][y], barracks);
        } else if (type.equals("wall climber")) {
            return addWallClimber(x, y, amount, player, barracks);
        } else if (type.equals("engineer")) {
            return addEngineer(player, amount, gameMap.getMap()[x][y], barracks);
        } else if (type.equals("ladder man")) {
            return addLadderMan(x, y, amount, player, barracks);
        } else if (type.equals("portable shield")) {
            return addPortableShield(x, y, amount, player, barracks);
        }
        return INVALID_UNIT_TYPE.getOutput();
    }

    private String addPortableShield(int x, int y, int amount, Player player, Barracks barracks) {
        if (barracks != null && !barracks.getType().equals(BarracksType.SIEGE_TENT))
            return NOT_RIGHT_PLACE_UNIT.getOutput();

        if (player.getGold() * amount < PortableShields.getCost())
            return NOT_ENOUGH_GOLD_UNIT.getOutput();
        if (player.getInventory().get(Resources.WOOD) * amount < PortableShields.getWood())
            return NOT_ENOUGH_RESOURCES_UNIT.getOutput() + " wood";

        for (int i = 0; i < amount; i++) {
            new PortableShields(player, gameMap.getMap()[x][y]);
        }
        return SUCCESSFUL_DROP_UNIT.getOutput();
    }

    private String addWallClimber(int x, int y, int amount, Player player, Barracks barracks) {
        if (barracks != null && !barracks.getType().equals(BarracksType.BARRACK))
            return NOT_RIGHT_PLACE_UNIT.getOutput();

        if (player.getGold() < amount * WallClimber.getCost()) return NOT_ENOUGH_GOLD_UNIT.getOutput();

        for (int i = 0; i < amount; i++) {
            new WallClimber(player, gameMap.getMap()[x][y]);
        }

        return SUCCESSFUL_DROP_UNIT.getOutput();
    }

    private String addBatteringRam(int x, int y, int amount, Player player, Barracks barracks) {
        if (barracks != null && !barracks.getType().equals(BarracksType.SIEGE_TENT))
            return NOT_RIGHT_PLACE_UNIT.getOutput();

        if (player.getGold() * amount < BatteringRam.getGoldCost())
            return NOT_ENOUGH_GOLD_UNIT.getOutput();
        if (player.getInventory().get(Resources.STONE) * amount < BatteringRam.getStoneCost())
            return NOT_ENOUGH_RESOURCES_UNIT.getOutput() + " stone";
        if (player.getInventory().get(Resources.WOOD) * amount < BatteringRam.getWoodCost())
            return NOT_ENOUGH_RESOURCES_UNIT.getOutput() + " wood";

        for (int i = 0; i < amount; i++) {
            new BatteringRam(player, gameMap.getMap()[x][y]);
        }
        return SUCCESSFUL_DROP_UNIT.getOutput();
    }

    private String addSiegeTower(int x, int y, int amount, Player player, Barracks barracks) {
        if (!barracks.getType().equals(BarracksType.SIEGE_TENT) && barracks != null)
            return NOT_RIGHT_PLACE_UNIT.getOutput();

        if (player.getGold() * amount < SiegeTower.getGoldCost())
            return NOT_ENOUGH_GOLD_UNIT.getOutput();
        if (player.getInventory().get(Resources.STONE) * amount < SiegeTower.getStoneCost())
            return NOT_ENOUGH_RESOURCES_UNIT.getOutput() + " stone";
        if (player.getInventory().get(Resources.WOOD) * amount < SiegeTower.getWoodCost())
            return NOT_ENOUGH_RESOURCES_UNIT.getOutput() + " wood";

        for (int i = 0; i < amount; i++) {
            new SiegeTower(player, gameMap.getMap()[x][y]);
        }
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

    private String addEngineer(Player player, int amount, Tile tile, Barracks barracks) {
        if (barracks != null && !barracks.getType().equals(BarracksType.ENGINEER_GUILD))
            return NOT_RIGHT_PLACE_UNIT.getOutput();

        if (player.getGold() < amount * Engineer.price) return NOT_ENOUGH_GOLD_ENGINEER.getOutput();
        if ((player.getMaxPopulation() - player.getCurrentPopulation()) < amount)
            return NOT_ENOUGH_POPULATION_ENGINEER.getOutput();//todo engineer amount

        for (int i = 0; i < amount; i++) {
            new Engineer(player, tile);
        }

        return SUCCESSFUL_ADD_ENGINEER.getOutput();
    }

    private String addLadderMan(int x, int y, int amount, Player player, Barracks barracks) {
        if (barracks != null && !barracks.getType().equals(BarracksType.ENGINEER_GUILD))
            return NOT_RIGHT_PLACE_UNIT.getOutput();

        if (player.getGold() < amount * LadderMen.getPrice()) return NOT_ENOUGH_GOLD_UNIT.getOutput();
        for (int i = 0; i < amount; i++) {
            new LadderMen(player, gameMap.getMap()[x][y]);
        }

        return SUCCESSFUL_DROP_UNIT.getOutput();
    }

    private String addThrower(int x, int y, ThrowerTypes throwerType, int amount, Player player, Barracks barracks) {
        if (barracks != null && !barracks.getType().equals(BarracksType.SIEGE_TENT))
            return NOT_RIGHT_PLACE_UNIT.getOutput();

        int goldCost = amount * throwerType.getGold();
        if (player.getGold() < goldCost) return NOT_ENOUGH_GOLD_THROWER.getOutput();

        int currentUselessEngineers = player.getKeep().getMaxEngineerPopulation() - player.getKeep().getCurrentEngineerPopulation();
        if (currentUselessEngineers < amount * throwerType.getEngineer())
            return NOT_ENOUGH_ENGINEER_BARRACKS.getOutput();

        Tile targetTile = gameMap.getMap()[x][y];

        if (!throwerType.getName().contains("tower")) {
            for (int i = 0; i < amount; i++) {
                Throwers newThrower = new Throwers(player, targetTile, throwerType);
                ArrayList<Engineer> requiredEngineers = player.getKeep().getEngineers(throwerType.getEngineer());
                for (Engineer engineer : requiredEngineers) {
                    engineer.setJob(newThrower);
                }
            }
            return SUCCESSFUL_DROP_UNIT.getOutput();
        } else {
            if (amount > 1) return AMOUNT_TOWER_THROWER.getOutput();

            Building building = targetTile.getBuilding();
            if (building == null) return BUILDING_NOT_EXIST_THROWER.getOutput();
            if (!building.getOwner().equals(player)) return ACQUISITION_THROWER.getOutput();
            if (!(building instanceof Towers)) return BUILDING_NOT_PROPER_TOWER.getOutput();

            Towers tower = (Towers) building;
            if (!(tower.getType().equals(TowerTypes.SQUARE_TOWER) || tower.getType().equals(TowerTypes.ROUND_TOWER)))
                return BUILDING_NOT_PROPER_TOWER.getOutput();
            Throwers newThrower = new Throwers(player, targetTile, throwerType);
            ArrayList<Engineer> requiredEngineers = player.getKeep().getEngineers(throwerType.getEngineer());
            for (Engineer engineer : requiredEngineers) {
                engineer.setJob(newThrower);
            }
            tower.setThrower(newThrower);
        }

        return SUCCESSFUL_DROP_UNIT.getOutput();
    }

    public String moveUnit(Matcher matcher, GameMenu gameMenu) {
        String patrolInfo = matcher.group("placeInfo");
        HashMap<String, String> infoMap = getOptions(COORDINATES.getKeys(), patrolInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        error = checkCoordinates(infoMap, "x", "y");
        if (error != null) return error;

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        Tile targetTile = gameMap.getMap()[x][y];
        if (targetTile.getMazafaza() != null && targetTile.getMazafaza().getName().contains("rock"))
            return ROCK_EXIST_MOVE_UNIT.getOutput();
        Texture targetTexture = targetTile.getTexture();
        if (targetTexture.equals(Texture.WATER) || targetTexture.equals(Texture.STONE_SLAB))
            return BAD_TEXTURE_MOVE_UNIT.getOutput();

        for (Unit unit : gameMenu.getSelectedUnits()) {
            if (MoveUnitController.findPath(unit.getPosition(), targetTile, gameMap, unit.getOwner()).size() <= 1) {
                return UNABLE_MOVE_UNIT.getOutput();
            }
        }

        for (Unit unit : gameMenu.getSelectedUnits()) {
            unit.setStart(null);
            unit.setEnd(null);
            if (unit instanceof CombatUnit) {
                ((CombatUnit) unit).setEnemyTarget(null);
            }
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
            if (unit instanceof CombatUnit) {
                ((CombatUnit) unit).setEnemyTarget(null);
            }
            unit.setCurrentTarget(null);
            unit.setPatrol(true);
            unit.setStart(gameMap.getMap()[x1][y1]);
            unit.setEnd(gameMap.getMap()[x2][y2]);
        }

        return SUCCESSFUL_PATROL.getOutput();
    }

    public String selectUnitMatcherHandler(Matcher matcher, Player player, GameMenu gameMenu) {
        String unitInfo = matcher.group("unitInfo");
        HashMap<String, String> infoMap = getOptions(SELECT_UNIT.getKeys(), unitInfo);
        String error = infoMap.get("error");
        if (error != null) return error;

        String checkCoordinatesError = checkCoordinates(infoMap, "x", "y");
        if (checkCoordinatesError != null) return checkCoordinatesError;

        if (!infoMap.get("c").matches("\\d+")) return INVALID_AMOUNT_SELECT_UNIT.getOutput();
        int count = Integer.parseInt(infoMap.get("c"));

        int x = Integer.parseInt(infoMap.get("x")) - 1;
        int y = Integer.parseInt(infoMap.get("y")) - 1;

        String type = infoMap.get("t");

        TroopTypes troopType = TroopTypes.getTroopTypeByName(type);
        if (troopType != null) {
            return selectUnitTroop(x, y, troopType, count, player, gameMenu);
        }

        ThrowerTypes throwerType = ThrowerTypes.getThrowerTypeByName(type);
        if (throwerType != null) {
            return selectThrower(x, y, throwerType, count, player, gameMenu);
        } else if (type.equals("engineer")) {
            return selectEngineer(x, y, count, player, gameMenu);
        } else if (type.equals("battering ram")) {
            return selectBatteringRam(x, y, count, player, gameMenu);
        } else if (type.equals("ladder man")) {
            return selectLadderMan(x, y, count, player, gameMenu);
        } else if (type.equals("portable shields")) {
            return selectPortableShields(x, y, count, player, gameMenu);
        } else if (type.equals("siege tower")) {
            return selectSiegeTower(x, y, count, player, gameMenu);
        } else if (type.equals("wall climber")) {
            return selectWallClimber(x, y, count, player, gameMenu);
        }

        return INVALID_UNIT_TYPE_SELECT_UNIT.getOutput();
    }

    private String selectBatteringRam(int x, int y, int amount, Player player, GameMenu gameMenu) {
        Tile targetTile = gameMap.getMap()[x][y];
        ArrayList<Unit> units = targetTile.getUnits();
        int counter = 0;
        for (Unit unit : units) {
            if (unit.getOwner().equals(player) && unit instanceof BatteringRam) counter++;
        }
        if (counter < amount) return NOT_ENOUGH_UNIT.getOutput();
        counter = amount;
        ArrayList<Unit> gameMenuUnits = gameMenu.getSelectedUnits();
        gameMenuUnits.clear();
        gameMenu.setSelected(null);
        for (Unit unit : units) {
            if (counter <= 0) break;
            if (unit.getOwner().equals(player) && unit instanceof BatteringRam) {
                gameMenuUnits.add(unit);
                counter--;
            }
        }

        return SUCCESSFUL_SELECT_UNIT.getOutput();
    }

    private String selectWallClimber(int x, int y, int amount, Player player, GameMenu gameMenu) {
        Tile targetTile = gameMap.getMap()[x][y];
        ArrayList<Unit> units = targetTile.getUnits();
        int counter = 0;
        for (Unit unit : units) {
            if (unit.getOwner().equals(player) && unit instanceof WallClimber) counter++;
        }
        if (counter < amount) return NOT_ENOUGH_UNIT.getOutput();
        counter = amount;
        ArrayList<Unit> gameMenuUnits = gameMenu.getSelectedUnits();
        gameMenuUnits.clear();
        gameMenu.setSelected(null);
        for (Unit unit : units) {
            if (counter <= 0) break;
            if (unit.getOwner().equals(player) && unit instanceof WallClimber) {
                gameMenuUnits.add(unit);
                counter--;
            }
        }

        return SUCCESSFUL_SELECT_UNIT.getOutput();
    }

    private String selectPortableShields(int x, int y, int amount, Player player, GameMenu gameMenu) {
        Tile targetTile = gameMap.getMap()[x][y];
        ArrayList<Unit> units = targetTile.getUnits();
        int counter = 0;
        for (Unit unit : units) {
            if (unit.getOwner().equals(player) && unit instanceof PortableShields) counter++;
        }
        if (counter < amount) return NOT_ENOUGH_UNIT.getOutput();
        counter = amount;
        ArrayList<Unit> gameMenuUnits = gameMenu.getSelectedUnits();
        gameMenuUnits.clear();
        gameMenu.setSelected(null);
        for (Unit unit : units) {
            if (counter <= 0) break;
            if (unit.getOwner().equals(player) && unit instanceof PortableShields) {
                gameMenuUnits.add(unit);
                counter--;
            }
        }

        return SUCCESSFUL_SELECT_UNIT.getOutput();
    }

    private String selectSiegeTower(int x, int y, int amount, Player player, GameMenu gameMenu) {
        Tile targetTile = gameMap.getMap()[x][y];
        ArrayList<Unit> units = targetTile.getUnits();
        int counter = 0;
        for (Unit unit : units) {
            if (unit.getOwner().equals(player) && unit instanceof SiegeTower) counter++;
        }
        if (counter < amount) return NOT_ENOUGH_UNIT.getOutput();
        counter = amount;
        ArrayList<Unit> gameMenuUnits = gameMenu.getSelectedUnits();
        gameMenuUnits.clear();
        gameMenu.setSelected(null);
        for (Unit unit : units) {
            if (counter <= 0) break;
            if (unit.getOwner().equals(player) && unit instanceof SiegeTower) {
                gameMenuUnits.add(unit);
                counter--;
            }
        }

        return SUCCESSFUL_SELECT_UNIT.getOutput();
    }

    private String selectLadderMan(int x, int y, int amount, Player player, GameMenu gameMenu) {
        Tile targetTile = gameMap.getMap()[x][y];
        ArrayList<Unit> units = targetTile.getUnits();
        int counter = 0;
        for (Unit unit : units) {
            if (unit.getOwner().equals(player) && unit instanceof LadderMen) counter++;
        }
        if (counter < amount) return NOT_ENOUGH_UNIT.getOutput();
        counter = amount;
        ArrayList<Unit> gameMenuUnits = gameMenu.getSelectedUnits();
        gameMenuUnits.clear();
        gameMenu.setSelected(null);
        for (Unit unit : units) {
            if (counter <= 0) break;
            if (unit.getOwner().equals(player) && unit instanceof LadderMen) {
                gameMenuUnits.add(unit);
                counter--;
            }
        }

        return SUCCESSFUL_SELECT_UNIT.getOutput();
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
        counter = amount;
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

    private String selectThrower(int x, int y, ThrowerTypes throwerType, int amount, Player player, GameMenu gameMenu) {
        Tile targetTile = gameMap.getMap()[x][y];
        ArrayList<Unit> units = targetTile.getUnits();
        int counter = 0;
        for (Unit unit : units) {
            if (unit.getOwner().equals(player) && unit instanceof Throwers && ((Throwers) unit).getType().equals(throwerType)) {
                counter++;
            }
        }
        if (counter < amount) return NOT_ENOUGH_UNIT.getOutput();
        ArrayList<Unit> gameMenuUnits = gameMenu.getSelectedUnits();
        counter = amount;
        gameMenuUnits.clear();
        for (Unit unit : units) {
            if (counter <= 0) break;
            if (unit.getOwner().equals(player) && unit instanceof Throwers && ((Throwers) unit).getType().equals(throwerType)) {
                gameMenuUnits.add(unit);
                counter--;
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
        counter = amount;
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

        Unit tempUnit = gameMenu.getSelectedUnits().get(0);
        if (tempUnit instanceof LadderMen || tempUnit instanceof SiegeTower || tempUnit instanceof BatteringRam)
            return UNABLE_TO_ATTACK_TILE.getOutput();

        Tile targetTile = gameMap.getMap()[x][y];

        ArrayList<Unit> targetTileUnits = targetTile.getUnits();
        boolean isEnemyThere = false;
        for (Unit unit : targetTileUnits)
            if (!unit.getOwner().equals(gameMenu.getGame().getCurrentPlayer())) {
                isEnemyThere = true;
                break;
            }

        if (!isEnemyThere) return NO_UNIT_TO_ATTACK.getOutput();

        CombatUnit combatUnit;
        for (Unit unit : gameMenu.getSelectedUnits()) {
            unit.setPatrol(false);
            unit.setStart(null);
            unit.setEnd(null);
            unit.setCurrentTarget(null);
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

        if (gameMap.getMap()[x][y].getBuilding() == null || gameMap.getMap()[x][y].getBuilding().getOwner().equals(gameMenu.getGame().getCurrentPlayer()))
            return NO_BUILDING_TO_ATTACK.getOutput();

        Unit tempUnit = gameMenu.getSelectedUnits().get(0);
        if (tempUnit instanceof LadderMen || tempUnit instanceof SiegeTower) {
            if (!(gameMap.getMap()[x][y].getBuilding() instanceof Wall)) return WALL_NOT_EXIST_LADDER_MEN.getOutput();
        }
        if (tempUnit instanceof BatteringRam)
            if (!(gameMap.getMap()[x][y].getBuilding() instanceof Gates))
                return GATE_NOT_EXIT_BATTERING_RAM.getOutput();

        CombatUnit combatUnit = (CombatUnit) gameMenu.getSelectedUnits().get(0);
        Material unitMaterial = combatUnit.getTargets().get(0);
        Building building = gameMap.getMap()[x][y].getBuilding();
        if (building.getMaterial().getValue() > unitMaterial.getValue()) return UNABLE_TO_ATTACK_BUILDING.getOutput();

        for (Unit unit : gameMenu.getSelectedUnits()) {
            unit.setStart(null);
            unit.setEnd(null);
            unit.setCurrentTarget(null);
            unit.setPatrol(false);
            combatUnit = (CombatUnit) unit;
            combatUnit.setEnemyTarget(building);
        }
        return SUCCESSFUL_ATTACK_BUILDING.getOutput();
    }

    public void disbandUnit(GameMenu gameMenu) {
        for (Unit unit : gameMenu.getSelectedUnits()) {
            unit.setStart(null);
            unit.setEnd(null);
            if (unit instanceof CombatUnit) {
                ((CombatUnit) unit).setEnemyTarget(null);
            }
            unit.setPatrol(false);
            unit.setCurrentTarget(unit.getOwner().getKeep().getPosition());
        }
    }
}
