package controller.gameControllers;

import Model.GamePlay.Player;
import controller.Controller;
import controller.interfaces.*;

import java.util.regex.Matcher;

public class GameController extends Controller implements GameMarketInterface{
    private BuildingController buildingController;
    private KingdomController kingdomController;
    private MarketController marketController;
    private MoveUnitController moveUnitController;
    private TradeController tradeController;
    private UnitController unitController;

    public GameController() {
        this.buildingController = new BuildingController(this);
        this.kingdomController = new KingdomController(this);
        this.marketController = new MarketController(this);
        this.moveUnitController = new MoveUnitController(this);
        this.tradeController = new TradeController(this);
        this.unitController = new UnitController(this);
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
}
