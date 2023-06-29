package view.Network;

public class ClientPlayer {
    public String username;
    public Client client;
    public boolean isPlayerAlive = true;

    ClientPlayer(String username, String serverIP) {
        this.username = username;
        client = new Client(serverIP);
        client.sendJoinRequest(username);

        Runnable gameEventHandleRunnable = () -> {
            while (true) {
                if (client.hasNewEvent()) {
                    analyseGameEvent(client.getEvent());
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }
        };
        new Thread(gameEventHandleRunnable).start();
    }

    private void analyseGameEvent(GameEvent gameEvent) {
        if (gameEvent.equals(GameEvent.JOIN_T0_GAME)) {

        }
        if (gameEvent.equals(GameEvent.DROP_UNIT)) {

        }
        if (gameEvent.equals(GameEvent.DROP_BUILDING)) {

        }
        if (gameEvent.equals(GameEvent.USER_JOINED_TO_NETWORK)) {

        }
    }
}
