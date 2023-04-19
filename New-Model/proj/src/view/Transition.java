package view;

import Model.GamePlay.Game;
import Model.User;

public class Transition extends Exception{
    private Menu destMenu;
    private User user;
    private Game game;

    public Transition(Menu destMenu) {
        this.destMenu = destMenu;
    }

    public Menu getDestMenu() {
        return destMenu;
    }

    public void setDestMenu(Menu destMenu) {
        this.destMenu = destMenu;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
