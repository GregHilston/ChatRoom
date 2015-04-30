import javax.swing.*;
import java.awt.*;

/**
 * Client GUI. Has a spot for messages to appear, list of users in the channel, area of the user to type a message and
 * button to submit messages
 */
public class ClientGuiApp {
    private JFrame frame;
    private ChatBoxUserList chatBoxUserList;
    private JPanel basePanel;
    private JPanel messageAndSubmitPanel;
    private GridBagConstraints gridBagConstraints;
    private JTextField messageTextField;
    private JButton submitButton;

    public ClientGuiApp() {
        frame = new JFrame();
        frame.setSize(600, 600); // TODO: Change to be relative
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        basePanel = new JPanel();
        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));

        chatBoxUserList = new ChatBoxUserList();

        messageAndSubmitPanel = new JPanel();
        messageAndSubmitPanel.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;

        messageTextField = new JTextField();
        messageAndSubmitPanel.add(messageTextField, gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;

        submitButton = new JButton("Submit");
        messageAndSubmitPanel.add(submitButton, gridBagConstraints);

        basePanel.add(chatBoxUserList);
        basePanel.add(messageAndSubmitPanel);
        frame.add(basePanel);

        frame.setVisible(true);
    }

    public static void main(String args[]) {
        ClientGuiApp clientGuiApp = new ClientGuiApp();
    }
}
