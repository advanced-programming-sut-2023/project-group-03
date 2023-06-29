package Model.Buildings.Enums;

import javafx.scene.shape.Rectangle;

public class ResourceIcon extends Rectangle {
    Resources resources;

    public ResourceIcon(double v, double v1, Resources resources) {
        super(v, v1);
        this.resources = resources;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }
}
