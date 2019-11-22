package com.com.chatter;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class AppFrame extends JFrame {

    private final SocketChatter panel = new SocketChatter();

    public AppFrame() {
        this.add(this.panel);

        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
