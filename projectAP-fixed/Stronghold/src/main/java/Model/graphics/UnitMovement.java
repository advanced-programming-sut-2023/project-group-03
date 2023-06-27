package Model.graphics;

import Model.Field.Tile;
import Model.graphics.MapFX;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

public class UnitMovement {
    ArrayList<Image> images = new ArrayList<>();
    int durationTime;
    TranslateTransition[] moveTransitions;
    Timeline timeline;
    int timelineCycleCount = 100;
    double frac = 0;
    Rectangle shape;
    double[][][] tileCenters;



    public UnitMovement(ArrayList<Tile> tiles , int wholeDurationMillis, Rectangle shape, ArrayList<Image> images, MapFX map) {
        tileCenters = map.getTilesCenters();
        this.shape = shape;
        durationTime = wholeDurationMillis;
        this.images = images;

        //move transitions
        moveTransitions = new TranslateTransition[tiles.size()];
        for (int i = 0; i < tiles.size(); i++) {
            double targetX = tileCenters[tiles.get(i).getRowNum()][tiles.get(i).getColumnNum()][0];
            double targetY = tileCenters[tiles.get(i).getRowNum()][tiles.get(i).getColumnNum()][1];
            moveTransitions[i] = new TranslateTransition(Duration.millis((double) wholeDurationMillis / tiles.size()), shape);
            moveTransitions[i].setToX(targetX);
            moveTransitions[i].setToY(targetY);
            if (i > 0) {
                int finalI = i;
                moveTransitions[i - 1].setOnFinished(event -> moveTransitions[finalI].play());
            }
        }

        //timeline
        timeline = new Timeline(new KeyFrame(Duration.millis((double) wholeDurationMillis / timelineCycleCount), e -> {
            frac += (double) durationTime / timelineCycleCount * 10; //we will have 10 times of whole animation.
            int size = images.size();
            int imageIndex = (int) Math.floor((frac / wholeDurationMillis) * size) % size;
            shape.setFill(new ImagePattern(images.get(imageIndex)));
        }));
    }

    public void startUnitAnimation() {
        moveTransitions[0].play();
        timeline.play();
    }
}
