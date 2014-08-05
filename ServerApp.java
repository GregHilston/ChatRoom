import java.net.*;
import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerApp {
    private ServerSocket serverSocket;
    private int portNumber;

    public ServerApp(int portNumber) {
        this.portNumber = portNumber;
    }

    /***
     * Starts running the server and spawns a new ServerThread for each new client connecting
     */
    private void startAndListen() {
        try{
            serverSocket = new ServerSocket(portNumber);

            System.out.println("ServerApp started on " + getLocalIpAddress() + " listening on port: " + portNumber;

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Blocking call. Waits for client
                System.out.println("Client connected");
            }
        }catch (IOException e) {
            System.err.println("Could not listen on port " + serverInfo.getPortNumber());
            System.exit(-1);
        }
    }


    /***
     * Gets just the local ip address used by the server
     *
     * @return      Local ip address
     */
    private String getLocalIpAddress() {
        String ipAddresses = "";
        Enumeration enumeration;
        try {
            enumeration = NetworkInterface.getNetworkInterfaces();
            while(enumeration.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) enumeration.nextElement();
                Enumeration ee = n.getInetAddresses();

                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    ipAddresses += i.getHostAddress() + " ";
                }
            }

        } catch (SocketException e1) {
            e1.printStackTrace();
        }

        return ipAddresses;
    }

    /**
     * Adds the thread to the list of threads, for connected clients
     *
     * @param currentThread     thread representing actively connected client
     */
    protected static void addThread(ServerThread currentThread) {
        serverThreads.add(currentThread);
    }


    /**
     * Removes the thread from the list of connected users, as this client has disconnected
     *
     * @param currentThread     thread representing client who disconnected
     */
    protected static void removeThread(ServerThread currentThread) {
        serverThreads.remove(currentThread);
    }


    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java ServerApp <port number>");
            System.exit(1);
        }

        ServerApp serverApp = new ServerApp(Integer.parseInt(args[0]));
        serverApp.startAndListen();
    }
}
