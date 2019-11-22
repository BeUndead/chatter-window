package com.com.chatter;

import javax.swing.SwingUtilities;

public class UI {

    public static void main(final String... args) {
        SwingUtilities.invokeLater(() -> {
            new AppFrame();
        });
    }
}
