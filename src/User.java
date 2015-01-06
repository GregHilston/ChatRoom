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
    private String ipAndPort;
    private PrintWriter printWriter;


    public User(String name, Socket userSocket) {
        this.name = name;
        this.userSocket = userSocket;
        this.ipAndPort = userSocket.getInetAddress() + ":" + userSocket.getPort();

        try {
            printWriter =  new PrintWriter(userSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /***
     * Writes a message to this user's screen
     *
     * @param message   message to write
     */
    public void writeMessage(String message) {
        printWriter.println(message);
    }


    public String getName() {
        return name;
    }


    public String getIpAndPort() {
        return ipAndPort;
    }
}
