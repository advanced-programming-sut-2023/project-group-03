package Model.Units.Combat;

import Model.Buildings.Defending.Stair;
import Model.Buildings.Defending.Enums.StairsTypes;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.graphics.MapFX;

public class LadderMen extends CombatUnit {
    private static final int price = 30;

    public LadderMen(Player owner, Tile position, MapFX mapFX) {
        super(owner, position, "laddermen", mapFX);
        this.setDamage(0);
        this.setSpeed(30);
        this.setHP(30);
        this.setBaseRange(0);
        this.setGold(5);
        this.setBaseRange(0);
        targets.add(Material.STONE);
    }

    public static int getPrice() {
        return price;
    }

    @Override
    public void check() {
        super.check();
    }

    public void setLadder(Tile target) {
        target.setLadder(new Stair(owner, target, StairsTypes.LADDER, null));//todo
    }

    @Override
    public void print() {

    }
}
