package Model.Units.Combat;

import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.Units.Unit;
import controller.gameControllers.MoveUnitController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public abstract class CombatUnit extends Unit {
    protected HashSet<Material> targets = new HashSet<>();
    protected int damage;
    protected int baseRange;
    protected int modifiedRange;
    protected int defenseRate;
    protected Tile tileToAttack = null;
    protected Drawable EnemyTarget = null;

    public CombatUnit(Player owner, Tile position,String name) {
        super(owner, position,name);
    }

    public void attackTo(Tile tile) {

    }

    @Override

    public void getHit(int value){
    }

    public HashSet<Material> getTargets() {
        return targets;
    }

    public void setTargets(HashSet<Material> targets) {
        this.targets = targets;
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

    public void attackToEnemy() {
        if (EnemyTarget != null) {
            GameMap map = owner.getGame().getMap();
            ArrayList<Tile> area = MoveUnitController.closeTilesForAttack(modifiedRange, position, map);
            if (area.contains(EnemyTarget.getPosition())) {
                EnemyTarget.setHP(EnemyTarget.getHP() - damage);
            } else {
                currentTarget = EnemyTarget.getPosition();
            }
        }
    }


    protected boolean AttackEnemyInRange() {
        if (this.getBaseRange() == 0) {
            Unit toHit = selectRandomEnemy(position);
            if (toHit == null) {
                return false;
            }
            toHit.setHP(toHit.getHP() - damage);
            return true;
        } else {
            GameMap map = owner.getGame().getMap();
            ArrayList<Tile> area = MoveUnitController.closeTilesForAttack(getModifiedRange(), position, map);
            for (int i = 0; i < area.size(); i++) {
                Tile targetTile = area.get(i);
                for (Unit unit : targetTile.getUnits()) {
                    if (!unit.getOwner().equals(owner)) {
                        Unit toHit = selectRandomEnemy(targetTile);
                        if(toHit!=null){ toHit.setHP(toHit.getHP() - damage);}
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public void setEnemyTarget(Drawable enemyTarget) {
        EnemyTarget = enemyTarget;
        currentTarget = position;
        tileToAttack = null;
    }

    @Override
    public void setCurrentTarget(Tile currentTarget) {
        super.setCurrentTarget(currentTarget);
        EnemyTarget = null;
        tileToAttack = null;
    }

    public Tile getTileToAttack() {
        return tileToAttack;
    }

    public void setTileToAttack(Tile tileToAttack) {
        this.tileToAttack = tileToAttack;
        EnemyTarget = null;
        currentTarget = position;
    }
}
