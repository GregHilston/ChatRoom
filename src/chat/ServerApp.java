package chat;

import java.net.*;
import java.io.*;

public class ServerApp {
    private int portNumber;
    private ServerSocket serverSocket;
    private Broadcaster broadcaster = Broadcaster.getInstance();
    private int clientCount = 0;

    public ServerApp(int portNumber) {
        this.portNumber = portNumber;
    }


    private void startAndListen() {
        try{
            serverSocket = new ServerSocket(portNumber);
            System.out.println("ServerApp listening on port: " + portNumber);
            Logger.getInstance().createLogFile();

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Blocking call. Waits for client
                ServerThread currentThread = new ServerThread(clientSocket, clientCount);
                broadcaster.addThread(currentThread);
                Logger.getInstance().log("Client " + clientCount + " connected from " + clientSocket.getRemoteSocketAddress().toString());
                currentThread.start();
                clientCount++;
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