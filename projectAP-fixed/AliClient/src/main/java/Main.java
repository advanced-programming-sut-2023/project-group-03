import model.client.Client;
import model.user.User;

import java.io.IOException;

public class Main {
//    public static void main(String[] args) {
//        Message message = new Message("ali", "12:40", "salam");
//        String data = message.convertMessageToJson();
//        System.out.println(data);
//        Message secondMessage = Message.convertJsonToMessage(data);
//        System.out.println(secondMessage.getContent());

//    }
    static User user;
    private static int port = 8080;
    public static void main(String[] args) throws IOException {
        new Client(user, "localhost", port, args);
    }
}
