package Model.Units.Combat;

import Model.Buildings.Building;
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
    protected ArrayList<Material> targets = new ArrayList<>();
    protected int damage;
    protected int baseRange;
    protected int modifiedRange;
    protected int RangeIncrease;
    protected int defenseIncrease;
    protected Tile tileToAttack = null;
    protected Drawable EnemyTarget = null;
    protected int gold;

    public CombatUnit(Player owner, Tile position,String name) {
        super(owner, position,name);
    }

    public void attackTo(Tile tile) {

    }

    public void getHit(CombatUnit enemy) {
        this.HP -= ((enemy.getDamage() - (enemy.getDamage() * this.getDefenseIncrease() / 100)));
    }

    public ArrayList<Material> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<Material> targets) {
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

    public int getRangeIncrease() {
        return RangeIncrease;
    }

    public void setRangeIncrease(int rangeIncrease) {
        RangeIncrease = rangeIncrease;
    }

    public int getDefenseIncrease() {
        return defenseIncrease;
    }

    public void setDefenseIncrease(int defenseIncrease) {
        this.defenseIncrease = defenseIncrease;
    }

    public Drawable getEnemyTarget() {
        return EnemyTarget;
    }

    public Tile getTarget() {
        return currentTarget;
    }

    public void setTarget(Tile target) {
        this.currentTarget = target;
    }


    protected void AttackToTile() {
        GameMap map = owner.getGame().getMap();
        if (tileToAttack != null) {
            if (baseRange == 0) {
                if (tileToAttack.equals(position)) {
                    Unit unit = selectRandomEnemy(position);
                    if (unit != null) {
                        unit.getHit(this);
                    } else {
                        BufferTarget = null;
                        tileToAttack = null;
                    }
                } else {
                    currentTarget = tileToAttack;
                }
            } else {
                ArrayList<Tile> area = MoveUnitController.closeTilesForMove(this.getModifiedRange(), position, map, owner);
                if (area.contains(tileToAttack)) {
                    if (this instanceof Throwers) {
                        ((Throwers) this).damageGroup(tileToAttack);
                    }
                    Unit unit = selectRandomEnemy(position);
                    if (unit != null) {
                        unit.getHit(this);
                    } else {
                        tileToAttack = null;
                    }
                } else {
                    currentTarget = tileToAttack;
                }
            }
        }
    }

    public Unit selectRandomEnemy(Tile target) {
        int number = 0;
        for (Unit unit : target.getUnits()) {
            if (!unit.getOwner().equals(owner) && unit instanceof CombatUnit) {
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
            if (!unit.getOwner().equals(owner) && unit instanceof CombatUnit) {
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
            ArrayList<Tile> area = MoveUnitController.closeTilesForAttack(modifiedRange + 1, position, map);
            boolean flag = false;
            for (int i = 0; i < area.size(); i++) {
                if (area.get(i).getBuilding() != null) {
                    if (area.get(i).getBuilding().equals(EnemyTarget)) {
                        flag = true;
                    }
                }
            }
            if (flag) {
                EnemyTarget.getHit(this);
                if (this instanceof Throwers) {
                    ((Throwers) this).damageGroup(EnemyTarget.getPosition());
                }
                if (this instanceof LadderMen) {
                    ((LadderMen) this).setLadder(position);
                    this.erase();
                }
                if (this instanceof SiegeTower) {
                    ((SiegeTower) this).makeStairs(position);
                    this.erase();
                }
            } else {
                if (EnemyTarget instanceof Building) {
                    ArrayList<Tile> path = MoveUnitController.findPathToBuilding(position, ((Building) EnemyTarget), map, owner);
                    currentTarget = path.get(path.size() - 1);
                } else {
                    currentTarget = EnemyTarget.getPosition();
                }
            }
        }
    }


    protected boolean AttackEnemyInRange() {
        if (this.getBaseRange() == 0) {
            Unit toHit = selectRandomEnemy(position);
            if (toHit == null) {
                return false;
            }
            toHit.getHit(this);
            return true;
        } else {
            GameMap map = owner.getGame().getMap();
            ArrayList<Tile> area = MoveUnitController.closeTilesForAttack(getModifiedRange(), position, map);
            for (int i = 0; i < area.size(); i++) {
                Tile targetTile = area.get(i);
                for (Unit unit : targetTile.getUnits()) {
                    if (!unit.getOwner().equals(owner) && unit instanceof CombatUnit) {
                        Unit toHit = selectRandomEnemy(targetTile);
                        if(toHit!=null){
                            toHit.getHit(this);}
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

    @Override
    public void check() {
        super.check();
        if (shouldBreak()) {
            return;
        }
        if (position == BufferTarget) {
            BufferTarget = null;
        }
        if (BufferTarget != null) {
            currentTarget = BufferTarget;
            AutoMove();
        }
        updateRange();
        if (EnemyTarget != null) {
            attackToEnemy();
            if (EnemyTarget.getHP() < 0) {
                EnemyTarget = null;
            }
            AutoMove();
            return;
        }
        if (tileToAttack != null) {
            AttackToTile();
            AutoMove();
            return;
        }
        if (!currentTarget.equals(position)) {
            AutoMove();
            return;
        }
    }

    @Override
    public void AutoMove() {
        super.AutoMove();
        if (tileToAttack != null) {
            if (tileToAttack.equals(position)) {
                tileToAttack = null;
            }
        }
    }

    public void updateRange() {
        modifiedRange = (baseRange * getRangeIncrease()) / 100 + baseRange;
    }

    public int getGold() {
        return gold;
    }


    public void setGold(int gold) {
        this.gold = gold;
    }
}
