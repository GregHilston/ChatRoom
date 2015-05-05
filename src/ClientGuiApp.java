import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.*;

/**
 * Client GUI. Has a spot for messages to appear, list of users in the channel, area of the user to type a message and
 * button to submit messages
 */
public class ClientGuiApp {
    private ClientApp clientApp;
    private ChatBoxUserListGui chatBoxUserListGui;
    private JTextArea chatTextArea;

    public ClientGuiApp() {
        clientApp = new ClientApp("127.0.0.1", 1234, true);
        clientApp.connect();

        Thread serverThread = new Thread(new HandleServerReply());
        serverThread.start();
    }

    public void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        chatBoxUserListGui = new ChatBoxUserListGui();
        JTextField messageTextField = new JTextField();
        messageTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientApp.sendString(messageTextField.getText());
                System.out.println("Message Received: " + messageTextField.getText());
                messageTextField.setText("");
            }
        });

        pane.add(chatBoxUserListGui);
        pane.add(messageTextField);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Client GUI App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Handles the reply from the server and any loss of connection to the server.
     */
    private class HandleServerReply implements Runnable {
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientApp.getServerSocket().getInputStream()))) {
                String fromServer;

                while ((fromServer = in.readLine()) != null) {

                    System.out.println("fromServer: \"" + fromServer + "\"");
                    chatTextArea.setText(chatTextArea.getText() + "\n" + fromServer);
                }
            } catch (IOException e) {
                System.err.println("ERROR: Lost connection to server");
                System.exit(-1);
            }
        }
    }

    public static void main(String args[]) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ClientGuiApp clientGuiApp = new ClientGuiApp();
                clientGuiApp.createAndShowGUI();
            }
        });
    }
}
