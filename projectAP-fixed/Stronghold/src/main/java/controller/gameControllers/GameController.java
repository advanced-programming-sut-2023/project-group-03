package controller.gameControllers;

import Model.Field.GameMap;
import Model.GamePlay.Player;
import controller.Controller;
import controller.interfaces.*;

import java.util.regex.Matcher;

public class GameController extends Controller implements GameMarketInterface , BuildingInterface {
    private BuildingController buildingController;
    private KingdomController kingdomController;
    private MarketController marketController;
    private MoveUnitController moveUnitController;
    private TradeController tradeController;
    private UnitController unitController;

    private GameMap gameMap;

    public GameController(GameMap gameMap) {
        this.buildingController = new BuildingController(this);
        this.kingdomController = new KingdomController(this);
        this.marketController = new MarketController(this);
        this.moveUnitController = new MoveUnitController(this);
        this.tradeController = new TradeController(this);
        this.unitController = new UnitController(this);
        this.gameMap = gameMap;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    @Override
    public String showPriceList(Player player) {
        return marketController.showPriceList(player);
    }

    @Override
    public String buyMatcherHandler(Matcher matcher, Player player) {
        return marketController.buyMatcherHandler(matcher, player);
    }

    @Override
    public String sellMatcherHandler(Matcher matcher, Player player) {
        return marketController.sellMatcherHandler(matcher, player);
    }

    @Override
    public String buildTowerMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildTowerMatcherHandler(matcher, player);
    }

    @Override
    public String buildWallMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildWallMatcherHandler(matcher, player);
    }

    @Override
    public String buildBarracksMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildBarracksMatcherHandler(matcher, player);
    }

    @Override
    public String buildInventoryMatcherHandler(Matcher matcher, Player player) {
        return buildingController.buildInventoryMatcherHandler(matcher, player);
    }
}
