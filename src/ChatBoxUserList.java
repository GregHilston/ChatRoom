import javax.swing.*;
import java.awt.*;

/**
 * Just supports seeing chat messages and displaying users for this channel.
 * To be used by ClientGUI and later ServerGUI
 */
public class ChatBoxUserList extends JPanel {
    private JScrollPane chatBoxScrollPane;
    private JTextArea chatBox; // Where all chat messages are displayed
    private JScrollPane userListScrollPane;
    private JTextArea userList; // Where all user in the channel are displayed
    private GridBagConstraints gridBagConstraints;

    public ChatBoxUserList() {
        super();
        this.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();

        chatBox = new JTextArea();
        chatBox.setText("chatBox");
        chatBox.setEditable(false);

        chatBoxScrollPane = new JScrollPane(chatBox);

        userList = new JTextArea();
        userList.setText("userList");
        userList.setEditable(false);

        userListScrollPane = new JScrollPane(userList);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.ipady = 500;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(chatBoxScrollPane, gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.ipady = 500;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(userListScrollPane, gridBagConstraints);
    }
}