package model.Units.Combat;

import model.Field.Tile;
import model.GamePlay.Player;
import model.Units.Enums.AttackingMode;
import model.Units.Enums.ThrowerTypes;

public class Throwers extends CombatUnit{
    private ThrowerTypes type;
    private int cost;

    public Throwers(Player owner, Tile position, ThrowerTypes type) {
        super(owner, position);
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
    public boolean moveTo(Tile tile) {
        return super.moveTo(tile);
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
