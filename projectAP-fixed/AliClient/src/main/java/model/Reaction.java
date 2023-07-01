package model;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Enum.ReactionEnum;


public class Reaction {
    private HBox reaction = new HBox();
    private ReactionEnum reactionEnum;
    private Label counterLabel;
    private int counter;
    private HBox hBox;

    public Reaction(ReactionEnum reactionEnum, int counter, HBox hBox) {
        this.counter = counter;
        this.reactionEnum = reactionEnum;
        this.hBox = hBox;

        ImageView imageView = new ImageView(reactionEnum.getImage());
        imageView.prefWidth(30);
        imageView.prefHeight(30);

        counterLabel = new Label(Integer.toString(counter));
        counterLabel.setPrefSize(30, 30);

        reaction.getChildren().addAll(imageView, counterLabel);
    }

    public void updateReaction() {
        Platform.runLater(() -> {
            counterLabel.setText(Integer.toString(counter));
            if (counter == 0) {
                hBox.getChildren().remove(reaction);
            }
            else if (!hBox.getChildren().contains(reaction)) {
                hBox.getChildren().add(reaction);
            }
        });
    }

    //getters and setters

    public HBox getReaction() {
        return reaction;
    }

    public void setReaction(HBox reaction) {
        this.reaction = reaction;
    }

    public ReactionEnum getReactionEnum() {
        return reactionEnum;
    }

    public void setReactionEnum(ReactionEnum reactionEnum) {
        this.reactionEnum = reactionEnum;
    }

    public Label getCounterLabel() {
        return counterLabel;
    }

    public void setCounterLabel(Label counterLabel) {
        this.counterLabel = counterLabel;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public synchronized void counterUp() {
        this.counter++;
    }

    public HBox gethBox() {
        return hBox;
    }

    public void sethBox(HBox hBox) {
        this.hBox = hBox;
    }
}
