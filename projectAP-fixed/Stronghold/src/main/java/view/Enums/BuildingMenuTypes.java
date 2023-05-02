package view.Enums;

import Model.Buildings.Defending.CastleBuilding;

import java.util.HashSet;

public enum BuildingMenuTypes {
    CASTLE_BUILDINGS,
    BARACKS,
    INDUSTRY_BUIDINGS,
    FARM_BUIDINGS,
    TOWN_BUILDINGS,
    WEAPON_BUILDIGS,
    FOOD_PROCESSING,
    ;

    private String showBuidings() {
        String output = "";
        if (this.equals(CASTLE_BUILDINGS)) {
            output += "1.high walls ";
            output += "2.low walls ";
            output += "3.gate\n";
            output += "4.lookout tower ";
            output += "5.perimeter tower ";
            output += "6.Defense turret\n";
            output += "7.square tower";
            output += "8.round tower ";
        }
        if (this.equals(BARACKS)) {
            
        }
        if (this.equals(INDUSTRY_BUIDINGS)) {

        }
        if (this.equals(FARM_BUIDINGS)) {

        }
        if (this.equals(TOWN_BUILDINGS)) {

        }
        if (this.equals(WEAPON_BUILDIGS)) {

        }
        if (this.equals(FOOD_PROCESSING)) {

        }
        return "all possible buildings:\n" +
                ConsoleColors.TEXT_YELLOW +
                output + ConsoleColors.TEXT_RESET;
    }

}
