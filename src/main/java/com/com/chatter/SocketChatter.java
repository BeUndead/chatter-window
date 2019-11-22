/*
 * Created by JFormDesigner on Fri Nov 22 11:20:06 GMT 2019
 */

package com.com.chatter;

import com.com.sockets.CustomSessionHandler;
import net.miginfocom.swing.MigLayout;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import java.util.concurrent.TimeUnit;

/**
 * @author Liam Bryan
 */
public class SocketChatter extends JPanel {

    private static String URL = "ws://localhost:8080/chat";

    private CustomSessionHandler jimsHandler;
    private CustomSessionHandler bobsHandler;
    private CustomSessionHandler sendingHandler;

    private final WebSocketStompClient jimsClient =
            new WebSocketStompClient(new StandardWebSocketClient());
    private final WebSocketStompClient bobsClient =
            new WebSocketStompClient(new StandardWebSocketClient());
    private final WebSocketStompClient sendingClient =
            new WebSocketStompClient(new StandardWebSocketClient());

    public SocketChatter() {
        initComponents();
        SwingUtilities.invokeLater(() -> connectClients());
    }

    private void connectClients() {
        this.jimsClient.setMessageConverter(new MappingJackson2MessageConverter());
        this.bobsClient.setMessageConverter(new MappingJackson2MessageConverter());
        this.sendingClient.setMessageConverter(new MappingJackson2MessageConverter());

        jimsHandler = new CustomSessionHandler("Jim", msg -> appendText(this.jimTextArea, msg));
        bobsHandler = new CustomSessionHandler("Bob", msg -> appendText(this.bobTextArea, msg));
        sendingHandler = new CustomSessionHandler("Sender", msg -> {});

        this.jimsClient.connect(URL, jimsHandler);
        this.bobsClient.connect(URL, bobsHandler);
        this.sendingClient.connect(URL, sendingHandler);

        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (final InterruptedException intex) {
            // Ah wells...
        }
    }

    private void appendText(final JTextArea area, final String newText) {
        SwingUtilities.invokeLater(() -> {
            final String oldText = area.getText();
            area.setText(oldText + "\n" + newText);
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        bobScrollPane = new JScrollPane();
        bobTextArea = new JTextArea();

        jimScrollPane = new JScrollPane();
        jimTextArea = new JTextArea();

        toLabel = new JLabel();
        toComboBox = new JComboBox<String>();
        toComboBox.addItem("");
        toComboBox.addItem("Jim");
        toComboBox.addItem("Bob");
        toComboBox.addItem("Everyone");
        toComboBox.setEditable(false);

        messageLabel = new JLabel();
        messageTextArea = new JTextArea();

        messageScrollPane = new JScrollPane();

        sendButton = new JButton();

        //======== this ========
        setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[300:300]" +
            "[300:300]" +
            "[150:150]" +
            "[150:150]",
            // rows
            "[]" +
            "[]" +
            "[300!]" +
            "[]"));

        //======== scrollPane1 ========
        {
            bobScrollPane.setViewportView(bobTextArea);
        }
        bobTextArea.setEditable(false);
        add(bobScrollPane, "cell 0 0 1 4,grow");
        bobTextArea.setBorder(new TitledBorder("Bob"));

        //======== scrollPane2 ========
        {
            jimScrollPane.setViewportView(jimTextArea);
        }
        jimTextArea.setEditable(false);
        add(jimScrollPane, "cell 1 0 1 4,grow");
        jimTextArea.setBorder(new TitledBorder("Jim"));

        //---- label2 ----
        toLabel.setText("To");
        toLabel.setLabelFor(this.toComboBox);
        add(toLabel, "cell 2 0");
        add(toComboBox, "cell 3 0,grow");

        //---- label3 ----
        messageLabel.setText("Message");
        messageLabel.setLabelFor(this.messageTextArea);

        //======== scrollPane2 ========
        {
            messageScrollPane.setViewportView(messageTextArea);
        }
        add(messageScrollPane, "cell 2 2 2 1, grow");

        add(messageLabel, "cell 2 1");

        //---- button1 ----
        sendButton.setText("Send Message");
        add(sendButton, "cell 2 3 2 1,align center center,grow 0 0");
        sendButton.addActionListener((event) -> {
            final String to = (String) this.toComboBox.getSelectedItem();
            final String message = this.messageTextArea.getText();

            SwingUtilities.invokeLater(() -> {
                this.sendingHandler.send(to, message);
            });

            this.toComboBox.setSelectedIndex(0);
            this.messageTextArea.setText("");

            System.out.println("Sending that message");
        });
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JScrollPane jimScrollPane;
    private JTextArea jimTextArea;
    private JScrollPane bobScrollPane;
    private JTextArea bobTextArea;
    private JLabel toLabel;
    private JComboBox<String> toComboBox;
    private JLabel messageLabel;
    private JTextArea messageTextArea;
    private JScrollPane messageScrollPane;
    private JButton sendButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
