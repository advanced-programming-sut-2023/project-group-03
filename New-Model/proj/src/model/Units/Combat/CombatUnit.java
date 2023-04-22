package model.Units.Combat;

import model.Field.Tile;
import model.GamePlay.Material;
import model.GamePlay.Player;
import model.Units.Unit;

import java.util.HashSet;

public abstract class CombatUnit extends Unit {
    protected HashSet<Material> Targets;
    protected int damage;
    protected int speed;
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
