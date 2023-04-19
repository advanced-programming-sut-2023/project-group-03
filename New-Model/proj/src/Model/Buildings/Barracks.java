package Model.Buildings;

import Model.Buildings.Enums.TypeOfBarracks;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.Units.Combat.CombatUnit;
import Model.Units.Enums.TroopTypes;

import java.util.HashSet;

public class Barracks extends Building{

    TypeOfBarracks type;
    HashSet<TroopTypes> Products;

    public Barracks(Player owner, Tile position, TypeOfBarracks type) {
        super(owner, position);
        this.setMaterial(Material.STONE);
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
