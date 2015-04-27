/**
 * A representation of a message sent from a client to the server
 */
public class ChatMessage {
    private User user;
    private String message;
    private Type type;

    /**
     * Distinguishes the different types of messages
     */
    public enum Type {
        LOGIN, MESSAGE, LOGOUT
    }

    /**
     * @param user  the user that sent this message
     * @param type  the type of message
     * @param message   the message text itself
     */
    public ChatMessage(User user, Type type, String message) {
        this.user = user;
        this.type = type;
        this.message = message;
    }

    @Override
    public String toString() {
        return user.getName() + ": " + getMessage();
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}
