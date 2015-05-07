import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

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

    /**
     * Updates the list of users
     *
     * @param userList list of users
     */
    public void setUserList(ArrayList<User> userList) {
        String userlistText = "";

        for(User u : userList) {
            userlistText += u.getName() + "\n";
        }

        chatBoxUserListGui.setUserListTextArea(userlistText);
    }

    public void addUserToList(String userName) {
        chatBoxUserListGui.setUserListTextArea(chatBoxUserListGui.getUserListTextArea().getText() + userName + "\n");
    }

    public void removeUserFromList(String userName) {
        chatBoxUserListGui.setUserListTextArea(chatBoxUserListGui.getUserListTextArea().getText().replace(userName + "\n", ""));
    }

    /**
     * Appends the string to the end of the chatbox
     *
     * @param fromServer string to append
     */
    public void updateChatBox(String fromServer) {
        chatBoxUserListGui.getChatBoxTextArea().setText(chatBoxUserListGui.getChatBoxTextArea().getText() + "\n" + fromServer);
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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Double width = screenSize.getWidth();
        Double height = screenSize.getHeight();

        //Create and set up the window.
        JFrame frame = new JFrame("Client GUI App");
        frame.setSize(width.intValue() / 3, height.intValue() / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        //frame.pack();
        frame.setVisible(true);
    }
}
