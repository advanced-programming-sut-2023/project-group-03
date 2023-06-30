package view.Game;

import Model.Field.Tile;
import Model.GamePlay.Drawable;
import Model.GamePlay.Game;
import Model.GamePlay.Player;
import Model.Units.Unit;
import Model.graphics.MapFX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GraphicalGameMenu extends GameMenu {
    public GraphicalGameMenu(Game game, MapFX mapFX) {
        super( game, mapFX);
    }
}
