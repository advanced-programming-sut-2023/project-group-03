package Model.Buildings.Defending;

import Model.Buildings.Building;
import Model.Buildings.Defending.Enums.TrapsTypes;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Unit;
import view.Enums.ConsoleColors;

public class Trap extends Building {

    private final TrapsTypes type;
    private final int gold;
    private final int wood;
    private final int oil;
    private final int worker;
    private final int damage;

    public Trap(Player owner, Tile position, TrapsTypes type) {
        super(owner, position, type.getSize(), type.getName());
        this.gold = type.getGold();
        this.wood = type.getWood();
        this.oil = type.getOil();
        this.worker = type.getWorker();
        this.damage = type.getDamage();
        this.type = type;
        HP = 1000;
        owner.setCurrentPopulation(owner.getMaxPopulation() + type.getWorker());
        manageCost();
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

    public void erase() {
        super.erase();
        String log = ConsoleColors.formatPrinter(owner.getFlagColor().getColor(),
                ConsoleColors.TEXT_BG_BLACK, "a trap of type <" + type.getName() + "> worked");
        System.out.println(log);
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
