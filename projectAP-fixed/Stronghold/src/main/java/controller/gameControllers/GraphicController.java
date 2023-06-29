package controller.gameControllers;

import java.util.ArrayList;

import Model.Buildings.Enums.Resources;
import Model.User;
import Model.UserDatabase;
import Model.Field.GameMap;
import Model.GamePlay.Drawable;
import Model.GamePlay.Game;
import Model.GamePlay.Player;
import Model.buffers.MapBuffer;
import Model.graphics.MapFX;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.Game.Phase2Test.GameGraphic;

public class GraphicController {
    public static void setupGamePlayers(String gameMapName, ArrayList<String> playersNames, User user, Stage stage) {
        //get game map
        if (UserDatabase.getMapByName(gameMapName) == null) return;
        GameMap gameMap = UserDatabase.getMapByName(gameMapName);

        //check if a player's name is not in database
        for (String playerName : playersNames) {
            if (UserDatabase.getUserByName(playerName) == null) return;
        }

        Game game = new Game(gameMap, new ArrayList<>());

        //set up users
        for (int i = 0; i < playersNames.size(); i++) {
            gameMap.getPlayers()[i].setUser(UserDatabase.getUserByName(playersNames.get(i)));
            game.getPlayers().add(gameMap.getPlayers()[i]);
        }

        //adding player's keeps to drawable arrayList
        Drawable.setDrawables(new ArrayList<>(game.getMap().getDrawables()));
        for (int i = 0; i < game.getPlayers().size(); i++) {
            Drawable.getDrawables().add(game.getPlayers().get(i).getKeep());
        }

        game.setCurrentPlayer(game.getPlayers().get(0));

        int amount = 3500;

        //set up resources
        for (Player player : game.getPlayers()) {
            for (Resources resources : player.getInventory().keySet()) {
                player.getInventory().replace(resources, amount);
                player.setGold(100000);
                player.setPopularity(100);
                player.setCurrentPopulation(0);
                player.setMaxPopulation(16);
            }
        }

        //start game graphic
        Platform.runLater(() -> {
            GameGraphic gameGraphic = new GameGraphic(gameMap, gameMap.getSize(), game);
            try {
                gameGraphic.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }
}
