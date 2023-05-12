package Model.buffers;

import Model.Buildings.Building;
import Model.Buildings.Defending.Enums.Stair;
import Model.Field.*;
import Model.GamePlay.Player;
import Model.Units.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class TileBuffer {
    int rowNum;
    int columnNum;
    private byte texture;
    private byte mazafaza;
    private int owner;

    public TileBuffer(Tile tile,GameMap map) {
        rowNum=tile.getRowNum();
        columnNum = tile.getColumnNum();
        texture = tile.getTexture().getCode();
        if(tile.getMazafaza()!=null){ mazafaza = tile.getMazafaza().getCode();}

        for (int i = 0; i < map.getNumberOfPlayers(); i++) {
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

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }
}
