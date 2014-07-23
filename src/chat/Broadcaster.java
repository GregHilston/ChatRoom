package chat;

import java.util.ArrayList;

public class Broadcaster {
    private static Broadcaster broadcaster = new Broadcaster();


    /***
     * Hidden, to force calling of getInstance
     */
    private Broadcaster() {
        // Singleton
    }


    /***
     * Singleton instance getter
     *
     * @return      The one instance of the Broadcaster
     */
    public static Broadcaster getInstance() {
        return broadcaster;
    }


    /**
     * Prints the message to every other user whose chatting on the server
     *
     * @param message       Raw input from the user
     * @param userName      User's name
     */
    protected void broadcastMessage(String message, String userName) {
        String formattedMessage = userName + ": " + message;
        Logger.getInstance().log(formattedMessage);

        for(ServerThread t : ServerApp.serverThreads) {
            if(!t.getUserName().equals(userName)) {
                t.print(formattedMessage + "\n");
            }
        }
    }


    /**
     * Prints the message to every other user whose chatting in the specified channel
     *
     * @param channel       Channel user sent the message to
     * @param message       Raw input from the user
     * @param userName      User's name
     */
    protected void channelMessage(Channel channel, String message, String userName) {
        String formattedMessage = userName + ": " + message;
        Logger.getInstance().log(formattedMessage);

        for(ServerThread t : ServerApp.serverThreads) {
            if(!t.getUserName().equals(userName)) {
                t.print(formattedMessage + "\n");
            }
        }
    }


    /***
     * A message that is only intended for one user
     *
     * @param message       String intended from sender to receiver
     * @param sender        User that whispered the message
     * @param receiver      User that is intended to receive the whispered message
     * @return              "CHATTING" if the whisper was successfully sent or "[USERNAME] not currently online" for failure
     */
    protected String whisper(String message, User sender, User receiver) {
        /*
        String whisperMessage = input.substring(9); // Remove the leading "/whisper "
        String[] split = whisperMessage.split(" "); // A single space seperates the user and the message
        String userName = split[0];
        String message = split[1];

        if(!ServerInfo.getInstance().doesUserExist(userName)) {
            return userName + " does not exist";
        }

        String formattedMessage = "(whisper) " + whisperer + ": " + message;
        */

        Logger.getInstance().log(sender + " whispered: " + message + " to " + receiver);

        for(ServerThread t : ServerApp.serverThreads) {
            if(t.getUserName().equals(receiver.getUserName())) {
                t.print(sender + ": " + message + "\n");
                return "CHATTING";
            }
        }

        return receiver.getUserName() + " does not exist";
    }
}
