package graphicsTest;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

public class UnitMovementTemp {
    ArrayList<Image> images = new ArrayList<>();
    double targetX;
    double targetY;
    Duration duration;
    TranslateTransition moveTransition;
    Timeline timeline;
    double frac = 0;
    Rectangle shape = new Rectangle(20, 40);


    public UnitMovementTemp(double targetX, double targetY, int millisTime, Image... images) {
        this.targetX = targetX;
        this.targetY = targetY;
        duration = Duration.millis(millisTime);
        this.images.addAll(Arrays.asList(images));

        moveTransition = new TranslateTransition(duration, shape);
        moveTransition.setToX(targetX);
        moveTransition.setToY(targetY);

        timeline =  new Timeline(new KeyFrame(Duration.millis((double) millisTime / 100), this::changePicture));
        timeline.setCycleCount(100);
    }

    private void changePicture(ActionEvent actionEvent) {
        frac += duration.toMillis() / 100 * 10;
        System.out.println(frac / duration.toMillis());
        System.out.println("hello");
        int size = this.images.size();
        int imageIndex = Math.min((int) Math.floor((frac / duration.toMillis()) * size) % size, size - 1);
        shape.setFill(new ImagePattern(images.get(imageIndex)));
    }

    public void startAnimations() {
        moveTransition.play();
        timeline.play();
    }
}
