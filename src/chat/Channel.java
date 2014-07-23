package chat;

import java.util.ArrayList;

public class Channel {
    private String channelName;
    private int numOfUsers;
    private ArrayList<String> users = new ArrayList<String>();

    public Channel(String channelName) {
        this.channelName = channelName;
        this.numOfUsers = 0;
    }


    /***
     * Adds a user to this channel
     *
     * @param        userToAdd user to add
     */
    protected void addUser(String userToAdd) {
        users.add(userToAdd);
        numOfUsers++;
    }


    /***
     * Removes a user from this channel
     *
     * @param       userToRemove user to remove
     */
    protected void removeUser(String userToRemove) {
        users.remove(userToRemove);
        numOfUsers--;
    }


    /***
     * Gets the number of users currently chatting in this channel
     *
     * @return      number of users chatting in this channel
     */
    public int getNumOfUsers() {
        return numOfUsers;
    }
}
