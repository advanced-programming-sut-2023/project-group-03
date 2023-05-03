package controller.gameControllers;

import Model.Buildings.Barracks;
import Model.Buildings.Enums.Resources;
import Model.Field.GameMap;
import Model.Field.Texture;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;
import Model.Units.Engineer;
import Model.Units.Enums.TroopTypes;
import Model.Units.Unit;
import controller.interfaces.UnitInterface;
import view.Game.GameMenu;

import static controller.Enums.InputOptions.*;
import static controller.Enums.Response.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class UnitController extends GeneralGameController implements UnitInterface {
    GameController gameController;

    UnitController(GameController gameController) {
        super(gameController.getGameMap());
        this.gameController = gameController;
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
            Troop newTroop = new Troop(player, tile, troopType);
            tile.addUnit(newTroop);
        }

        return SUCCESSFUL_DROP_UNIT.getOutput();
    }

    public String addEngineer(Player player, String amountString, Tile tile) {
        if (!amountString.matches("\\d+")) return INVALID_AMOUNT_UNIT.getOutput();
        int amount = Integer.parseInt(amountString);

        if (player.getGold() < amount * Engineer.price) return NOT_ENOUGH_GOLD_ENGINEER.getOutput();
        if (player.getPopularity() < amount) return NOT_ENOUGH_POPULATION_ENGINEER.getOutput();

        for (int i = 0; i < amount; i++) {
            Engineer newEngineer = new Engineer(player, tile);
            tile.addUnit(newEngineer);
        }

        return SUCCESSFUL_ADD_ENGINEER.getOutput();
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
        if (endTile.getTexture().equals(Texture.WATER) || endTile.getTexture().equals(Texture.OIL)
         || endTile.getTexture().equals(Texture.STONE_SLAB)) return BAD_TEXTURE_END.getOutput();

        Tile startTile = gameMap.getMap()[x2][y2];
        if (startTile.getTexture().equals(Texture.WATER) || startTile.getTexture().equals(Texture.OIL)
                || startTile.getTexture().equals(Texture.STONE_SLAB)) return BAD_TEXTURE_START.getOutput();

        ArrayList<Unit> units = gameMenu.getSelectedUnits();
        for (Unit unit : units) {
            unit.setPatrol(true);
            unit.setStart(gameMap.getMap()[x1][y1]);
            unit.setEnd(gameMap.getMap()[x2][y2]);
        }

        return SUCCESSFUL_PATROL.getOutput();
    }
}
