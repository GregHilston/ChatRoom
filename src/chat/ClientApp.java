package chat;

import java.io.*;
import java.net.*;

public class ClientApp {
    private String hostName;
    private int portNumber;

    public ClientApp(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    private void connect() {
        try{
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String clientInput;

            while ((fromServer = in.readLine()) != null) {
                if(!fromServer.equals("Server: CHATTING")) { // Filter the end state message
                    System.out.println(fromServer);
                }

                clientInput = stdIn.readLine();
                if (clientInput != null) {
                    // System.out.println("Client: " + clientInput);
                    out.println(clientInput);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
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
