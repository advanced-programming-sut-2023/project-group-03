package Model.Buildings;

import Model.Buildings.Enums.TypeOfBarracks;
import Model.Feild.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.Units.Combat.CombatUnit;
import Model.Units.Enums.troopTypes;

import java.util.HashSet;

public class Baracks extends Building{

    TypeOfBarracks type;
    HashSet<troopTypes> Products;

    public Baracks(Player owner, Tile position, TypeOfBarracks type) {
        super(owner, position);
        this.setMaterial(Material.STONE);
    }

    public HashSet<troopTypes> getProducts() {
        return Products;
    }

    public CombatUnit make(troopTypes types) {
        return null;
    }
    @Override
    public void check() {

    }

    @Override
    public void print() {

    }
}
