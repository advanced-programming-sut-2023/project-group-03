package model.Buildings;

import model.Buildings.Enums.Resources;
import model.Field.Tile;
import model.GamePlay.Player;

public class Store extends Building{
    private static Store instance;

    public static Store getInstance() {
        return instance;
    }

    public static void setInstance(Player owner, Tile position) {

    }

    private Store(Player owner, Tile position) {
        super(owner, position);
    }

    public void buy(Resources resources, int number) {
        owner.decreaseGold(number * resources.getGold());
        owner.increaseInventory(resources,number);
    }

    public void sell(Resources resources, int number) {
        owner.increaseGold(number * resources.getGold());
        owner.decreaseInventory(resources,number);
    }

    @Override
    public void check() {

    }

    @Override
    public void print() {

    }


}
