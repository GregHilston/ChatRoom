import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

/***
 * Managers all the Channels on the server.
 */

public class ChannelManager {
    private ArrayList<Channel> channels = new ArrayList<Channel>();
    private final int DEFAULTCHANNEL = 0;

    public ChannelManager(String defaultChannelName) {
        channels.add(new Channel(defaultChannelName));
    }


    /***
     * Hands off the creation of the User to the default channel
     *
     * @param userSocket    Socket for this User
     * @param ipAddress     IP address for this user
     */
    public void addUser(Socket userSocket, SocketAddress ipAddress) {
        channels.get(DEFAULTCHANNEL).addUser(userSocket, ipAddress);
    }


    public ArrayList<Channel> getChannels() {
        return channels;
    }


    /***
     * A chat Channel, responsible for holding users
     */
    private class Channel {
        private String channelName;
        private ArrayList<User> users = new ArrayList<User>();

        public Channel(String channelName) {
            this.channelName = channelName;
        }

        private void addUser(Socket userSocket, SocketAddress ipAddress) {
            users.add(new User(userSocket, ipAddress));
        }
    }
}
