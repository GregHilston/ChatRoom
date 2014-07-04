package chat;

import java.io.*;
import java.net.*;

public class ClientApp {
    private String hostName;
    private int portNumber;
    private Socket socket;
    private Boolean connected = false;
    private ClientGui clientGui;


    public ClientApp(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.clientGui = ClientGui.getInstance();
    }


    /***
     * Client attempting to connect to the ChatRoom's ServerApp
     */
    private void connect() {
        try{
            socket = new Socket(hostName, portNumber);

            Thread serverThread = new Thread(new HandleServerReply());
            serverThread.start();

            Thread clientThread = new Thread(new HandleClientInput());
            clientThread.start();

            connected = true;
            // socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }


    /***
     * Handles the reply form the server and any loss of connection to the server.
     */
    private class HandleServerReply implements Runnable {


        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String fromServer;

                while ((fromServer = in.readLine()) != null) {
                    if(!fromServer.equals("Server: CHATTING")) { // Filter the end state message
                        System.out.println(fromServer);
                    }
                }

                in.close();
                connected = false;
            } catch(IOException e) {
                System.err.println("ERROR: Lost connection to server");
                System.exit(-1);
            }
        }
    }


    /***
     * Handles the client's input and sending it to the Server
     */
    private class HandleClientInput implements Runnable {

        public void run() {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                String clientInput;

                while(connected) {
                    clientInput = stdIn.readLine();
                    if (clientInput != null) {
                        // System.out.println("Client: " + clientInput);
                        out.println(clientInput);
                    }
                }

                out.close();
                stdIn.close();
            } catch(IOException e) {
                System.err.println("ERROR: Lost connection to server");
                System.exit(-1);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        ClientApp client = new ClientApp(args[0], Integer.parseInt(args[1]));
        client.connect();
    }
}
