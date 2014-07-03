package chat;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class ServerInfo {
    private static ServerInfo singleton = new ServerInfo();
    private int portNumber;
    private ArrayList<String> userNames = new ArrayList<String>();
    private int clientCount = 0;


    /***
     * Hidden, to force calling of getInstance
     */
    private ServerInfo() {
        // Singleton
    }


    /***
     * Singleton instance getter
     *
     * @return      The one instance of the ServerInfo
     */
    public static ServerInfo getInstance() {
        return singleton;
    }


    /**
     *
     * @param userName  Proposed UserName
     * @return          True: UserName was accepted    False: UserName already taken
     */
    protected Boolean addUserName(String userName) {
        if(userNames.contains(userName)) {
            return false;
        }
        else {
            userNames.add(userName);
            return true;
        }
    }


    /***
     * Removes this username from the list of registered user names on the server
     *
     * @param userName      Username to unregister
     */
    protected void removeUserName(String userName) {
        userNames.remove(userName);
    }


    /***
     * Gets the number of registered users on the server
     *
     * @return This count only increases, after a unique username has been accepted by the client
     */
    protected int numberOfUsers() {
        return userNames.size();
    }


    /***
     * Get the username at the particular index, in the list of registered usernames
     *
     * @param index     Index of username wanted
     * @return      Username at index
     */
    protected String getUserName(int index) {
        if(index > (userNames.size() - 1))
            return "";

        return userNames.get(index);
    }


    /***
     * Gets just the local ip address used by the server
     *
     * @return      Local ip address
     */
    protected String getLocalIpAddress() {
        String ipAddresses = "";
        Enumeration enumeration = null;
        try {
            enumeration = NetworkInterface.getNetworkInterfaces();
            while(enumeration.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) enumeration.nextElement();
                Enumeration ee = n.getInetAddresses();

                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    if(i instanceof Inet4Address && !i.toString().contains("127")) // Filters all non-local IP addresses
                        ipAddresses = i.getHostAddress();
                }
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
        }

        return ipAddresses;
    }

//    public String getLocalIpAddress() {
//        String ipAddress = "";
//
//        try {
//            ipAddress = InetAddress.getLocalHost().toString();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//
//        return ipAddress;
//    }


    /***
     * @return      Port number used by the server
     */
    protected int getPortNumber() {
        return portNumber;
    }


    /***
     * @param portNumber        Port number used by the server
     */
    protected void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }


    /*** A user is considered in this count even when not in the "CHATTING" state. (IE: Initial handshake counts)
     *
     * @return      Number of clients on the server
     */
    protected int getClientCount() {
        return clientCount;
    }


    /***
     *
     * @param clientCount       Number to set client count to
     */
    protected void setClientCount(int clientCount) {
        this.clientCount = clientCount;
    }
}
