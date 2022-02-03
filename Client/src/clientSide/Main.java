package clientSide;

import clientSide.view.UserInterface;

import javax.swing.*;

/**
 * A class to run the gui based menu.
 *
 * @author Hammad
 */

public class Main {
    /**
     * main method, entry point of the program.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserInterface();
            }
        });

    }

}
