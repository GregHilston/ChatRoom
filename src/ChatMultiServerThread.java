import java.net.*;
import java.io.*;

public class ChatMultiServerThread extends Thread {
    private Socket socket = null;

    public ChatMultiServerThread(Socket socket) {
        super("ChatMultiServerThread");
        this.socket = socket;
    }

    public void run() {
        try{
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine, outputLine;
            ChatProtocol chatProtocol = new ChatProtocol();
            outputLine = chatProtocol.processInput(null);
            out.println(outputLine); // Initial server message

            while ((inputLine = in.readLine()) != null) {
                outputLine = chatProtocol.processInput(inputLine);
                out.println(outputLine); // Server message, based on this client's input
                if (outputLine != null && outputLine.equals("Bye"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
