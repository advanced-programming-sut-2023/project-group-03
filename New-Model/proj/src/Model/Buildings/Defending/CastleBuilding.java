package Model.Buildings.Defending;

import Model.Buildings.Building;
import Model.Feild.Tile;
import Model.GamePlay.Material;
import Model.GamePlay.Player;

public abstract class CastleBuilding extends Building {
    private int stoneCost;
    public CastleBuilding(Player owner, Tile position) {
        super(owner, position);
        this.setMaterial(Material.STONE);
    }

}
