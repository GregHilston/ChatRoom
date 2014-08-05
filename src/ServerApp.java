import java.net.*;
import java.io.*;
import java.net.ServerSocket;
import java.util.Enumeration;

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

            System.out.println("ServerApp started on " + getIpAddresses() + " listening on port: " + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Blocking call. Waits for client
                System.out.println("Client connected");
                // TODO: Here is where the ChannelManager's default Channel would get a new ServerThread for the newly connected user
            }
        }catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }


    /***
     * Gets just the local ip address used by the server
     *
     * @return      Local ip address
     */
    private String getIpAddresses() {
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


    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java ServerApp <port number>");
            System.exit(1);
        }

        ServerApp serverApp = new ServerApp(Integer.parseInt(args[0]));
        serverApp.startAndListen();
    }
}