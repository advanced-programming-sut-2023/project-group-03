package Model.Buildings;

import Model.Buildings.Enums.Resources;
import Model.Buildings.Enums.RestTypes;
import Model.Field.Tile;
import Model.GamePlay.Player;

public class Rest extends Building {
    private RestTypes type;

    public Rest(Player owner, Tile position, RestTypes type) {
        super(owner, position);
        this.length = type.getLength();
        this.width = type.getWidth();
        owner.decreaseGold(type.getGold());
        owner.decreaseInventory(Resources.WOOD,type.getWood());
    }

    @Override
    public void check() {

    }

    @Override
    public void print() {

    }
}
