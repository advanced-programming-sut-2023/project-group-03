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

    }

    @Override
    public void print() {

    }
}
