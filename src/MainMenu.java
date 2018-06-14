/**
 * Code that specifies what is put into the menu. This also defines the frame that the game is played in.
 */

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Toolkit;

public class MainMenu extends JPanel implements ActionListener {

    JPanel menuPanel = new JPanel();
    JButton play = new JButton("Play");
    private String menuscreen = "/Title Screen.jpg";
    ImageIcon MS = new ImageIcon(this.getClass().getResource(menuscreen));
    private boolean visible = true;

    public MainMenu() {
        addKeyListener(new MainMenu.TAdapter());
        setFocusable(true);
        FlowLayout layout = new FlowLayout();
        setLayout(layout);
        setSize(800, 600);
        JButton startButton = new JButton("Play");
        play.setLocation (0, 0);
        add(startButton);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(100, 100);
        setVisible(true);
        
        
    }

    public void paint(Graphics g) {
        if (visible == true) {
            super.paint(g);
            // g.drawImage(MS.getImage(), 0, -20, this);
        } else {
            System.out.println("GRATZ");
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
            //repaint ();
        }
    }

    void play() {
        System.out.println("yolo");
        add(new Background());
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if (command.equals("Play")) {
            play();
        }
        if (command.equals("Instructions")) {

        }
    }

    private class TAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            visible = false;
            repaint();
            play();
            System.out.println("Yo");
        }
    }
}
