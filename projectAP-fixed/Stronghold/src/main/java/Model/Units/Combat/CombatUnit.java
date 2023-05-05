package Model.Units.Combat;

import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.Units.Unit;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class CombatUnit extends Unit {
    protected HashSet<Material> Targets;
    protected int damage;
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

    public HashSet<Material> getTargets() {
        return Targets;
    }

    public void setTargets(HashSet<Material> targets) {
        Targets = targets;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getBaseRange() {
        return baseRange;
    }

    public void setBaseRange(int baseRange) {
        this.baseRange = baseRange;
    }

    public int getModifiedRange() {
        return modifiedRange;
    }

    public void setModifiedRange(int modifiedRange) {
        this.modifiedRange = modifiedRange;
    }

    public int getDefenseRate() {
        return defenseRate;
    }

    public void setDefenseRate(int defenseRate) {
        this.defenseRate = defenseRate;
    }

    public Tile getTarget() {
        return currentTarget;
    }

    public void setTarget(Tile target) {
        this.currentTarget = target;
    }
}
