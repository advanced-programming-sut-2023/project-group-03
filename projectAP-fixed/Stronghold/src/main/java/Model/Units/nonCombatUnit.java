package Model.Units;

import Model.Field.Tile;
import Model.GamePlay.Player;

public abstract class nonCombatUnit extends Unit{

    public nonCombatUnit(Player owner, Tile position,String name) {
        super(owner, position,name);
        speed = 1;
    }
}
