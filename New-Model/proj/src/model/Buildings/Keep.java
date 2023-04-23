package model.Buildings;

import model.Field.Tile;
import model.GamePlay.Player;

public class Keep extends Building {
    private static Keep instance;

    private Keep(Player owner, Tile position) {
        super(owner, position);
    }

    public static void setInstance(Player owner, Tile tile) {
        Keep.instance = instance;
    }

    public static Keep getInstance() {
        return instance;
    }

    @Override
    public void check() {

    }

    @Override
    public void print() {

    }
}