package chat;

import java.net.*;
import java.io.*;

public class ServerApp {
    private ServerSocket serverSocket;
    private Broadcaster broadcaster = Broadcaster.getInstance();

    public ServerApp(int portNumber) {
        ServerInfo.getInstance().setPortNumber(portNumber);
    }


    private void startAndListen() {
        try{
            serverSocket = new ServerSocket(ServerInfo.getInstance().getPortNumber());
            System.out.println("ServerApp listening on port: " + ServerInfo.getInstance().getPortNumber());
            Logger.getInstance().createLogFile();

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Blocking call. Waits for client
                ServerThread currentThread = new ServerThread(clientSocket, ServerInfo.getInstance().getClientCount());
                broadcaster.addThread(currentThread);
                Logger.getInstance().log("Client " + ServerInfo.getInstance().getClientCount() + " connected from " + clientSocket.getRemoteSocketAddress().toString());
                currentThread.start();
                ServerInfo.getInstance().setClientCount(ServerInfo.getInstance().getClientCount() + 1);
            }
        }catch (IOException e) {
            System.err.println("Could not listen on port " + ServerInfo.getInstance().getPortNumber());
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