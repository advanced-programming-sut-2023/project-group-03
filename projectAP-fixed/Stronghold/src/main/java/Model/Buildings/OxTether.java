package Model.Buildings;

import Model.Field.Tile;
import Model.GamePlay.Player;

public class OxTether extends Building {
    private static final int wood = 40;

    public OxTether(Player owner, Tile position) {
        super(owner, position, 1, "ox tether");
        HP = 150;
        woodCost = 40;
        size = 1;
    }

    public static int getWood() {
        return wood;
    }

    @Override
    public void check() {
        if (HP <= 0) {
            this.erase();
        }
    }

    @Override
    public void print() {

    }
}
