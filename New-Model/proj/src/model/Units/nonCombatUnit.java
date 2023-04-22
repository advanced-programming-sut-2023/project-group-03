package model.Units;

import model.Field.Tile;
import model.GamePlay.Player;

public abstract class nonCombatUnit extends Unit{

    public nonCombatUnit(Player owner, Tile position) {
        super(owner, position);
    }
}
