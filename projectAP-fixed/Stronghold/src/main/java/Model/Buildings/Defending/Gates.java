package Model.Buildings.Defending;

import Model.Buildings.Defending.Enums.GateTypes;
import Model.Field.Direction;
import Model.Field.GameMap;
import Model.Field.Height;
import Model.Field.Tile;
import Model.GamePlay.Player;
import Model.Units.Combat.Troop;
import Model.Units.Unit;
import view.Enums.ConsoleColors;

import java.util.ArrayList;

public class Gates extends CastleBuilding {
    private GateTypes type;
    private boolean isOpen;
    private Direction direction;
    private ArrayList<Tile> terminals = new ArrayList<>();

    public Gates(Player owner, Tile position, GateTypes type,Tile upTerminal,Tile downTerminal) {
        super(owner, position, type.getSize(), type.getName());
        this.HP = type.getHP();
        this.stoneCost = type.getStoneCost();
        this.goldCost = 5;
        this.type = type;
        manageCost();
        terminals.add(upTerminal);
        terminals.add(downTerminal);
        downTerminal.setBuilding(this);
        upTerminal.setBuilding(this);
        downTerminal.setHeight(Height.GROUND);
        upTerminal.setHeight(Height.GROUND);
        for (Tile terminal : terminals) {
            for (Direction direction : terminal.getNeighbours().keySet()) {
                Tile current = terminal.getNeighbours().get(direction);
                if (current.getBuilding() == null || !current.getBuilding().equals(this)) {
                    terminal.getNeighboursConnected().remove(current);
                    current.getNeighboursConnected().remove(terminal);
                    if (Math.abs(terminal.getHeight().getValue() + terminal.modifiedLadder()
                            - current.modifiedLadder() - current.getHeight().getValue()) <= 1) {
                        terminal.getNeighboursConnected().add(current);
                        current.getNeighboursConnected().add(terminal);
                    }
                }
            }
        }
    }

    private void updateOwner() {
        for (Troop now: troops) {
            if(now.getOwner().equals(owner)){
                return;
            }
        }
        if(troops.size()==0){
            return;
        }
        owner = troops.get(0).getOwner();
    }

    private void updateCloseOrOpen() {
        for (Tile terminal : terminals) {
            for (Unit unit : terminal.getUnits()) {
                if (unit.getOwner().equals(owner)) {
                    this.isOpen = true;
                }
            }
        }
    }

    public GateTypes getType() {
        return type;
    }

    public void setType(GateTypes type) {
        this.type = type;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void Open() {
        this.isOpen = true;
    }

    public void Close() {
        this.isOpen = false;
    }

    @Override
    public void check() {
        if(shouldBreak()){
            return;
        }
        updateOwner();
        updateCloseOrOpen();
    }

    public void setTerminals(ArrayList<Tile> terminals) {
        this.terminals = terminals;
    }

    public void addTerminal(Tile tile) {
        terminals.add(tile);
    }

    public ArrayList<Tile> getTerminals() {
        return terminals;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void erase() {
        super.erase();
        String log = ConsoleColors.formatPrinter(owner.getFlagColor().getColor(),
                ConsoleColors.TEXT_BG_BLACK, "a building of type <" + type.getName() + "> distroyed in (" +
                        position.getRowNum() + "," + position.getColumnNum() + ")");
        System.out.println(log);
    }

    public void updateTilesOfGate() {
        int x = position.getRowNum();
        int y = position.getColumnNum();
        GameMap map = owner.getGame().getMap();
        for (int i = x - size / 2; i <= x + size / 2; i++) {
            for (int j = y - size / 2; j <= y + size / 2; j++) {
            Tile current = map.getMap()[i][j];
                Gates gates = ((Gates) current.getBuilding());
                if (this.getTerminals().contains(current)) {

                }
            }
        }

    }

    @Override
    public void print() {

    }

}
