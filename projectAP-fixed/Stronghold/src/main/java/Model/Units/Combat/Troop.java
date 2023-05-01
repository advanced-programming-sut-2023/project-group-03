package Model.Units.Combat;

import Model.Buildings.Enums.Resources;
import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;
import Model.Units.Enums.AttackingMode;
import Model.Units.Enums.TroopTypes;

import java.util.HashSet;

public class Troop extends CombatUnit{
    TroopTypes type;
    AttackingMode mode;
    HashSet <Resources> equipment;
    int gold;
    Drawable currentTarget;

    public Troop(Player owner, Tile position, TroopTypes type) {
        super(owner, position);
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
        return currentTarget;
    }

    public void setCurrentTarget(Drawable currentTarget) {
        this.currentTarget = currentTarget;
    }

    @Override
    public void print() {

    }
}
