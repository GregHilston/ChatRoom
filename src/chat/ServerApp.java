package chat;

import java.net.*;
import java.io.*;

public class ServerApp {
    private ServerSocket serverSocket;
    private Broadcaster broadcaster = Broadcaster.getInstance();
    private ServerInfo serverInfo = ServerInfo.getInstance();
    private Logger logger = Logger.getInstance();


    public ServerApp(int portNumber) {
        serverInfo.setPortNumber(portNumber);
    }

    /***
     * Starts running the server and spawns a new ServerThread for each new client connecting
     */
    private void startAndListen() {
        try{
            serverSocket = new ServerSocket(serverInfo.getPortNumber());

            System.out.println("ServerApp started on " + serverInfo.getLocalIpAddress() + " listening on port: " + serverInfo.getPortNumber());
            logger.createLogFile();

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Blocking call. Waits for client
                ServerThread currentThread = new ServerThread(clientSocket, serverInfo.getClientCount());
                broadcaster.addThread(currentThread);
                logger.log("Client " + serverInfo.getClientCount() + " connected from " + clientSocket.getRemoteSocketAddress().toString());
                currentThread.start();
                serverInfo.setClientCount(serverInfo.getClientCount() + 1);
            }
        }catch (IOException e) {
            System.err.println("Could not listen on port " + serverInfo.getPortNumber());
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