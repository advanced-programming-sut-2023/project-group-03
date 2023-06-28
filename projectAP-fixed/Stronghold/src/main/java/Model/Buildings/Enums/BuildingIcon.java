package Model.Buildings.Enums;

import javafx.scene.shape.Rectangle;

public class BuildingIcon extends Rectangle {
    public BuildingIcon(double v, double v1) {
        super(v, v1);
    }

    BuildingGraphics buildingGraphics;

    public BuildingGraphics getBuildingGraphics() {
        return buildingGraphics;
    }

    public void setBuildingGraphics(BuildingGraphics buildingGraphics) {
        this.buildingGraphics = buildingGraphics;
    }
}
