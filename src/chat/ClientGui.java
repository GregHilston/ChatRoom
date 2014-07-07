package chat;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import javax.swing.*;

public class ClientGui {
    JFrame frame;

    public ClientGui() {
        frame = new JFrame();
        initComponents();
        frame.setVisible(true);
    }

    private void sendButtonActionPerformed(ActionEvent e) {
        String messsage = inputField.getText();
        inputField.setText("");

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        southPanel = new JPanel();
        inputField = new JTextField();
        sendButton = new JButton();
        centerPane = new JScrollPane();
        outputPanel = new JTextPane();
        eastPane = new JScrollPane();
        userListPane = new JTextPane();

        //======== this ========
        frame.setTitle("Chat Room");
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== southPanel ========
        {
            southPanel.setLayout(new FlowLayout());

            //---- inputField ----
            inputField.setPreferredSize(new Dimension(300, 23));
            inputField.setMinimumSize(new Dimension(100, 23));
            inputField.setMaximumSize(new Dimension(2147483647, 23));
            southPanel.add(inputField);

            //---- sendButton ----
            sendButton.setText("Send");
            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sendButtonActionPerformed(e);
                }
            });
            southPanel.add(sendButton);
        }
        contentPane.add(southPanel, BorderLayout.SOUTH);

        //======== centerPane ========
        {

            //---- outputPanel ----
            outputPanel.setMinimumSize(new Dimension(300, 300));
            outputPanel.setPreferredSize(new Dimension(300, 300));
            outputPanel.setEditable(false);
            centerPane.setViewportView(outputPanel);
        }
        contentPane.add(centerPane, BorderLayout.CENTER);

        //======== eastPane ========
        {

            //---- userListPane ----
            userListPane.setPreferredSize(new Dimension(100, 21));
            userListPane.setOpaque(false);
            userListPane.setMinimumSize(new Dimension(75, 21));
            userListPane.setMaximumSize(new Dimension(2147483647, 21));
            userListPane.setEditable(false);
            eastPane.setViewportView(userListPane);
        }
        contentPane.add(eastPane, BorderLayout.EAST);
        frame.pack();
        frame.setLocationRelativeTo(frame.getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel southPanel;
    private JTextField inputField;
    private JButton sendButton;
    private JScrollPane centerPane;
    private JTextPane outputPanel;
    private JScrollPane eastPane;
    private JTextPane userListPane;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
