package Model.Buildings.Defending;

import Model.Buildings.Defending.Enums.TrapsTypes;
import Model.Field.Tile;
import Model.GamePlay.Player;

public class CagedWarDogs extends Trap{
    public CagedWarDogs(Player owner, Tile position) {
        super(owner, position,TrapsTypes.CAGED_WAR_DOGS);
    }

    public void check() {

    }

    public void print() {

    }
}
