package graphicsTest;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

public class UnitAnimationTemp extends Transition {
    private ArrayList<Image> images = new ArrayList<>();
    private double targetX;
    private double targetY;
    public Rectangle shape = new Rectangle(20, 20);

    public UnitAnimationTemp(double targetX, double targetY, Image... images) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.setCycleDuration(Duration.millis(3000));
        this.images.addAll(Arrays.asList(images));
    }
    @Override
    protected void interpolate(double frac) {
        int size = images.size();
        int imageIndex = Math.min((int) Math.floor(frac * size), size - 1);
        System.out.println(imageIndex);
        shape.setFill(new ImagePattern(images.get(imageIndex)));
        shape.setX(shape.getX() + 1);
    }
}
