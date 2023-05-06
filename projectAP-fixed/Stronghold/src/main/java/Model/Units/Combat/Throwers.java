package Model.Units.Combat;

import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Enums.ThrowerTypes;

public class Throwers extends CombatUnit{
    private ThrowerTypes type;
    private int cost;

    public Throwers(Player owner, Tile position, ThrowerTypes type) {
        super(owner, position);
        targets.add(type.getTarget());
        this.type = type;
        this.speed = type.getSpeed();
        this.damage = type.getDamage();
        this.HP = type.getHP();
        this.baseRange = type.getRange();
        this.modifiedRange = this.baseRange;
        this.cost = type.getGold();
        owner.decreaseGold(type.getGold());
    }

    @Override
    public void AutoMove() {
        super.AutoMove();
    }

    @Override
    public void check() {
        super.check();
        attackToEnemy();
        AutoMove();
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

    @Override
    public void print() {

    }
}
