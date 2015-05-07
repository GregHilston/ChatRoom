import javax.swing.*;
import java.awt.*;

/**
 * Just supports seeing chat messages and displaying users for this channel.
 * To be used by ClientGUI and later ServerGUI
 */
public class ChatBoxUserListGui extends JPanel {
    private JScrollPane chatBoxScrollPane;
    private JTextArea chatBoxTextArea; // Where all chat messages are displayed
    private JScrollPane userListScrollPane;
    private JTextArea userListTextArea; // Where all user in the channel are displayed

    public ChatBoxUserListGui() {
        super();
        this.setLayout(new BorderLayout()); // TODO: Decide if this is better than new BoxLayout(this, BoxLayout.X_AXIS)

        chatBoxTextArea = new JTextArea();
        chatBoxTextArea.setEditable(false);
        chatBoxScrollPane = new JScrollPane(chatBoxTextArea);

        userListTextArea = new JTextArea();
        userListTextArea.setEditable(false);
        userListScrollPane = new JScrollPane(userListTextArea);

        this.add(chatBoxScrollPane, BorderLayout.CENTER);
        this.add(userListScrollPane, BorderLayout.EAST);

        Dimension frameDimension = chatBoxTextArea.getParent().getSize();
        Double frameWidth = frameDimension.getWidth();
        Double frameHeight = frameDimension.getHeight();

        chatBoxTextArea.setSize(frameWidth.intValue() / 6, frameHeight.intValue() / 6);
        userListTextArea.setSize(frameWidth.intValue() / 6, frameHeight.intValue() / 6);
    }

    public JTextArea getChatBoxTextArea() {
        return chatBoxTextArea;
    }

    public JTextArea getUserListTextArea() {
        return userListTextArea;
    }

    public void setUserListTextArea(String userListText) {
        userListTextArea.setText(userListText);
    }
}