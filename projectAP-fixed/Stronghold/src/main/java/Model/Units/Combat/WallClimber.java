package Model.Units.Combat;

import Model.Field.Tile;
import Model.GamePlay.Player;

public class WallClimber extends CombatUnit{
    private int cost;
    private Tile target;

    public void MakeLadder() {

    }

    @Override
    public void attackTo(Tile tile) {
        super.attackTo(tile);
    }

    public WallClimber(Player owner, Tile position) {
        super(owner, position,"wall climber");
    }

    @Override
    public void print() {

    }
}
