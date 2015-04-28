/**
 * A representation of a message sent from a client to the server
 */
public class ChatMessage {
    private User user;
    private String message;

    /**
     * @param user    the user that sent this message
     * @param message the message text itself
     */
    public ChatMessage(User user, String message) {
        this.user = user;
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
