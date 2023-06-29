package Model.Buildings.Defending;

import Model.Buildings.Building;
import Model.Field.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;
import Model.graphics.MapFX;

import java.util.ArrayList;

public abstract class CastleBuilding extends Building {
    protected ArrayList<Troop> troops;

    public CastleBuilding(Player owner, Tile position, int size, String name, MapFX mapFX) {
        super(owner, position, size, name, mapFX);
        this.setMaterial(Material.STONE);
        this.troops = new ArrayList<>();
    }

    public void setStoneCost(int stoneCost) {
        this.stoneCost = stoneCost;
    }

    @Override
    public void erase() {
        super.erase();
        for (int i = 0; i < troops.size(); i++) {
            troops.get(i).erase();
        }
    }

    public int getStoneCost() {
        return stoneCost;
    }

    public void addTroop(Troop troop) {
        troops.add(troop);
    }

    public void removeTroop(Troop troop) {
        troops.remove(troop);
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }
}
