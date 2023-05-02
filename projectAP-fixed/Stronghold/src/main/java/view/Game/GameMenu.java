package view.Game;

import Model.GamePlay.Drawable;
import Model.GamePlay.Game;
import view.Menu;
import view.Transition;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Menu {
    Game game;
    Drawable selected;

    public GameMenu(Scanner scanner, Game game) {
        super(scanner);
        this.game = game;
    }

    @Override
    public void run() throws Transition {

    }

    // map

    private void showMap(Matcher matcher){}

    private void moveMap(Matcher matcher){}

    private void showDetails(Matcher matcher){}

    // kingdom

    private void changeFoodRate(Matcher matcher){}

    private void changeTaxRate(Matcher matcher){}

    private void changeFearRate(Matcher matcher){}

    // show rates are handled in the controller as they have no errors

    // buildings


    private void selectBuilding(Matcher matcher){}

    private void createUnit(Matcher matcher){}

    private void repair(){}

    // units and workers

    private void selectUnit(Matcher matcher){}

    // the following functions must give an error if no units are selected

    private void moveUnit(Matcher matcher){}

    private void patrolUnit(Matcher matcher){}

    private void setUnitState(Matcher matcher){
        // sets unit state (standing|defensive|offensive)
    }

    private void attack(Matcher matcher){}

    private void pourOil(Matcher matcher){}

    private void buildSiegeStructures(Matcher matcher){
        // only if selected unit is an engineer
    }

    private void disbandUnit(Matcher matcher){}

    // change environment

    private void setTexture(Matcher matcher){}

    private void setTextureRectangle(Matcher matcher){}

    private void clearField(Matcher matcher){
        // error if Field is already empty
    }

    private void dropRock(Matcher matcher){
        // error if field is not empty
    }

    private void dropTree(Matcher matcher){}

    private void dropBuilding(Matcher matcher){}

    private void dropUnit(Matcher matcher){}

    // trading

    private void doTrade(Matcher matcher){}

    private void tradeList(Matcher matcher){}

    private void tradeAccept(Matcher matcher){}

    private void tradeHistory(Matcher matcher){}

}
