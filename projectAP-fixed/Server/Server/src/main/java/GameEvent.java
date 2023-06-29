
import java.util.ArrayList;
import java.util.Arrays;

public enum GameEvent {
    START_GAME("start game", null),
    JOIN_T0_GAME("join to game", null),
    USER_JOINED_TO_NETWORK("user joined to net work", null),
    USER_SUCCESSFULLY_CREATED("user successfully created", null),
    DROP_BUILDING("drop building", new ArrayList<>(Arrays.asList("x", "y", "t"))),
    DROP_UNIT("drop unit", new ArrayList<>(Arrays.asList("x", "y", "t", "c"))),
    ;

    GameEvent(String name, ArrayList<String> keys) {
        this.keys = keys;
        this.name = name;
    }

    private final ArrayList<String> keys;

    private void fixMessage(String... args) {
        message = new String();
        message += name;
        if (keys == null) {
            return;
        }
        for (String key : keys) {
            message += " -" + key;
        }
    }

    String name;
    String message;
    int code;

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
    public static GameEvent getEvent(String content) {
        return GameEvent.JOIN_T0_GAME;
    }
}