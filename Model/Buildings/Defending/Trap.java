package Model.Buildings.Defending;

import Model.Buildings.Building;
import Model.Feild.Tile;
import Model.GamePlay.Player;

public class Trap extends Building {
    private TypeOfTraps type;
    private int gold;
    private int wood;
    private int oil;
    private int worker;

    public Trap(Player owner, Tile position, TypeOfTraps type) {
        super(owner, position);
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
