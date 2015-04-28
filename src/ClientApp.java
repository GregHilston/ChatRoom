import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

// TODO: Have a way to send a message from an instance of this object

/**
 * Application that end user interacts with.
 */
public class ClientApp {
    private String hostName;
    private int portNumber;
    private Socket serverSocket;
    private Boolean connected = false;

    public ClientApp(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    /**
     * Client attempting to connect to the ChatRoom's ServerApp
     */
    protected void connect() {
        try {
            serverSocket = new Socket(hostName, portNumber);

            Thread serverThread = new Thread(new HandleServerReply());
            serverThread.start();

            Thread clientThread = new Thread(new HandleClientInput());
            clientThread.start();

            connected = true;
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }

    /**
     * Client gracefully disconnecting
     */
    protected void disconnect() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            Logger.logString("serverSocket could not be closed");
        }
    }

    protected Socket getServerSocket() {
        return serverSocket;
    }

    /**
     * Handles the reply from the server and any loss of connection to the server.
     */
    private class HandleServerReply implements Runnable {
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))) {
                String fromServer;
                while ((fromServer = in.readLine()) != null) {
                    System.out.println(fromServer);
                    // TODO: Implement and handle server replies
                }
                connected = false;
            } catch (IOException e) {
                System.err.println("ERROR: Lost connection to server");
                System.exit(-1);
            }
        }
    }

    /**
     * Handles the client's input and sending it to the Server
     */
    private class HandleClientInput implements Runnable {
        public void run() {
            try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
                PrintWriter clientOut = new PrintWriter(serverSocket.getOutputStream(), true);
                String clientInput;
                while (connected) {
                    clientInput = stdIn.readLine();
                    if (clientInput != null) {
                        clientOut.println(clientInput);
                    }
                }
                clientOut.close();
            } catch (IOException e) {
                System.err.println("ERROR: Lost connection to server");
                System.exit(-1);
            }
        }
    }

    /**
     * Runs an instance of ClientApp
     *
     * @param args <server ip address> <server port number>
     */
    public static void main(String[] args) {
        String hostName = "";
        int portNumber = -1;

        if (args.length == 2) {
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            try {
                System.out.print("Please enter the server's ip address: ");
                hostName = reader.readLine();

                System.out.print("Please enter the server's port number: ");
                portNumber = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ClientApp clientApp = new ClientApp(hostName, portNumber);
        clientApp.connect();
    }
}
