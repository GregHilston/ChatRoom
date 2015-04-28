import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Object representation of the client on our ChatRoom
 */
public class User {
    private String name;
    private Socket socket;
    private PrintWriter printWriter;
    private ChannelManager.Channel channel; // The channel this user belongs to
    private State state = State.LOGIN;

    /**
     * Distinguishes the state this client is in
     */
    public enum State {
        LOGIN, CHATTING, LOGOUT
    }

    public User(String name, Socket socket, ChannelManager.Channel channel) {
        this.name = name;
        this.socket = socket;
        this.channel = channel;

        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a string to this user's screen. Usually from the Server
     *
     * @param message message to write
     */
    public void writeMessage(String message) {
        printWriter.println(Logger.getCurrentTimeStamp() + message);
    }

    /**
     * Writes a message to this user's screen
     *
     * @param message message to write
     */
    public void writeMessage(ChatMessage message) {
        printWriter.println(Logger.getCurrentTimeStamp() + message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InetAddress getIp() {
        return this.socket.getInetAddress();
    }

    public int getPort() {
        return this.socket.getPort();
    }

    public ChannelManager.Channel getChannel() {
        return channel;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
