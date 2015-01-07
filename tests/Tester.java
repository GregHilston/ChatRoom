import org.junit.Test;

public class Tester {
    /***
     * Handles the reply from a client and any loss of connection to the server.
     */
    private static class ServerThread implements Runnable {
        ServerApp serverApp = new ServerApp(1234); // Create our server

        public void run() {
            serverApp.startAndListen(); // Open our port up and block on waiting for connections from clients
        }
    }
    
    
    /***
     * Tests to see if we can determine when a client disconnects
     */
    @Test
    public static void testClientDisconnectCaughtException()
    {
        Thread serverThread = new Thread(new ServerThread());
        serverThread.start(); // So we can create a client on this thread
        
        ClientApp clientApp = new ClientApp("127.0.0.1", 1234);
        clientApp.connect();

        System.out.println(clientApp.getServerSocket().isClosed()); // Expect to be false
        clientApp.disconnect();
        System.out.println(clientApp.getServerSocket().isClosed()); // Expect to be true

        assert(clientApp.getServerSocket().isClosed());
    }

    
    public static void main(String[] args) {
        testClientDisconnectCaughtException();
    }
}
