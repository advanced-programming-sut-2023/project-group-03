package model.Buildings;

import model.Buildings.Enums.BarracksType;
import model.Buildings.Enums.Resources;
import model.Field.Tile;
import model.GamePlay.Player;
import model.Units.Combat.CombatUnit;
import model.Units.Enums.TroopTypes;

import java.util.HashSet;

public class Barracks extends Building{

    BarracksType type;
    HashSet<TroopTypes> Products;

    public Barracks(Player owner, Tile position, BarracksType type) {
        super(owner, position);
        this.material = type.getMaterial();
        owner.decreaseInventory(Resources.WOOD,type.getWood());
        owner.decreaseInventory(Resources.OIL,type.getOil());
        owner.decreaseGold(type.getGold());
    }

    public HashSet<TroopTypes> getProducts() {
        return Products;
    }

    public CombatUnit make(TroopTypes types) {
        return null;
    }
    @Override
    public void check() {
        if(shouldBreak()){
            return;
        }
        // TODO should make troops be here?
    }

    @Override
    public void print() {

    }
}
