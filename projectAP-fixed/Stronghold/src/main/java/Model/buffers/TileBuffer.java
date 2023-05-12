package Model.buffers;

import Model.Buildings.Building;
import Model.Buildings.Defending.Enums.Stair;
import Model.Field.*;
import Model.GamePlay.Player;
import Model.Units.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class TileBuffer {
    private byte texture;
    private byte mazafaza;
    private int owner;

    public TileBuffer(Tile tile,GameMap map) {
        texture = tile.getTexture().getCode();
        if(tile.getMazafaza()!=null){ mazafaza = tile.getMazafaza().getCode();}

        for (int i = 0; i < map.getPlayers().length; i++) {
            if (map.getPlayers()[i].equals(tile.getOwner())) {
                owner = i;
            }
        }
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public void writeOnTile(Tile tile, GameMap map) {
        tile.setTexture(Texture.getTextureByCode(texture));
        tile.setOwner(map.getPlayers()[owner]);
        tile.setMazafaza(Model.Field.mazafaza.getMazafazaBycode(mazafaza));
    }
}
