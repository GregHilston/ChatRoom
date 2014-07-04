package chat;

import java.util.ArrayList;

public class Broadcaster {
    private static Broadcaster broadcaster = new Broadcaster();
    private ArrayList<ServerThread> serverThreads = new ArrayList<ServerThread>();


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

        for(ServerThread t : serverThreads) {
            if(!t.getUserName().equals(userName)) {
                t.print(formattedMessage + "\n");
            }
        }
    }


    /**
     * Adds the thread to the list of threads, for connected clients
     *
     * @param currentThread     thread representing actively connected client
     */
    protected void addThread(ServerThread currentThread) {
        serverThreads.add(currentThread);
    }


    /**
     * Removes the thread from the list of connected users, as this client has disconnected
     *
     * @param currentThread     thread representing client who disconnected
     */
    protected void removeThread(ServerThread currentThread) {
        serverThreads.remove(currentThread);
    }


    /***
     * A message that is only intended for one user
     *
     * @param input     Contains the user the message is intended for and the message itself
     * @return      "CHATTING" if the whisper was successfully sent or "[USERNAME] not currently online" for failure
     */
    protected String whisper(String input, String whisperer) {
        String whisperMessage = input.substring(9); // Remove the leading "/whisper "
        String[] split = whisperMessage.split(" "); // A single space seperates the user and the message
        String userName = split[0];
        String message = split[1];

        if(!ServerInfo.getInstance().doesUserExist(userName)) {
            return userName + " does not exist";
        }

        String formattedMessage = "(whisper) " + whisperer + ": " + message;
        Logger.getInstance().log(formattedMessage + " to " + userName);

        for(ServerThread t : serverThreads) {
            if(t.getUserName().equals(userName)) {
                t.print(formattedMessage + "\n");
            }
        }

        return "CHATTING";
    }
}
