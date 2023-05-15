package Model.Buildings.Defending;

import Model.Buildings.Defending.Enums.TowerTypes;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Combat.Throwers;
import Model.Units.Combat.Troop;
import view.Enums.ConsoleColors;

public class Towers extends CastleBuilding {
    private TowerTypes type;
    private Throwers thrower;
    private int defenseIncrease;
    private int rangeIncrease;

    public Towers(Player owner, Tile position, TowerTypes type) {
        super(owner, position, type.getSize(), type.getName());
        this.type = type;
        this.HP = type.getHP();
        this.size = type.getSize();
        this.stoneCost = type.getStoneCost();
        this.defenseIncrease = type.getDefenseIncrease();
        this.rangeIncrease = type.getRangeIncrease();
    }

    public void addTroop(Troop troop) {
        troops.add(troop);
        troop.setRangeIncrease(troop.getRangeIncrease() + rangeIncrease);
        troop.setDefenseIncrease(troop.getDefenseIncrease() + defenseIncrease);
    }

    public void removeTroop(Troop troop) {
        troops.remove(troop);
        troop.setRangeIncrease(troop.getRangeIncrease() - rangeIncrease);
        troop.setDefenseIncrease(troop.getDefenseIncrease() - defenseIncrease);
    }

    public Throwers getThrower() {
        return thrower;
    }

    public void setThrower(Throwers thrower) {
        this.thrower = thrower;
    }

    public TowerTypes getType() {
        return type;
    }

    public int getDefenseIncrease() {
        return defenseIncrease;
    }

    public int getRangeIncrease() {
        return rangeIncrease;
    }

    @Override
    public void check() {
        if(shouldBreak()){
            return;
        }
    }

    public void erase() {
        super.erase();
        if(thrower!=null){ thrower.erase();}

        String log = ConsoleColors.formatPrinter(owner.getFlagColor().getColor(),
                ConsoleColors.TEXT_BG_BLACK, "a building of type <" + type.getName() + "> distroyed in (" +
                        position.getRowNum() + "," + position.getColumnNum() + ")");
        System.out.println(log);
    }

    @Override
    public void print() {

    }
}
