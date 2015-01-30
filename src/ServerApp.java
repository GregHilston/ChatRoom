import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.HashSet;

/**
 * The Chat server that users join channels to speak to one another
 */

public class ServerApp {
    private ServerSocket serverSocket;
    private int portNumber;
    private ChannelManager channelManager = new ChannelManager("Lobby");
    private static HashSet<String> names = new HashSet<>();


    public ServerApp(int portNumber) {
        this.portNumber = portNumber;
    }


    /**
     * Starts running the server and spawns a new ServerThread for each new client connecting
     */
    protected void startAndListen() {
        try {
            serverSocket = new ServerSocket(portNumber);

            Logger.writeMessage("ServerApp started on " + getIpAddresses() + " listening on port: " + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Blocking call. Waits for client
                SocketAddress clientIp = clientSocket.getRemoteSocketAddress();
                Logger.writeMessage("Client connected from " + clientIp);

                Thread clientThread = new Thread(new HandleClientReply(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            Logger.writeMessage("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }


    /**
     * Checks if an entered username is unique on this server
     *
     * @param name name to check
     * @return true    unique username
     */
    private static boolean uniqueUsername(String name) {
        return !names.contains(name.toLowerCase());
    }


    /**
     * Checks if an entered username is composed of only alphanumeric characters
     *
     * @param name name to check
     * @return true     name contains only alphanumeric characters
     */
    private static boolean alphaNumericOnly(String name) {
        return name.matches("^.*[^a-zA-Z0-9].*$"); // Regex
    }

    /**
     * Handles the reply from a client and any loss of connection to the server.
     */
    private class HandleClientReply implements Runnable {
        private Socket clientSocket;
        private boolean uniqueName = false;


        public HandleClientReply(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }


        public void run() {
            String fromClient;
            String name = "Unnamed User";

            try (DataInputStream in = new DataInputStream(clientSocket.getInputStream())) {
                DataOutputStream serverOut = new DataOutputStream(clientSocket.getOutputStream());

                serverOut.writeUTF("Enter a name: ");

                while ((fromClient = in.readUTF()) != null) {
                    if (!uniqueName) {
                        // fromClient is a proposed username

                        if (alphaNumericOnly(fromClient)) {
                            serverOut.writeUTF("That username contains illegal non-alphanumeric character(s), please try another username.");
                        } else if (!uniqueUsername(fromClient)) {
                            serverOut.writeUTF("That username is already in use, please try another username.");
                        } else { // Valid username
                            uniqueName = true;
                            name = fromClient;
                            names.add(name.toLowerCase());
                            User user = new User(name, clientSocket);
                            channelManager.addUser(user);
                            user.writeMessage("Welcome to \"" + channelManager.getDefaultChannel().getName() + "\"");
                            Logger.writeMessage(user.getIpAndPort() + " now known as " + name);
                        }
                    } else {
                        channelManager.messageAllUsers(name + ": " + fromClient);
                    }
                }
            } catch (IOException e) {
                Logger.writeMessage("ERROR: Lost connection to \"" + name + "\"");
                names.remove(name.toLowerCase());
                e.printStackTrace();
            }
            
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


    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java ServerApp <port number>");
            System.exit(1);
        }

        ServerApp serverApp = new ServerApp(Integer.parseInt(args[0]));
        serverApp.startAndListen();
    }
}