import java.net.Socket;
import java.net.SocketAddress;

/***
 * Object representation of the client on our ChatRoom
 */

public class User {
    private String name;
    private SocketAddress ipAddress;
    private Socket userSocket = new Socket();

    public User(Socket userSocket, SocketAddress ipAddress) {
        this.userSocket = userSocket;
        this.ipAddress = ipAddress;
    }
}
