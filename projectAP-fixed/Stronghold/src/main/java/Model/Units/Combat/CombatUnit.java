package Model.Units.Combat;

import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.Units.Unit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

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

    public Unit selectRandomEnemy(Tile target) {
        int number = 0;
        for (Unit unit : target.getUnits()) {
            if (!unit.getOwner().equals(owner)) {
                number++;
            }
        }
        if (number == 0) {
            return null;
        }
        Random random = new Random();
        int randomNumber = Math.abs(random.nextInt()) % number;
        number = 0;
        for (int i = 0; i < target.getUnits().size(); i++) {
            Unit unit = target.getUnits().get(i);
            if (!unit.getOwner().equals(owner)) {
                if (number == randomNumber) {
                    return unit;
                } else {
                    number++;
                }
            }
        }
        return null;
    }
}
