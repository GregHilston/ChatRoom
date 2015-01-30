import java.util.ArrayList;

/**
 * Managers all the Channels on the server.
 */

public class ChannelManager {
    private ArrayList<Channel> channels = new ArrayList<Channel>();
    private final int DEFAULTCHANNEL = 0;

    public ChannelManager(String defaultChannelName) {
        channels.add(new Channel(defaultChannelName));
    }

    /**
     * Hands off the creation of the User to the default channel
     *
     * @param user user to add
     */
    public void addUser(User user) {
        channels.get(DEFAULTCHANNEL).addUser(user);
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public Channel getDefaultChannel() {
        return channels.get(DEFAULTCHANNEL);
    }

    /**
     * A chat Channel, responsible for holding users
     */
    public class Channel {
        private String name;
        private ArrayList<User> users = new ArrayList<User>();

        public Channel(String name) {
            this.name = name;
        }

        /**
         * Add a user to this channel
         *
         * @param user the user to add to the channel
         */
        public void addUser(User user) {
            users.add(user);
        }

        public String getName() {
            return name;
        }

        public int getNumberOfUsers() {
            return users.size();
        }
    }

    public void messageAllUsers(String message) {
        Logger.writeMessage(message);

        for (Channel c : channels) {
            for (User user : c.users) {
                user.writeMessage(message);
            }
        }
    }
}
