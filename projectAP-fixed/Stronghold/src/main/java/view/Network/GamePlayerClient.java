package view.Network;

import Model.GamePlay.Player;
import Model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class GamePlayerClient {
    Player player;
    GameClient gameClient;
    String name;
    boolean isAlive = true;

    public GamePlayerClient(Player user, String host, int port) {
        gameClient = new GameClient(host, port);

    }
}
