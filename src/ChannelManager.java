import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

/***
 * Managers all the Channels on the server.
 */

public class ChannelManager {
    private ArrayList<Channel> channels = new ArrayList<Channel>();
    private final int DEFAULTCHANNEL = 0;
    private String defaultChannelName;


    public ChannelManager(String defaultChannelName) {
        this.defaultChannelName = defaultChannelName;
        channels.add(new Channel(defaultChannelName));
    }


    /***
     * Hands off the creation of the User to the default channel
     *
     * @param userSocket    Socket for this User
     * @param ipAddress     IP address for this user
     */
    public void addUser(String name, Socket userSocket) {
        channels.get(DEFAULTCHANNEL).addUser(name, userSocket);
    }


    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public String getDefaultChannelName() {
        return defaultChannelName;
    }


    /***
     * A chat Channel, responsible for holding users
     */
    public class Channel {
        private String channelName;
        private ArrayList<User> users = new ArrayList<User>();

        public Channel(String channelName) {
            this.channelName = channelName;
        }


        public void addUser(String name, Socket userSocket) {
            users.add(new User(name, userSocket));
        }


        public String getChannelName() {
            return channelName;
        }
    }


    public void messageAllUsers(String message) {
        for(Channel c : channels) {
            for(User user : c.users) {
                user.writeMessage(message);
            }
        }
    }
}
