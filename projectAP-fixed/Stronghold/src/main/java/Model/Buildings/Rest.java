package Model.Buildings;

import Model.Buildings.Enums.Resources;
import Model.Buildings.Enums.RestTypes;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;

public class Rest extends Building {
    private RestTypes type;

    public Rest(Player owner, Tile position, RestTypes type) {
        super(owner, position);
        this.setGoldCost(type.getGold());
        this.setHP(50);
        this.setMaterial(Material.WOOD);
        this.set
        if (type.equals(RestTypes.HOVEL)) {

        }
        manageCost();
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
