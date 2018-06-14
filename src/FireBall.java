/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nguyentha
 */
import javax.swing.ImageIcon;

public class FireBall extends Sprite {

    private final String fireball = "/Fireball.png";
    ImageIcon ii = new ImageIcon(this.getClass().getResource(fireball));
    private int multiplier; //Either multiplies the x and y values by positive or negative

    public FireBall() {
        multiplier = (int) (Math.random() * 2);
        if (multiplier == 1) {
            multiplier = 1;
        } else {
            multiplier = -1;
        }

        setImage(ii.getImage());
        x = multiplier * (int) (Math.random() * 2000 + 1); //Sets x to a random location
        y = multiplier * (int) (Math.random() * 2000 + 1); //Sets y to a random location
    }

    public void move(int pdx, int pdy) {
        x += dx - pdx; //Moves the x according to the direction x and countermoving to the player's direction
        y += dy - pdy; //Moves the y according to the direction y and countermoving to the player's direction
    }

        /*Pre: moveDictate is called from cycle in Background sending in player's X value and player's Y value
     Post: sets the direction for minion movement based on where the minion is to the player*/
    public void moveDictate(int playerX, int playerY) {
        if (playerX > x) {
            dx = 10;
        } else if (playerX < x) {
            dx = -10;
        } else {
            dx = 0;
        }

        if (playerY > y) {
            dy = 10;
        } else if (playerY < y) {
            dy = -10;
        } else {
            dy = 0;
        }

    }

    /*Pre: destroyed is called from cycle in Background when the HP for minion reaches 0
     Post: sets the minion to be invisible*/
    public void destroyed() {
        visible = false;
    }

    /*Pre: respawn is called from cycle in Background, sending in player's x and y
     Post: sets the x and y values of minion to a random number and sets minion to be visible again*/
    public void respawn(int px, int py) {
        multiplier = (int) (Math.random() * 2);
        if (multiplier == 1) {
            multiplier = 1;
        } else {
            multiplier = -1;
        }

        x = px + multiplier * (int) (Math.random() * 2000 + 1);
        y = py + multiplier * (int) (Math.random() * 2000 + 1);
        visible = true;
    }
}
