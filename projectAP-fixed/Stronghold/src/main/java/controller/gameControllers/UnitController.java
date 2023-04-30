package controller.gameControllers;

import Model.Buildings.Barracks;
import Model.Buildings.Enums.Resources;
import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;
import Model.Units.Engineer;
import Model.Units.Enums.TroopTypes;
import controller.interfaces.UnitInterface;
import static controller.Enums.InputOptions.*;
import static controller.Enums.Response.*;

import java.util.HashMap;
import java.util.regex.Matcher;

public class UnitController extends GeneralGameController implements UnitInterface {
    GameController gameController;
    GameMap gameMap;

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

    public String addTroop(TroopTypes troopType, int amount, Player player, Tile tile) {
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
}
