package chat;

import java.net.*;
import java.io.*;

public class ServerApp {
    private int portNumber;
    private ServerSocket serverSocket;
    private Broadcaster broadcaster = Broadcaster.getInstance();

    public ServerApp(int portNumber) {
        this.portNumber = portNumber;
    }


    private void startAndListen() {
        try{
            serverSocket = new ServerSocket(portNumber);
            System.out.println("ServerApp listening on port: " + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Blocking call. Waits for client
                ServerThread currentThread = new ServerThread(clientSocket);
                broadcaster.addThread(currentThread);
                currentThread.start();
                System.out.println("ClientApp connected");
            }
        }catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }


    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java ServerApp <port number>");
            System.exit(1);
        }

        ServerApp multiServer = new ServerApp(Integer.parseInt(args[0]));
        multiServer.startAndListen();
    }
}