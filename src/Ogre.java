/**
 * Defines the Sprite entity of the strong ogres.
 */

import javax.swing.ImageIcon;

public class Ogre extends Sprite {

    private final String MinionBDown1 = "/MinionB Down 1.png"; //Declares string to image location
    private final String MinionBDown2 = "/MinionB Down 2.png";
    private final String MinionBUp1 = "/MinionB Up 1.png";
    private final String MinionBUp2 = "/MinionB Up 2.png";
    private final String MinionBLeft1 = "/MinionB Left 1.png";
    private final String MinionBLeft2 = "/MinionB Left 2.png";
    private final String MinionBRight1 = "/MinionB Right 1.png";
    private final String MinionBRight2 = "/MinionB Right 2.png";
    private final String death = "/Explosion.GIF";
    ImageIcon D1 = new ImageIcon(this.getClass().getResource(MinionBDown1)); //Declares imageicons to the image locations
    ImageIcon D2 = new ImageIcon(this.getClass().getResource(MinionBDown2));
    ImageIcon U1 = new ImageIcon(this.getClass().getResource(MinionBUp1));
    ImageIcon U2 = new ImageIcon(this.getClass().getResource(MinionBUp2));
    ImageIcon L1 = new ImageIcon(this.getClass().getResource(MinionBLeft1));
    ImageIcon L2 = new ImageIcon(this.getClass().getResource(MinionBLeft2));
    ImageIcon R1 = new ImageIcon(this.getClass().getResource(MinionBRight1));
    ImageIcon R2 = new ImageIcon(this.getClass().getResource(MinionBRight2));
    ImageIcon DE = new ImageIcon(this.getClass().getResource(death));
    
    private int animTick;
    private int ogreHP = 15; //HP of the ogre
    private int multiplier; //Either multiplies the x and y values by positive or negative

    /*Pre: class created from Background class
     Post: sets the starting image and starting XY values */
    public Ogre() {
        multiplier = (int) (Math.random() * 2);
        if (multiplier == 1) {
            multiplier = 1;
        } else {
            multiplier = -1;
        }

        setImage(D1.getImage());
        x = multiplier * (int) (Math.random() * 2000 + 1); //Sets x to a random location
        y = multiplier * (int) (Math.random() * 2000 + 1); //Sets y to a random location
    }

    /*Pre: getHp is called from cycle in Background class for use
    Post: returns the value of ogreHP stored in Ogre*/
    public int getHP() {
        return (ogreHP);
    }

    /*Pre: HPEffect is called from cycle in Background when an interaction occurs
    Post: takes away HP from ogreHP and pushes the Ogre in a certain direction based on player direction*/
    public void HPEffect(int effect, int dx, int dy, int pdir) {
        ogreHP += effect;
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
        if (px - x > py - y) { //If ogre is farther than the player horizontally(X) than vertically (Y)
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
     Post: sets the direction for ogre movement based on where the ogre is to the player*/
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
     Post: sets the x and y values of ogre to a random number, resets ogreHP, and sets ogre to be visible again*/
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
        ogreHP = 15;
        visible = true;
    }
}
