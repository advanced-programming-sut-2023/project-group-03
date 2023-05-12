package Model.Units.Combat;

import Model.Buildings.Defending.Enums.Stair;
import Model.Buildings.Defending.Enums.StairsTypes;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;

public class LadderMen extends CombatUnit {

    public LadderMen(Player owner, Tile position) {
        super(owner, position, "laddermen");
        this.setDamage(0);
        this.setMaterial(Material.FLESH);
        this.setSpeed(30);
        this.setHP(30);
        this.setBaseRange(0);
        this.setGold(5);
    }

    @Override
    public void check() {
        if (EnemyTarget == null) {
            return;
        }
    }

    public void setLadder(Tile target) {
        target.setLadder(new Stair(owner,target, StairsTypes.LADDER));
        return;
    }

    @Override
    public void print() {

    }
}
