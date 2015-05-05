import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Client GUI. Has a spot for messages to appear, list of users in the channel, area of the user to type a message and
 * button to submit messages
 */
public class ClientGui {
    private ChatBoxUserListGui chatBoxUserListGui;
    private ClientApp clientApp;

    public ClientGui(ClientApp clientApp) {
        this.clientApp = clientApp;
    }

    public void updateChatBox(String fromServer) {
        chatBoxUserListGui.getChatBox().setText(chatBoxUserListGui.getChatBox().getText() + "\n" + fromServer);
    }

    public void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        chatBoxUserListGui = new ChatBoxUserListGui();

        JTextField messageTextField = new JTextField();
        messageTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientApp.sendString(messageTextField.getText());
                // System.out.println("Message Received: " + messageTextField.getText());
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
    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Client GUI App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
