import java.io.*;
import java.net.*;

public class ClientApp {
    private String hostName;
    private int portNumber;
    private Socket socket;
    private Boolean connected = false;

    public ClientApp(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }


    /***
     * Client attempting to connect to the ChatRoom's ServerApp
     */
    protected void connect() {
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


    public static void main(String[] args) {
        String hostName = "";
        int portNumber = -1;

        if(args.length == 2) {
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        }
        else{
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            try {
                System.out.print("Please enter the server's ip address: ");
                hostName = stdIn.readLine();

                System.out.print("Please enter the server's port number: ");
                portNumber = Integer.parseInt(stdIn.readLine());
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        ClientApp clientApp = new ClientApp(hostName, portNumber);
        clientApp.connect();
    }
}
