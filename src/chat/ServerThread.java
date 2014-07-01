package chat;

import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    private Socket socket = null;
    private Protocol protocol;
    private PrintWriter out;

    public ServerThread(Socket socket) {
        super("ServerThread");
        this.socket = socket;
    }

    public void run() {
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String fromClient, fromServerThread;
            protocol = new Protocol();
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
                ServerInfo.getInstance().removeUserName(getUserName());
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
        out.print("Broadcast: " + formattedMessage);
    }


    public String getUserName() {
        return protocol.getUserName();
    }
}
