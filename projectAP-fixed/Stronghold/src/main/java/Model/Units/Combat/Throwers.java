package Model.Units.Combat;

import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.Units.Enums.ThrowerTypes;
import Model.Units.Unit;
import controller.gameControllers.MoveUnitController;

import java.util.ArrayList;

public class Throwers extends CombatUnit{
    private ThrowerTypes type;
    private int cost;
    private int power;

    public Throwers(Player owner, Tile position, ThrowerTypes type) {
        super(owner, position,type.getName());
        targets.add(type.getTarget());
        this.type = type;
        this.speed = type.getSpeed();
        this.damage = type.getDamage();
        this.HP = type.getHP();
        this.baseRange = type.getRange();
        this.modifiedRange = this.baseRange;
        this.cost = type.getGold();
        targets.add(type.getTarget());
        this.power = type.getPower();
        owner.decreaseGold(type.getGold());
    }

    @Override
    public void AutoMove() {
        super.AutoMove();
    }

    @Override
    public void check() {
        super.check();
    }

    @Override
    public void attackTo(Tile tile) {
        super.attackTo(tile);
    }

    public ThrowerTypes getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }


    public void damageGroup(Tile target) {
        ArrayList<Tile> area = MoveUnitController.closeTilesForAttack(power, target, owner.getGame().getMap());
        for (Tile tile : area) {
            for (Unit unit : tile.getUnits()) {
                if (unit.getOwner() != this.owner && unit instanceof CombatUnit) {
                    unit.setHP(unit.getHP() - damage / 10);
                }
            }
            if (tile.getBuilding() != null && tile.getBuilding().getMaterial().getValue() <= Material.STONE.getValue()) {
                tile.getBuilding().getHit(this);
            }
        }
    }

    @Override
    public void print() {

    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
