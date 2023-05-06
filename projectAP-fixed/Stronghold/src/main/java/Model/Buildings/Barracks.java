package Model.Buildings;

import Model.Buildings.Enums.BarracksType;
import Model.Buildings.Enums.Resources;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.Units.Combat.CombatUnit;
import Model.Units.Engineer;
import Model.Units.Enums.TroopTypes;

import java.util.Arrays;
import java.util.HashSet;

import static Model.Units.Enums.TroopTypes.*;

public class Barracks extends Building{

    BarracksType type;
    HashSet<TroopTypes> Products = new HashSet<>();

    public Barracks(Player owner, Tile position, BarracksType type) {
        super(owner, position, type.getSize());
        this.material = type.getMaterial();
        this.type = type;
        owner.decreaseInventory(Resources.WOOD,type.getWood());
        owner.decreaseInventory(Resources.OIL,type.getOil());
        owner.decreaseGold(type.getGold());
        owner.getKeep().getBarracks().put(type, this);
        if (type.equals(BarracksType.BARRACK))
            Products = new HashSet<>(
                    Arrays.asList(SWORDMEN));
        else if (type.equals(BarracksType.ENGINEER_GUILD))
            Products = new HashSet<>(
                    Arrays.asList(LADDERMEN));
        else if (type.equals(BarracksType.MERCENARY_POST))
            Products = new HashSet<>(
                    Arrays.asList());
        else if (type.equals(BarracksType.TUNNELER_GUILD))
            Products = new HashSet<>(
                    Arrays.asList(TUNELLER));
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
        // TODO should make troops be here? //from mehran: NO
    }

    @Override
    public void print() {

    }
}
