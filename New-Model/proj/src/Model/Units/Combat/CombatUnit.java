package Model.Units.Combat;

import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.Units.Unit;

import java.util.HashSet;

public abstract class CombatUnit extends Unit {
    protected HashSet<Material> Targets;
    protected int damage;
    protected int Speed;
    protected int baseRange;
    protected int modifiedRange;
    protected int defenseRate;

    public CombatUnit(Player owner, Tile position) {
        super(owner, position);
    }

    public void attackTo(Tile tile) {

    }

    @Override

    public void getHit(int value){
    }


}
