package Model.Buildings;

import Model.Buildings.Enums.Resources;
import Model.Feild.Tile;
import Model.GamePlay.Player;

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

    }

    public void sell(Resources resources, int number) {

    }

    @Override
    public void check() {

    }

    @Override
    public void print() {

    }


}
