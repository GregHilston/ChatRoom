package chat;

import java.util.ArrayList;

public class Broadcaster {
    private static Broadcaster broadcaster = new Broadcaster();
    private ArrayList<ServerThread> serverThreads = new ArrayList<ServerThread>();

    private Broadcaster() {
        // Singleton
    }

    public static Broadcaster getInstance() {
        return broadcaster;
    }


    /**
     * Prints the message to every other user whose chatting on the server
     *
     * @param message   Raw input from the user
     * @param userName  User's name
     */
    public void broadcastMessage(String message, String userName) {
        String formattedMessage = userName + ": " + message;
        Logger.getInstance().log(formattedMessage);

        for(ServerThread t : serverThreads) {
            if(!t.getUserName().equals(userName)) {
                t.print(formattedMessage + "\n");
            }
        }
    }


    /**
     *
     * @param currentThread
     */
    public void addThread(ServerThread currentThread) {
        serverThreads.add(currentThread);
    }


    /**
     *
     * @param currentThread
     */
    public void removeThread(ServerThread currentThread) {
        serverThreads.remove(currentThread);
    }

}
