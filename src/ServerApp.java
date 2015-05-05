import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Enumeration;
import java.util.HashSet;

/**
 * The Chat server that users join channels to speak to one another
 */
public class ServerApp {
    private static HashSet<User> users = new HashSet<>(); // Users currently on the server
    private ServerSocket serverSocket;
    private int portNumber;
    private ChannelManager channelManager = new ChannelManager("#lobby");

    public ServerApp(int portNumber) {
        this.portNumber = portNumber;
    }

    /**
     * Checks if an entered username is unique on this server
     *
     * @param name name to check
     * @return if unique username
     */
    private static boolean uniqueUsername(String name) {
        for (User u : users) {
            if (u.getName().toLowerCase().equals(name.toLowerCase())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if an entered username is composed of only alphanumeric characters
     *
     * @param name name to check
     * @return if name contains only alphanumeric characters
     */
    private static boolean alphaNumericOnly(String name) {
        return name.matches("^.*[^a-zA-Z0-9].*$"); // Regex
    }

    /**
     * Starts running the server and spawns a new ServerThread for each new client connecting
     */
    protected void startAndListen() {
        try {
            serverSocket = new ServerSocket(portNumber);

            Logger.logString("ServerApp started on " + getIpAddresses() + " listening on port: " + portNumber);

            //noinspection InfiniteLoopStatement
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Blocking call. Waits for client
                SocketAddress clientIp = clientSocket.getRemoteSocketAddress();
                Logger.logString("Client connected from " + clientIp);

                Thread clientThread = new Thread(new HandleClientReply(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            Logger.logString("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }

    /**
     * Gets all ip addresses used by the server
     *
     * @return Ip addresses
     */
    private String getIpAddresses() {
        String ipAddresses = "";
        Enumeration enumeration;
        try {
            enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) enumeration.nextElement();
                Enumeration ee = n.getInetAddresses();

                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    ipAddresses += i.getHostAddress() + " ";
                }
            }

        } catch (SocketException e1) {
            e1.printStackTrace();
        }

        return ipAddresses;
    }

    /**
     * Handles the reply from a client and any loss of connection to the server.
     */
    private class HandleClientReply implements Runnable {
        private Socket clientSocket;
        private PrintWriter serverOut;
        private User user;

        public HandleClientReply(Socket clientSocket) {
            this.clientSocket = clientSocket;
            this.user = new User("Unnamed", clientSocket, channelManager.getDefaultChannel());
        }

        public void run() {
            String fromClient;

            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                serverOut = new PrintWriter(clientSocket.getOutputStream(), true);

                serverOut.println("Enter a name: ");
                while ((fromClient = in.readLine()) != null) {
                    if (user.getState() == User.State.LOGIN) {
                        if(login(fromClient)) { // fromClient is a proposed username
                            user.setName(fromClient);
                            user.setState(User.State.CHATTING);
                        }
                    } else if(user.getState() == User.State.CHATTING) {
                        if(fromClient.startsWith("/")) { // Server command
                            handleCommand(user, fromClient);
                        } else {
                            user.getChannel().messageAllOtherUsers(new ChatMessage(user, fromClient));
                        }
                    }
                }
            } catch (IOException e) {
                Logger.logString("Lost connection to \"" + user.getName() + "\"");
                if (user != null) {
                    users.remove(user);
                }
                // e.printStackTrace(); // TODO: Decide if I want this actually printed / logged
            }
        }

        /**
         * Attempts to register the user with a username
         *
         * @param proposedUserName username the user proposed
         * @return whether the proposed username was accepted
         */
        private Boolean login(String proposedUserName) {
            if (alphaNumericOnly(proposedUserName)) {
                serverOut.println("That username contains illegal non-alphanumeric character(s), please try another username.");
                return false;
            } else if (!uniqueUsername(proposedUserName)) {
                serverOut.println("That username is already in use, please try another username.");
                return false;
            } else { // Valid username
                user = new User(proposedUserName, clientSocket, channelManager.getDefaultChannel());
                users.add(user);
                channelManager.addUser(user);
                user.writeString("Welcome to \"" + channelManager.getDefaultChannel().getName() + "\"");
                user.writeString(user.getChannel().getUserNames());
                channelManager.getDefaultChannel().messageAllOtherUsers(new ChatMessage(user, "has joined the channel"));
                Logger.logString(user.getIp() + ":" + user.getPort() + " now known as " + user.getName());
                return true;
            }
        }

        /**
         * Lists all commands supported by this Server
         */
        private void printCommands() {
            user.writeString("\t\\help \t lists all commands");
            user.writeString("\t\\list \t lists the users in your channel");
            user.writeString("\t\\quit \t disconnect from the server");
        }

        /**
         * Handles a command from the user
         *
         * @param command command user entered. Starts with "/"
         */
        private void handleCommand(User user, String command) {
            switch (command) {
                case "/list":
                    user.writeString(user.getChannel().getUserNames());
                    break;
                case "/quit":
                case "/disconnect":
                    user.setState(User.State.LOGOUT);
                    users.remove(user); // Removes the user from the server's list
                    user.getChannel().removeUser(user); //Removes the user from the channel he / she was in
                    user.disconnect();
                    Logger.logString(user.getName() + " disconnected");
                    channelManager.getDefaultChannel().messageAllOtherUsers(new ChatMessage(user, "has left the server"));
                default:
                    user.writeString("Unknown command: \"" + command + "\"");
                case "/help":
                    printCommands();
                    break;
            }
        }
    }

    /**
     * Runs an instance of ServerApp
     *
     * @param args <port number>
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.err.println("Usage: java ServerApp <port number>");
            System.exit(1);
        }

        ServerApp serverApp = new ServerApp(Integer.parseInt(args[0]));
        serverApp.startAndListen();
    }
}