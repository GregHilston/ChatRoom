package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientApp {
    public static void main(String[] args) {
        String hostName = "";
        int portNumber = -1;

        if(args.length == 2) {
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        }
        else{
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            try {
            System.out.print("Please enter the server's ip address: ");
            hostName = stdIn.readLine();

            System.out.print("Please enter the server's port number: ");
            portNumber = Integer.parseInt(stdIn.readLine());
            } catch(IOException e) {
                e.printStackTrace();
            }
        }


        ClientGui chatView = new ClientGui();

        Client chatController = new Client(hostName, portNumber);
        chatController.connect();

    }
}
