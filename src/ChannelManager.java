import java.util.ArrayList;

/**
 * Managers all the Channels on the server.
 */
public class ChannelManager {
    private ArrayList<Channel> channels = new ArrayList<>();
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

    public Channel getDefaultChannel() {
        return channels.get(DEFAULTCHANNEL);
    }

    /**
     * A chat Channel responsible for holding users
     */
    public class Channel {
        private String name;
        private String description; // TODO: implement (print when user joins a channel)
        private ArrayList<User> users = new ArrayList<>(); // Users currently in this channel

        public Channel(String name) {
            this.name = name;
        }

        /**
         * Messages every user besides the author
         *
         * @param message message being sent
         */
        public void messageAllOtherUsers(ChatMessage message) {
            Logger.logMessage(message);

            for (User u : users) {
                if (!u.getName().equalsIgnoreCase(message.getUser().getName())) { // Doesn't write the message to the author
                    u.writeMessage(message);
                }
            }
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

        public String getUserNames() {
            String userList = "Users currently in channel:";

            for(User u : users) {
                userList += "\n\t" + u.getName() + "";
            }

            return userList;
        }

        public void removeUser(User u) {
            users.remove(u);
        }
    }

    /**
     * Messages every user in the channel. Usually from the Server
     *
     * @param string string to send to the users in this channel
     */
    public void messageAllUsers(String string) {
        Logger.logString(string);

        for (Channel c : channels) {
            for (User user : c.users) {
                user.writeString(string);
            }
        }
    }

    /**
     * Messages every user in the channel
     *
     * @param message message to send to the users in this channel
     */
    public void messageAllUsers(ChatMessage message) {
        Logger.logMessage(message);

        for (Channel c : channels) {
            for (User user : c.users) {
                user.writeMessage(message);
            }
        }
    }
}
