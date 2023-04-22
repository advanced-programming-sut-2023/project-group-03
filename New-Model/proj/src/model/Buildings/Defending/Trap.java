package model.Buildings.Defending;

import model.Buildings.Building;
import model.Buildings.Defending.Enums.TrapsTypes;
import model.Field.Tile;
import model.GamePlay.Player;

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
        if(shouldBreak()){
            return;
        }
        //TODO check damage for soldier inside
        // format UseTrap() where if trap is used it is taken from the building
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
