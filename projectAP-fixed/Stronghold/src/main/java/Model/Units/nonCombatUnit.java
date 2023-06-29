package Model.Units;

import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.graphics.MapFX;

public abstract class nonCombatUnit extends Unit {

    public nonCombatUnit(Player owner, Tile position, String name, MapFX mapFX) {
        super(owner, position, name, mapFX);
        speed = 1;
    }
}
