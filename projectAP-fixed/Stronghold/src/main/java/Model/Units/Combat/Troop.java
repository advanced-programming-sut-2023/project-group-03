package Model.Units.Combat;

import Model.Buildings.Enums.Resources;
import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;
import Model.Units.Enums.AttackingMode;
import Model.Units.Enums.TroopTypes;
import Model.Units.Unit;
import controller.gameControllers.MoveUnitController;

import java.util.ArrayList;
import java.util.HashSet;

public class Troop extends CombatUnit{
    protected TroopTypes type;
    protected AttackingMode mode;
    protected HashSet <Resources> equipment;
    protected int gold;
    public Troop(Player owner, Tile position, TroopTypes type) {
        super(owner, position, type.getName());
        this.type = type;
        this.speed = type.getSpeed();
        this.damage = type.getDamage();
        this.HP = type.getHP();
        this.baseRange = type.getRange();
        this.modifiedRange = this.baseRange;
        this.gold = type.getGold();
        owner.decreaseGold(type.getGold());
        this.equipment = type.getEquipment();
        this.mode = AttackingMode.STANDING;
        
        for (Resources now:equipment) {
            owner.decreaseInventory(now,1);
        }
    }

    public void attack() {

    }

    public TroopTypes getType() {
        return type;
    }

    public void setType(TroopTypes type) {
        this.type = type;
    }

    public AttackingMode getMode() {
        return mode;
    }

    public void setMode(AttackingMode mode) {
        this.mode = mode;
    }

    public HashSet<Resources> getEquipment() {
        return equipment;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public Drawable getCurrentTarget() {
        return EnemyTarget;
    }

    public void setCurrentTarget(Drawable currentTarget) {
        this.EnemyTarget = currentTarget;
    }

    protected void defensiveModAttack() {
        GameMap map = owner.getGame().getMap();
        if (currentTarget.equals(position) && EnemyTarget.equals(null)) {
            if (AttackEnemyInRange()) {
                return;
            } else if (baseRange == 0) {
                ArrayList<Tile> area = MoveUnitController.closeTilesForAttack(15, position, map);
                for (int i = 0; i < area.size(); i++) {
                    Tile targetTile = area.get(i);
                    for (Unit unit : targetTile.getUnits()) {
                        if (!unit.getOwner().equals(owner)) {
                            currentTarget = targetTile;
                            return;
                        }
                    }
                }
                return;
            }
        } else {
            return;
        }
    }

    protected void standingModAttack() {
        if (currentTarget.equals(position) && EnemyTarget == null) {
            AttackEnemyInRange();
        } else {
            return;
        }
    }

    protected void AttackingModAttack() {
        GameMap map = owner.getGame().getMap();
        if (currentTarget.equals(position) && EnemyTarget.equals(null)) {
            if (AttackEnemyInRange()) {
                return;
            } else if (baseRange == 0) {
                ArrayList<Tile> area = MoveUnitController.closeTilesForAttack(35, position, map);
                for (int i = 0; i < area.size(); i++) {
                    Tile targetTile = area.get(i);
                    for (Unit unit : targetTile.getUnits()) {
                        if (!unit.getOwner().equals(owner)) {
                            currentTarget = targetTile;
                            return;
                        }
                    }
                }
                return;
            }
        } else {
            return;
        }
    }


    @Override
    public void check() {
        super.check();
        if (isPatrol()) {
            return;
        }
        if (EnemyTarget != null) {
            attackToEnemy();
            AutoMove();
            return;
        }
        if (!currentTarget.equals(position)) {
            AutoMove();
            return;
        }
        if (mode.equals(AttackingMode.DEFENSIVE)) {
            defensiveModAttack();
        }
        else if (mode.equals(AttackingMode.STANDING)) {
            standingModAttack();
        } else if (mode.equals(AttackingMode.AGGRESSIVE)) {
            AttackingModAttack();
        } else {
            AttackEnemyInRange();
        }
        AutoMove();
    }

    @Override
    public void print() {

    }

}
