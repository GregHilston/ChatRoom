import java.util.ArrayList;

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
     * Creates a formatted string of all users in this channel
     *
     * @return
     */
    public String getUserList() {
        StringBuilder stringBuilder = new StringBuilder();

        for(User u : users) {
            stringBuilder.append(u.getName() + " ");
        }

        return stringBuilder.toString();
    }

    /**
     * Sends a string to every user in this channel
     * Generally used to send Server Messages to Clients
     *
     * @param s String to be sent
     */
    public void stringToAllUsers(String s) {
        for (User u : users) {
            u.writeString(s);
        }
    }

    /**
     * Sends a string to every user in this channel besides the author
     * Generally used to send Server Messages to Clients
     *
     * @param s String to be sent
     */
    public void stringToAllOtherUsers(User author, String s) {
        for (User u : users) {
            if (!u.getName().equalsIgnoreCase(author.getName())) { // Doesn't write the message to the author
                u.writeString(s);
            }
        }
    }

    /**
     * Messages every user in this channel
     *
     * @param message message being sent
     */
    public void messageAllUsers(ChatMessage message) {
        Logger.logMessage(message);

        for (User u : users) {
            u.writeMessage(message);
        }
    }

    /**
     * Messages every user in this channel besides the author
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

    /**
     * Sends the new user all users in the channel
     *
     * @param newUser the user that just joined the channel
     */
    public void sendUsers(User newUser) {
        for(User u : users) {
            newUser.writeString("/server: connect " + u.getName());
        }
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