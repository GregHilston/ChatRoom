import java.net.Socket;

public class TestClient {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("127.0.0.1", 12345);
        s.getOutputStream().write("Hello world!\nLine 2\nLine 3\nPartial line".getBytes());
        System.out.println("Sent some data. Now waiting indefinitely so you can kill this process.");
        while(true) Thread.sleep(1000);
    }
}
