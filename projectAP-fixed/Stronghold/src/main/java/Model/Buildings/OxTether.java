package Model.Buildings;

import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.graphics.MapFX;

public class OxTether extends Building {
    private static final int wood = 40;

    public OxTether(Player owner, Tile position, MapFX mapFX) {
        super(owner, position, 1, "ox tether", mapFX);
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
