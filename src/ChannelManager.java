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
     * Returns the Channel object with the given channelName
     *
     * @param channelName name of channel object desired
     * @return channel if found, otherwise null
     */
    public Channel getChannel(String channelName) {
        for(Channel channel : channels) {
            if(channel.getName().equalsIgnoreCase(channelName)) {
                return channel;
            }
        }

        return null;
    }

    /**
     * Messages every user in the channel. Usually from the Server
     *
     * @param string string to send to the users in this channel
     */
    public void messageAllUsers(String string) {
        Logger.logString(string);

        for (Channel c : channels) {
            c.stringToAllUsers(string);
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
            c.messageAllUsers(message);
        }
    }

    public void addChannel(Channel channel) {
        channels.add(channel);
    }
}
