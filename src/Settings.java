/**
 * Author: ThaiBinh Nguyen
 * This is the main driver of the game.
 */

import javax.swing.JFrame;

public class Settings extends JFrame {

    /**
     * @param args the command line arguments
     */

    /*pre: none
     post: sets the parameters for the Jframe. Starts the background class, and
     with it the game*/
    public Settings() {
        add(new Background());
        setTitle("Cannon Defender 3");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

    }

    public static void main(String[] args) {
        new Settings();
    }
}
