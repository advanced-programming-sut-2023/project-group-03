package Model.buffers;

import Model.Field.GameMap;
import Model.Field.Texture;
import Model.Field.Tile;

public class TileBuffer {
    int rowNum;
    int columnNum;
    private final byte texture;
    private byte mazafaza;
    private int owner;

    public TileBuffer(Tile tile, GameMap map) {
        rowNum = tile.getRowNum();
        columnNum = tile.getColumnNum();
        texture = tile.getTexture().getCode();
        if (tile.getMazafaza() != null) {
            mazafaza = tile.getMazafaza().getCode();
        }

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
