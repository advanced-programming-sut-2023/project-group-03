package Model.Units.Combat;

import Model.Buildings.Enums.Resources;
import Model.Feild.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Player;
import Model.Units.Enums.AttackingMode;
import Model.Units.Enums.troopTypes;

import java.util.HashSet;

public class Troop extends CombatUnit{
    troopTypes type;
    AttackingMode mode;
    HashSet <Resources> equipment;
    int gold;
    Drawable currentTarget;

    public Troop(Player owner, Tile position) {
        super(owner, position);
    }

    public void attack() {

    }

    public troopTypes getType() {
        return type;
    }

    public void setType(troopTypes type) {
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
