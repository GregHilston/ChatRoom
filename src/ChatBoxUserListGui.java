import javax.swing.*;
import java.awt.*;

/**
 * Just supports seeing chat messages and displaying users for this channel.
 * To be used by ClientGUI and later ServerGUI
 */
public class ChatBoxUserListGui extends JPanel {
    private JScrollPane chatBoxScrollPane;
    private JTextArea chatBox; // Where all chat messages are displayed
    private JScrollPane userListScrollPane;
    private JTextArea userList; // Where all user in the channel are displayed

    public ChatBoxUserListGui() {
        super();
        this.setLayout(new BorderLayout()); // TODO: Decide if this is better than new BoxLayout(this, BoxLayout.X_AXIS)

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBoxScrollPane = new JScrollPane(chatBox);

        userList = new JTextArea();
        userList.setEditable(false);
        userListScrollPane = new JScrollPane(userList);

        this.add(chatBoxScrollPane, BorderLayout.CENTER);
        this.add(userListScrollPane, BorderLayout.EAST);
    }

    public JTextArea getChatBox() {
        return chatBox;
    }
}