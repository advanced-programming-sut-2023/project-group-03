package model.Enum;

import javafx.scene.image.Image;

public enum ReactionEnum {
    SMILE(0, "smile", "smile.png"),
    LAUGH(1, "laugh", "laugh.png"),
    SHOCK(2, "shock", "shock.png"),
    LOVE(3, "love", "love.png"),
    SAD(4, "sad", "sad.png"),
    ;
    private int code;
    private String name;
    private String imageAddress;
    private Image image;

    ReactionEnum (int code, String name, String imageAddress) {
        this.name = name;
        this.imageAddress = imageAddress;
        this.image = new Image(
                ReactionEnum.class.getResource("/images/reactions/" + imageAddress).toExternalForm()
        );
    }

    public static ReactionEnum getReactionByName(String name) {
        for (ReactionEnum reactionEnum : ReactionEnum.values()) {
            if (reactionEnum.name.equals(name)) return reactionEnum;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public Image getImage() {
        return image;
    }

    public int getCode() {
        return code;
    }
}
