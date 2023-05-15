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
import view.Enums.ConsoleColors;

import java.util.ArrayList;
import java.util.HashSet;

public class Troop extends CombatUnit {
    protected TroopTypes type;
    protected AttackingMode mode;
    protected HashSet<Resources> equipment;

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
        targets.add(type.getTarget());

        for (Resources now : equipment) {
            owner.decreaseInventory(now, 1);
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
        if (currentTarget.equals(position) && EnemyTarget == null) {
            if (AttackEnemyInRange()) {
            } else if (baseRange == 0) {
                ArrayList<Tile> area = MoveUnitController.closeTilesForMove(15, position, map, owner);
                for (int i = 0; i < area.size(); i++) {
                    Tile targetTile = area.get(i);
                    for (Unit unit : targetTile.getUnits()) {
                        if (!unit.getOwner().equals(owner) && unit instanceof CombatUnit) {
                            makeDate(unit);
                            return;
                        }
                    }
                }
            }
        } else {
        }
    }


    protected void standingModAttack() {
        if (currentTarget.equals(position) && EnemyTarget == null) {
            AttackEnemyInRange();
        } else {
        }
    }

    protected void AttackingModAttack() {
        GameMap map = owner.getGame().getMap();
        if (currentTarget.equals(position) && EnemyTarget == null) {
            if (AttackEnemyInRange()) {
            } else if (baseRange == 0) {
                ArrayList<Tile> area = MoveUnitController.closeTilesForMove(35, position, map, owner);
                for (int i = 0; i < area.size(); i++) {
                    Tile targetTile = area.get(i);
                    for (Unit unit : targetTile.getUnits()) {
                        if (!unit.getOwner().equals(owner) && unit instanceof CombatUnit) {
                            makeDate(unit);
                            return;
                        }
                    }
                }
            }
        } else {
        }
    }


    @Override
    public void check() {
        super.check();
        if (isPatrol()) {
            return;
        }
        if (shouldBreak()) {
            ConsoleColors.colorPrint(owner.getFlagColor().getColor(), ConsoleColors.TEXT_BG_BLACK,
                    ">>>a " + (type.getName()) + " of " + owner.getUser().getNickname() + " died in (" + (position.getRowNum() + 1) + "," + (position.getColumnNum() + 1) + ")");
            return;
        }
        if (EnemyTarget != null) {
            return;
        }
        if (tileToAttack != null) {
            return;
        }
        if (!currentTarget.equals(position)) {
            return;
        }
        if (BufferTarget != null) {
            return;
        }
        if (mode.equals(AttackingMode.DEFENSIVE)) {
            defensiveModAttack();
        } else if (mode.equals(AttackingMode.STANDING)) {
            standingModAttack();
        } else if (mode.equals(AttackingMode.AGGRESSIVE)) {
            AttackingModAttack();
        } else {
            AttackEnemyInRange();
        }
        AutoMove();
    }

    public void makeDate(Unit unit) {
        if (!(unit instanceof Troop)) {
            currentTarget = unit.getPosition();
            return;
        }
        Troop troop = ((Troop) unit);
        if (troop.mode.equals(AttackingMode.STANDING)) {
            currentTarget = troop.getPosition();
            return;
        }
        if (troop.isPatrol()) {
            currentTarget = troop.getPosition();
            return;
        }
        if (troop.getBaseRange() > 0) {
            currentTarget = troop.getPosition();
            return;
        }
        GameMap map = owner.getGame().getMap();
        ArrayList<Tile> area = MoveUnitController.closeTilesForMove(35, troop.getPosition(), map, troop.getOwner());
        //ArrayList<Tile> areaSecond = MoveUnitController.manhattanCloseTiles(35, troop.getPosition(), map, troop.getOwner());
        if (!area.contains(this.position)) {
            currentTarget = troop.getPosition();
            return;
        }
        ArrayList<Tile> newPath = MoveUnitController.findPath(position, unit.getPosition(), map, owner);
        currentTarget = newPath.get(newPath.size() / 2);
        troop.setCurrentTarget(newPath.get(newPath.size() / 2));
    }

    @Override
    public void print() {

    }

}
