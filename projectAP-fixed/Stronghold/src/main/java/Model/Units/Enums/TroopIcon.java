package Model.Units.Enums;

import javafx.scene.shape.Rectangle;

public class TroopIcon extends Rectangle {
    TroopTypes troopTypes;

    public TroopIcon(double v, double v1, TroopTypes troopTypes) {
        super(v, v1);
        this.troopTypes = troopTypes;
    }

    public TroopTypes getTroopTypes() {
        return troopTypes;
    }

    public void setTroopTypes(TroopTypes troopTypes) {
        this.troopTypes = troopTypes;
    }
}
