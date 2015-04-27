import java.io.*;
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
    private ChannelManager channelManager = new ChannelManager("Lobby");

    public ServerApp(int portNumber) {
        this.portNumber = portNumber;
    }

    /**
     * Checks if an entered username is unique on this server
     *
     * @param   name name to check
     * @return  if unique username
     */
    private static boolean uniqueUsername(String name) {
        for(User u : users) {
            if(u.getName().toLowerCase().equals(name.toLowerCase())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if an entered username is composed of only alphanumeric characters
     *
     * @param   name name to check
     * @return  if name contains only alphanumeric characters
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
        private boolean uniqueName = false;
        private User user = null;

        public HandleClientReply(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            String fromClient;

            try(BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                PrintWriter serverOut = new PrintWriter(clientSocket.getOutputStream(), true);

                serverOut.println("Enter a name: ");

                while ((fromClient = in.readLine()) != null) {
                    if (!uniqueName) {
                        // fromClient is a proposed username

                        if (alphaNumericOnly(fromClient)) {
                            serverOut.println("That username contains illegal non-alphanumeric character(s), please try another username.");
                        } else if (!uniqueUsername(fromClient)) {
                            serverOut.println("That username is already in use, please try another username.");
                        } else { // Valid username
                            uniqueName = true;
                            user = new User(fromClient, clientSocket, channelManager.getDefaultChannel());
                            users.add(user);
                            channelManager.addUser(user);
                            user.writeMessage("Welcome to \"" + channelManager.getDefaultChannel().getName() + "\"");
                            Logger.logString(user.getIp() + ":" + user.getPort() + " now known as " + user.getName());
                        }
                    } else {
                        user.getChannel().messageAllOtherUsers(new ChatMessage(user, ChatMessage.Type.MESSAGE, fromClient));
                    }
                }
            } catch (IOException e) {
                Logger.logString("ERROR: Lost connection to \"" + user.getName() + "\"");
                if(user != null) {
                    users.remove(user);
                }
                e.printStackTrace();
            }
        }
    }

    /**
     * Runs an instance of ServerApp
     *
     * @param args  <port number>
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java ServerApp <port number>");
            System.exit(1);
        }

        ServerApp serverApp = new ServerApp(Integer.parseInt(args[0]));
        serverApp.startAndListen();
    }
}