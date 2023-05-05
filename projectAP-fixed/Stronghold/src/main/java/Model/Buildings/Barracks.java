package Model.Buildings;

import Model.Buildings.Enums.BarracksType;
import Model.Buildings.Enums.Resources;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.Units.Combat.CombatUnit;
import Model.Units.Enums.TroopTypes;

import java.util.HashSet;

public class Barracks extends Building{

    BarracksType type;
    HashSet<TroopTypes> Products;

    public Barracks(Player owner, Tile position, BarracksType type) {
        super(owner, position, type.getSize());
        this.material = type.getMaterial();
        this.type = type;
        owner.decreaseInventory(Resources.WOOD,type.getWood());
        owner.decreaseInventory(Resources.OIL,type.getOil());
        owner.decreaseGold(type.getGold());
    }

    public HashSet<TroopTypes> getProducts() {
        return Products;
    }

    public BarracksType getType() {
        return type;
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
