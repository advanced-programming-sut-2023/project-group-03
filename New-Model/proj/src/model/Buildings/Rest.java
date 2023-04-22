package model.Buildings;

import model.Buildings.Enums.Resources;
import model.Buildings.Enums.RestTypes;
import model.Field.Tile;
import model.GamePlay.Player;

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
        if(shouldBreak()){
            return;
        }
    }

    @Override
    public void print() {

    }
}
