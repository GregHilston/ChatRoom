import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

/***
 * Object representation of the client on our ChatRoom
 */

public class User {
    private String name;
    private Socket userSocket = new Socket();
    private PrintWriter printWriter;


    public User(String name, Socket userSocket) {
        this.name = name;
        this.userSocket = userSocket;

        try {
            printWriter =  new PrintWriter(userSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeMessage(String message) {
        printWriter.println(message);
    }
}
