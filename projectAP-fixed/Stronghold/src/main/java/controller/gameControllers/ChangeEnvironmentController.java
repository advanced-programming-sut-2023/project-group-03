package controller.gameControllers;

import java.util.regex.Matcher;

public class ChangeEnvironmentController {
    GameMenuController gameMenuController;

    public ChangeEnvironmentController(GameMenuController gameMenuController) {
        this.gameMenuController = gameMenuController;
    }
    public String checkSetTextureRequirements(int x, int y) {
        return "";
    }

    public String setTexture(Matcher matcher) {
        //any kind of texture including oil and water ...
        return "";
    }

    public void clearField(Matcher matcher) {

    }

    public String checkDropRockRequirements(int x, int y) {
        //check direction and position
        return "";
    }

    public String dropRock(Matcher matcher) {
        return "";
    }

    public String checkDropTreeRequirements(int x, int y) {
        //check type of tree
        //check texture of the ground
        return "";
    }

    public String dropTree(Matcher matcher) {
        return "";
    }
}
