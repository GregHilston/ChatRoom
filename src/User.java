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
    private Channel channel; // The channel this user belongs to
    private State state = State.LOGIN;

    /**
     * Distinguishes the state this client is in
     */
    public enum State {
        LOGIN, CHATTING, LOGOUT
    }

    public User(String name, Socket socket, Channel channel) {
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
     * @param message string to write
     */
    public void writeString(String message) {
        printWriter.println(message);
    }

    /**
     * Writes a message to this user's screen
     *
     * @param message message to write
     */
    public void writeMessage(ChatMessage message) {
        printWriter.println(Logger.getCurrentTimeStamp() + message);
    }

    /**
     * The client userlist needs to be cleared and rebuilt
     */
    public void refreshUserList() {
        writeString("/server: updateUserlist " + channel.getUserList());
    }

    /**
     * Alert all other users that this user connected
     */
    public void connectAlert() {
        getChannel().stringToAllOtherUsers(this, "/server: connect " + getName());
    }

    /**
     * Alert all other users that this user disconnected
     */
    public void disconnectAlert() {
        getChannel().stringToAllOtherUsers(this, "/server: disconnect " + getName());
    }

    /**
     * Alert all other users that this user joined the channel
     */
    public void joinChannelAlert() {
        getChannel().stringToAllOtherUsers(this, "/server: join " + getName());
    }

    /**
     * Alert all other users that this user left the channel
     */
    public void leftChannelAlert() {
        getChannel().stringToAllOtherUsers(this, "/server: left " + getName());
    }

    /**
     * Disconnects the user from the server
     */
    public void disconnect() {
        disconnectAlert();

        try {
            writeString("You have disconnected from the server");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
