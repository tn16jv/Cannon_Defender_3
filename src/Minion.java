/**
 * Defines the Sprite entity of common minions.
 */

import javax.swing.ImageIcon;

public class Minion extends Sprite {

    private final String MinionADown1 = "/MinionA Down 1.png"; //Declares string to image location
    private final String MinionADown2 = "/MinionA Down 2.png";
    private final String MinionAUp1 = "/MinionA Up 1.png";
    private final String MinionAUp2 = "/MinionA Up 2.png";
    private final String MinionALeft1 = "/MinionA Left 1.png";
    private final String MinionALeft2 = "/MinionA Left 2.png";
    private final String MinionARight1 = "/MinionA Right 1.png";
    private final String MinionARight2 = "/MinionA Right 2.png";
    private final String death = "/Explosion.GIF";
    ImageIcon D1 = new ImageIcon(this.getClass().getResource(MinionADown1)); //Declares imageicons to the image locations
    ImageIcon D2 = new ImageIcon(this.getClass().getResource(MinionADown2));
    ImageIcon U1 = new ImageIcon(this.getClass().getResource(MinionAUp1));
    ImageIcon U2 = new ImageIcon(this.getClass().getResource(MinionAUp2));
    ImageIcon L1 = new ImageIcon(this.getClass().getResource(MinionALeft1));
    ImageIcon L2 = new ImageIcon(this.getClass().getResource(MinionALeft2));
    ImageIcon R1 = new ImageIcon(this.getClass().getResource(MinionARight1));
    ImageIcon R2 = new ImageIcon(this.getClass().getResource(MinionARight2));
    ImageIcon DE = new ImageIcon(this.getClass().getResource(death));
    
    private int animTick;
    private int multiplier; //Either multiplies the x and y values by positive or negative

        /*Pre: class created from Background class
     Post: sets the starting image and starting XY values */
    public Minion() {
        multiplier = (int) (Math.random() * 2);
        if (multiplier == 1) {
            multiplier = 1;
        } else {
            multiplier = -1;
        }
        
        setImage(D1.getImage());
        x = multiplier * (int) (Math.random() * 2000 + 1); //Sets x to a random location
        y = multiplier *(int) (Math.random() * 2000 + 1); //Sets y to a random location
    }

    public void bumpEffect(int dx, int dy, int pdir) {
        switch (pdir) {
            case 1:
                y += 30;
                break;
            case 2:
                y -= 30;
                break;
            case 3:
                x -= 30;
                break;
            case 4:
                x += 30;
                break;
        }
    }

    /*Pre: move is called from cycle in Background, sending in player's directions for x and y, and player's x and y value
     Post: sets an image based on conditions and moves the minion x and y*/
    public void move(int pdx, int pdy, int px, int py) {
        if (px - x > py - y) { //If minion is farther than the player horizontally(X) than vertically (Y)
            if (dx > 0) { //If directional x is positive
                switch (animTick) { //Sets to image based on the animTick variable sent in
                    case 1:
                        setImage(R1.getImage());
                        break;
                    case 2:
                        setImage(R2.getImage());
                        break;
                    default:
                        setImage(R1.getImage());
                        break;
                }
            } else {
                switch (animTick) { //Sets to image based on the animTick variable sent in
                    case 1:
                        setImage(L1.getImage());
                        break;
                    case 2:
                        setImage(L2.getImage());
                        break;
                    default:
                        setImage(L1.getImage());
                        break;
                }
            }
        } else {
            if (dy > 0) { //If directionaly is positive
                switch (animTick) { //Sets to image based on the animTick variable sent in
                    case 1:
                        setImage(D1.getImage());
                        break;
                    case 2:
                        setImage(D2.getImage());
                        break;
                    default:
                        setImage(D1.getImage());
                        break;
                }
            } else {
                switch (animTick) { //Sets to image based on the animTick variable sent in
                    case 1:
                        setImage(U1.getImage());
                        break;
                    case 2:
                        setImage(U2.getImage());
                        break;
                    default:
                        setImage(U1.getImage());
                        break;
                }
            }
        }
        x += dx - pdx; //Moves the x according to the direction x and countermoving to the player's direction
        y += dy - pdy; //Moves the y according to the direction y and countermoving to the player's direction
    }

    /*Pre: moveDictate is called from cycle in Background sending in player's X value and player's Y value
     Post: sets the direction for minion movement based on where the minion is to the player*/
    public void moveDictate(int playerX, int playerY) {
        if (playerX > x) {
            dx = 1;
        } else if (playerX < x) {
            dx = -1;
        } else {
            dx = 0;
        }

        if (playerY > y) {
            dy = 1;
        } else if (playerY < y) {
            dy = -1;
        } else {
            dy = 0;
        }

    }

    /*Pre: setTick is called from Background, sending in the animTick value
     Post: sets the minion's animTick value to Background's animTick*/
    public void setTick(int num) {
        animTick = num;
    }
    
    /*Pre: destroyed is called from cycle in Background when the HP for minion reaches 0
     Post: sets the minion to be invisible*/
    public void destroyed() {
        setImage(DE.getImage());
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
        setImage(D1.getImage());
        visible = true;
    }
}
