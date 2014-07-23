package chat;

import java.util.ArrayList;

public class ChannelManager {
    private ArrayList<Channel> channels = new ArrayList<Channel>();
    private static ChannelManager channelManager = new ChannelManager();

    private ChannelManager() {
        // Singleton
    }

    public static ChannelManager getInstance() {
        return channelManager;
    }
}
