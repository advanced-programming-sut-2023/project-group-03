package Model.Buildings.Defending;

import Model.Buildings.Building;
import Model.Buildings.Defending.Enums.TrapsTypes;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Unit;

public class Trap extends Building {

    private TrapsTypes type;
    private int gold;
    private int wood;
    private int oil;
    private int worker;
    private int damage;

    public Trap(Player owner, Tile position, TrapsTypes type) {
        super(owner, position, type.getSize());
        this.gold = type.getGold();
        this.wood = type.getWood();
        this.oil = type.getOil();
        this.worker = type.getWorker();
        this.damage = type.getDamage();
        owner.setCurrentPopulation(owner.getMaxPopulation() + type.getWorker());
    }

    @Override
    public void check() {
        for (Unit unit : position.getUnits()) {
            if (!unit.getOwner().equals(this.owner)) {
                unit.setHP(unit.getHP() - type.getDamage());
            }
        }
    }

    @Override
    public void print() {

    }

    public int getGold() {
        return gold;
    }

    public int getWood() {
        return wood;
    }

    public int getOil() {
        return oil;
    }

    public int getWorker() {
        return worker;
    }
}
