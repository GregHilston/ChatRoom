import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ChatMultiServer {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java ChatMultiServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try{
            ServerSocket serverSocket = new ServerSocket(portNumber);

            System.out.println("ChatMultiServer listening on port: " + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ChatMultiServerThread(clientSocket).start();
                System.out.println("Client connected");
            }
        }catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}