package view.Network.Server;

import view.Network.GameClient;
import view.Network.GameEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;

public class DataBase {
    ArrayList<Connection> connections = new ArrayList<>();


    public ArrayList<Connection> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<Connection> connections) {
        this.connections = connections;
    }
}
