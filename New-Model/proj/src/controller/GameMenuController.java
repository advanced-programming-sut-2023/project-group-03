package controller;

import java.util.regex.Matcher;

import Model1.Model.Units.Unit;

import Model1.Model.User;

public class GameMenuController extends Controller {
    private Unit selectedUnit;

    private User currentPlayer;


    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public void setCurrentPlayer(User currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void showMap(Matcher matcher) {

    }

    public void moveMap(Matcher matcher) {

    }

    public void showDetails(Matcher matcher) {

    }

    //kingdom
    public String changeFoodRate(Matcher matcher) {
        //return a proper response if there is a problem
        //change it if there is no problem
        return "";
    }

    public String changeTaxRate(Matcher matcher) {
        //return a proper response if there is a problem
        //change it if there is no problem
        return "";
    }

    public String changeFearRate(Matcher matcher) {
        //return a proper response if there is a problem
        //change it if there is no problem
        return "";
    }

    public String selectBuilding(Matcher matcher) {
        //return a proper response if there is no building
        //select if there is no problem
        return "";
    }

    public String createUnit(Matcher matcher) {
        //check if you have the requirements
        return "";
    }

    public String repair(Matcher matcher) {
        //check if you have the elements
        return "";
    }

    public boolean getUnit(int x, int y) {
        //check if there is any unit there and if there is
        return false;
    }

    public boolean isOwner(Unit unit, User user) {
         return false;
    }

    public String selectUnit(Matcher matcher) {
        //check if there is any unit there
        //check if it is ours
        return "";
    }

    public boolean checkIfImpossiblePlace(int x, int y) {
        return false;
    }

    public void findProperPath(int x, int y, int dir, Model1.Model.Units.Unit unit) {

    }

    public void moveUnit(Matcher matcher) {

    }

    public String patrolUnit(Matcher matcher) {
        //move between x1,y1 and x2,y2
        //check if the positions are valid
        return "";
    }

    public void setUnitState(Matcher matcher) {

    }

    //attack
    public String attack(Matcher matcher, Unit attacker) {
        return "";
    }

    public String archerAttack(Matcher matcher, Unit archers) {
        //check if they are in range and then attack
        return "";
    }

    public boolean canPourOil(Unit unit, int x, int y) {
        //check if unit is an engineer
        //check if it is a proper building
        return false;
    }

    public void pourOil(Matcher matcher, Unit engineer) {

    }

    public String disbandUnit(Matcher matcher) {
        return "";
    }

    //---------------------

    //change environment
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

    //trading

    public String requestTrade(Matcher matcher) {
        return "";
    }

    public String showTradeList() {
        return "";
    }

    public String acceptTrade() {
        return "";
    }

    public String showTradeHistory() {
        return "";
    }

    //market
    public String showPriceList() {
        return "";
    }

    public String checkBuyRequirements() {
        //check if market has that resource and if you have the money
        return "";
    }

    public String buyFromMarket(Matcher matcher) {
        return "";
    }

    public String checkSellRequirements() {
        //check if you have the resource and if you have that amount
        return "";
    }

    public String sellToMarket(Matcher matcher) {

        return "";
    }

    //general
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
