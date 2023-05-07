package controller.gameControllers;

import static view.Enums.GameMenuCommands.*;
import static controller.ControllerFunctions.*;
import static controller.Enums.Response.*;
import static view.Enums.ShopMenuCommands.*;
import static Model.Buildings.Enums.Resources.*;

import Model.GamePlay.Player;
import Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketControllerTest {
    Player player;
    MarketController marketController;

    @BeforeEach
    void setup() {
        User user = new User(null, "ali", "ali", "ali@.com", null);
        player = new Player(user, null);
        marketController = new MarketController();
    }

    @Test
    void checkShowPriceList() {
        System.out.println(marketController.showPriceList(player));
    }

    @Test
    void checkBuy() {
        assertEquals(INVALID_COMMAND.getOutput(),
            marketController.buyMatcherHandler(getMatcher("buy sdlfkjslkf", BUY.toString()), player)
            , "invalid message");

        player.setGold(HORSE.getGold() * 10 - 1);
        assertEquals(NOT_ENOUGH_GOLD_PURCHASE.getOutput(),
                marketController.buyMatcherHandler(getMatcher("buy -a 10 -i horse", BUY.toString()), player)
                , "not enough gold to buy");

        player.setGold(HORSE.getGold() * 10);
        int initialHorses = player.getInventory().get(HORSE);
        assertEquals(SUCCESSFUL_PURCHASE.getOutput(),
                marketController.buyMatcherHandler(getMatcher("buy -a 10 -i horse", BUY.toString()), player)
                , "valid buy");
        assertTrue(player.getGold() == 0);
        assertEquals(player.getInventory().get(HORSE), initialHorses + 10);
    }

    @Test
    void checkSell() {
        player.getInventory().replace(HORSE, 9);
        assertEquals(NOT_ENOUGH_RESOURCE_SELL.getOutput(),
                marketController.sellMatcherHandler(getMatcher("sell -a 10 -i horse", SELL.toString()), player)
                , "not enough gold to buy");

        player.setGold(0);
        player.getInventory().replace(HORSE, 10);
        assertEquals(SUCCESSFUL_SELL.getOutput(),
                marketController.sellMatcherHandler(getMatcher("sell -a 10 -i horse", SELL.toString()), player)
                , "valid buy");
        assertTrue(player.getGold() == 10 * HORSE.getSellPrice());
        assertEquals(player.getInventory().get(HORSE), 0);
    }

}