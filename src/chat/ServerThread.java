package chat;

import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    private Socket socket = null;
    private Protocol protocol;
    private PrintWriter out;
    private int clientNumber;

    public ServerThread(Socket socket, int clientNumber) {
        super("ServerThread");
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    public void run() {
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String fromClient, fromServerThread;
            protocol = new Protocol();
            protocol.setClientNumber(clientNumber);
            fromServerThread = protocol.processInput(null);
            out.println(fromServerThread); // Initial server message

            while ((fromClient = in.readLine()) != null) { // Blocking for client input
                fromServerThread = protocol.processInput(fromClient);

                out.println(fromServerThread); // Server's response, based on this client's input

                if (fromServerThread != null && fromServerThread.equals("Bye")) {
                    break;
                }
            }
            socket.close();
        } catch (SocketException e) {
            if(getUserName() != null) {
                ServerInfo.getInstance().removeUserName(getUserName()); // Un-register this name
                Broadcaster.getInstance().removeThread(this); // Remove ourselves from the list of running threads
                Logger.getInstance().log(protocol.getUserName() + " has disconnected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Prints the formattedMessage to the ServerThread's client
     *
     * @param formattedMessage
     */
    public void print(String formattedMessage) {
        out.println(formattedMessage);
    }


    public String getUserName() {
        return protocol.getUserName();
    }
}
