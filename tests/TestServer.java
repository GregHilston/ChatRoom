import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(12345);
        System.out.println("before ss.accept();");
        Socket connectionToClient = ss.accept();
        System.out.println("after ss.accept();");
        ss.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(connectionToClient.getInputStream()));
        while(true) {
            System.out.println("before readLine");
            System.out.println("readLine returned: " + in.readLine());
            System.out.println("after readLine");
        }
    }
}
