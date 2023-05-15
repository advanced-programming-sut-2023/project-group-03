package controller.gameControllers;

import Model.GamePlay.Game;
import Model.Units.Unit;
import Model.User;
import controller.Controller;

public class GameMenuController extends Controller {
    private Unit selectedUnit;

    private User currentPlayer;

    private final Game game;

    public GameMenuController(User currentPlayer, Game game) {
        this.currentPlayer = currentPlayer;
        this.game = game;
    }

    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public void setCurrentPlayer(User currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void removeDeadBodies() {

    }

    public void finishTurn() {
        //call all the action functions
    }

    public String finishGame() {
        //finish game
        //give each player its points
        //give each player coins ...
        return "";
    }
}
