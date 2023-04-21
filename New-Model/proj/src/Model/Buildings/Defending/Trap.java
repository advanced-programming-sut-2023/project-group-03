package Model.Buildings.Defending;

import Model.Buildings.Building;
import Model.Buildings.Defending.Enums.TrapsTypes;
import Model.Field.Tile;
import Model.GamePlay.Player;

public class Trap extends Building {

    private TrapsTypes type;
    private int gold;
    private int wood;
    private int oil;
    private int worker;

    public Trap(Player owner, Tile position, TrapsTypes type) {
        super(owner, position);
        this.gold = type.getGold();
        this.wood = type.getWood();
        this.oil = type.getOil();
        this.worker = type.getWorker();
    }

    @Override
    public void check() {

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
