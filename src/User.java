import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Object representation of the client on our ChatRoom
 */

public class User {
    private String name;
    private String ipAndPort;
    private DataOutputStream printWriter;


    public User(String name, Socket userSocket) {
        this.name = name;
        this.ipAndPort = userSocket.getInetAddress() + ":" + userSocket.getPort();

        try {
            printWriter = new DataOutputStream(userSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Writes a message to this user's screen
     *
     * @param message message to write
     */
    public void writeMessage(String message) {
        try {
            printWriter.writeUTF(Logger.getTimeStamp() + message);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO handle shit
        }
    }


    public String getName() {
        return name;
    }


    public String getIpAndPort() {
        return ipAndPort;
    }
}
