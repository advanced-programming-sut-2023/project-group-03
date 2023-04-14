package Model.Units;

import Model.Feild.Tile;
import Model.GamePlay.Player;

public abstract class nonCombatUnit extends Unit{

    public nonCombatUnit(Player owner, Tile position) {
        super(owner, position);
    }
}
