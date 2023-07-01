package view.Network;

import Model.Field.GameMap;
import Model.Field.Tile;
import Model.GamePlay.Game;
import Model.GamePlay.Government;
import Model.GamePlay.Player;
import Model.User;
import Model.UserDatabase;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.example.MainGraphicTest;
import view.Game.Phase2Test.GameGraphic;
import view.Network.Server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;

public class GameClient extends Thread{
    Player player;
    User user;
    boolean isHost = false;

    LaunchInitializer launchInitializer;
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    private Queue<GameEvent> events;

    public GameClient(String host, int port, User user, LaunchInitializer launchInitializer) throws Exception {
        this.launchInitializer = launchInitializer;
        this.user = user;
        Socket socket = new Socket(host, port);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        start();
    }


    public GameEvent getEvent() {
        return events.remove();
    }

    public boolean hasEvent() {
        return events.size() > 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("client is waiting");
                String input = dataInputStream.readUTF();
                System.out.println("==========>"+input);
                if (input.startsWith(GameEvent.JOIN_T0_GAME.getName())) {
                    Label label = new Label();
                    String username = GameEvent.getOptions(GameEvent.JOIN_T0_GAME.getKeys(), GameEvent.makeClean(input)).get("u");
                    label.setText(launchInitializer.PlayersList.getChildren().size()+". "+username);
                    label.setFont(Font.font("cambria", FontWeight.BOLD,16));
                    Platform.runLater(() -> {
                        launchInitializer.PlayersList.getChildren().add(label);
                    });
                }
                if (input.startsWith(GameEvent.REJECT_JOIN.getName())) {
                    launchInitializer.SeverAddress.setText("there is no host yet");
                }
                if (input.startsWith(GameEvent.ANNOUNCE_LIST.getName())) {
                    String map = GameEvent.getOptions(GameEvent.ANNOUNCE_LIST.getKeys(), GameEvent.makeClean(input)).get("m");
                    for (int i = 1; i < 5; i++) {
                        String key = i + "";
                        String username = GameEvent.getOptions(GameEvent.ANNOUNCE_LIST.getKeys(), GameEvent.makeClean(input)).get(key);
                        if (username == null || username.equals("null")) {
                            continue;
                        }
                        if (i == 1) {
                            username += " (HOST)";
                        }
                        Label label = new Label();
                        label.setFont(Font.font("cambria", FontWeight.BOLD, 16));
                        if(username!=null)
                        label.setText(i + ". " + username);
                        Platform.runLater(() -> {
                            launchInitializer.gotoWaiting(launchInitializer.SeverAddress.getText());
                            launchInitializer.PlayersList.getChildren().add(label);
                            launchInitializer.MapLabel.setText(map);
                        });
                    }
                }
                if (input.startsWith(GameEvent.START_GAME.getName())) {
                    try {
                        Platform.runLater(()->{
                            GameMap gameMap = UserDatabase.getMapByName(launchInitializer.MapLabel.getText());
                            ArrayList<Player> players = new ArrayList<>();
                            for (int i = 0; i < launchInitializer.PlayersList.getChildren().size(); i++) {
                                if (i == 0) {
                                    continue;
                                }
                                Label label = (Label) launchInitializer.PlayersList.getChildren().get(i);
                                String name = label.getText();
                                String[] parts = name.split(" ");
                                name = parts[1];
                                Player player1 = new Player(UserDatabase.getUserByName(name), new Government());
                                players.add(player1);
                            }
                            Game game = new Game(gameMap, players);
                            GameGraphic gameGraphic = new GameGraphic(gameMap, gameMap.getSize(), game);
                            Tile.setGameMap(gameMap);
                            try {
                                gameGraphic.start(LaunchInitializer.stage);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                Thread.sleep(200);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public Player getPlayer() {
        return player;
    }

    public User getUser() {
        return user;
    }

    public LaunchInitializer getLaunchInitializer() {
        return launchInitializer;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }
}
