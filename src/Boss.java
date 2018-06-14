/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.ImageIcon;

/**
 *
 * @author User
 */
public class Boss extends Sprite {

    private final String BossDown1 = "/Boss Down 1.png"; //Declares string to image location
    private final String BossBDown2 = "/Boss Down 2.png";
    private final String BossBUp1 = "/Boss Up 1.png";
    private final String BossBUp2 = "/Boss Up 2.png";
    private final String BossBLeft1 = "/Boss Left 1.png";
    private final String BossBLeft2 = "/Boss Left 2.png";
    private final String BossBRight1 = "/Boss Right 1.png";
    private final String BossBRight2 = "/Boss Right 2.png";
    private final String death = "/Explosion.GIF";
    ImageIcon D1 = new ImageIcon(this.getClass().getResource(BossDown1)); //Declares imageicons to the image locations
    ImageIcon D2 = new ImageIcon(this.getClass().getResource(BossBDown2));
    ImageIcon U1 = new ImageIcon(this.getClass().getResource(BossBUp1));
    ImageIcon U2 = new ImageIcon(this.getClass().getResource(BossBUp2));
    ImageIcon L1 = new ImageIcon(this.getClass().getResource(BossBLeft1));
    ImageIcon L2 = new ImageIcon(this.getClass().getResource(BossBLeft2));
    ImageIcon R1 = new ImageIcon(this.getClass().getResource(BossBRight1));
    ImageIcon R2 = new ImageIcon(this.getClass().getResource(BossBRight2));
    ImageIcon DE = new ImageIcon(this.getClass().getResource(death));
    private int animTick;
    private int bossHP = 100; //HP of the boss
    private int multiplier;

    /*Pre: class created from Background class
    Post: sets the starting image and starting XY values */
    public Boss() {
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
    Post: returns the value of bossHP stored in Boss*/
    public int getHP() {
        return (bossHP);
    }

    /*Pre: HPEffect is called from cycle in Background when an interaction occurs
    Post: takes away HP from ogreHP and pushes the Ogre in a certain direction based on player direction*/
    public void HPEffect(int effect, int px, int py, int pdir) {
        bossHP += effect;
        if (x - px < y - py) {
            if (x > px) {
                x += 50;
            } else {
                x -= 50;
            }
        } else {
            if (y > py) {
                y += 50;
            } else {
                y -= 50;
            }
        }
    }

    /*Pre: move is called from cycle in Background, sending in player's directions for x and y, and player's x and y value
    Post: sets an image based on conditions and moves the minion x and y*/
    public void move(int pdx, int pdy, int px, int py) {
        if (px - x < py - y) { //If boss is farther than the player horizontally(X) than vertically (Y)
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
    Post: sets the direction for boss movement based on where the boss is to the player*/
    public void moveDictate(int playerX, int playerY) {
        if (playerX > x) {
            dx = 2;
        } else if (playerX < x) {
            dx = -2;
        } else {
            dx = 0;
        }

        if (playerY > y) {
            dy = 2;
        } else if (playerY < y) {
            dy = -2;
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
    Post: sets the x and y values of boss to a random number, resets bossHP, and sets boss to be visible again*/
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
        bossHP = 100;
        visible = true;
    }
}
