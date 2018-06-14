/**
 * Defines the behaviour and input of the player.
 */

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player extends Sprite {

    private final int START_X = 360;
    private final int START_Y = 260;
    private final String PlayerDown1 = "/Hero Down 1.png"; //Declares all image strings and image icons
    private final String PlayerDown2 = "/Hero Down 2.png";
    private final String PlayerUp1 = "/Hero Up 1.png";
    private final String PlayerUp2 = "/Hero Up 2.png";
    private final String PlayerLeft1 = "/Hero Left 1.png";
    private final String PlayerLeft2 = "/Hero Left 2.png";
    private final String PlayerRight1 = "/Hero Right 1.png";
    private final String PlayerRight2 = "/Hero Right 2.png";
    private final String PlayerDownAttk1 = "/Hero Down Slash 1.png";
    private final String PlayerDownAttk2 = "/Hero Down Slash 2.png";
    private final String PlayerUpAttk1 = "/Hero Up Slash 1.png";
    private final String PlayerUpAttk2 = "/Hero Up Slash 2.png";
    private final String PlayerLeftAttk1 = "/Hero Left Slash 1.png";
    private final String PlayerLeftAttk2 = "/Hero Left Slash 2.png";
    private final String PlayerRightAttk1 = "/Hero Right Slash 1.png";
    private final String PlayerRightAttk2 = "/Hero Right Slash 2.png";
    private final String PlayerDefenseDown = "/Hero Defense Down.png";
    private final String PlayerDefenseUp = "/Hero Defense Up.png";
    private final String PlayerDefenseLeft = "/Hero Defense Left.png";
    private final String PlayerDefenseRight = "/Hero Defense Right.png";
    ImageIcon D1 = new ImageIcon(this.getClass().getResource(PlayerDown1));
    ImageIcon D2 = new ImageIcon(this.getClass().getResource(PlayerDown2));
    ImageIcon U1 = new ImageIcon(this.getClass().getResource(PlayerUp1));
    ImageIcon U2 = new ImageIcon(this.getClass().getResource(PlayerUp2));
    ImageIcon L1 = new ImageIcon(this.getClass().getResource(PlayerLeft1));
    ImageIcon L2 = new ImageIcon(this.getClass().getResource(PlayerLeft2));
    ImageIcon R1 = new ImageIcon(this.getClass().getResource(PlayerRight1));
    ImageIcon R2 = new ImageIcon(this.getClass().getResource(PlayerRight2));
    ImageIcon DA1 = new ImageIcon(this.getClass().getResource(PlayerDownAttk1));
    ImageIcon DA2 = new ImageIcon(this.getClass().getResource(PlayerDownAttk2));
    ImageIcon UA1 = new ImageIcon(this.getClass().getResource(PlayerUpAttk1));
    ImageIcon UA2 = new ImageIcon(this.getClass().getResource(PlayerUpAttk2));
    ImageIcon LA1 = new ImageIcon(this.getClass().getResource(PlayerLeftAttk1));
    ImageIcon LA2 = new ImageIcon(this.getClass().getResource(PlayerLeftAttk2));
    ImageIcon RA1 = new ImageIcon(this.getClass().getResource(PlayerRightAttk1));
    ImageIcon RA2 = new ImageIcon(this.getClass().getResource(PlayerRightAttk2));
    ImageIcon DD = new ImageIcon(this.getClass().getResource(PlayerDefenseDown));
    ImageIcon UD = new ImageIcon(this.getClass().getResource(PlayerDefenseUp));
    ImageIcon LD = new ImageIcon(this.getClass().getResource(PlayerDefenseLeft));
    ImageIcon RD = new ImageIcon(this.getClass().getResource(PlayerDefenseRight));
    private boolean attackOn = false;     //Booleans for whether an action is on/off
    private boolean defenseOn = false;
    private boolean whirlOn = false;
    private int direction = 1; //Direction of player. 1 = down, 2 = up, 3 = left, 4 = right
    private double totalHealth = 100; //Declares and sets the value of the player's maximum health
    private double health = totalHealth; //Declares the active health and sets it to the starting maximum
    private double totalMana = 100; //Declares and sets the value of the player's maximum mana
    private double mana = 100; //Declares the active mana and sets it to the starting maximum
    final int animDelay = 2;
    int animCount = animDelay;
    int animPos = 0;
    int animDir = 1;
    private int animTick; //Uses integer numbers to set which sprite image is to be shown

    /*Pre: class created from Background class
     Post: sets the starting image and starting XY values */
    public Player() {
        setImage(D1.getImage());
        setX(START_X);
        setY(START_Y);
    }

    /*Pre: act is called from Background    
     Post: the value of x and y stored in Player changes based on direction, but is restricted by boundaries*/
    public void act() {
        x += dx;
        if (x < 165) { 
            x = 165;
        } else if (x > 750) {
            x = 750;
        } else {
            x += dx;
        }
        if (y > 470) {
            y = 470;
        } else if (y < -40) {
            y = -40;
        } else {
            y += dy;
        }

    }

    /*Pre: healthEffect is called from the cycle method in Background in various cases
     Post: adds or takes away from the health variable, restricted by totalHealth and health needing to be above 0*/
    public void healthEffect(double effect) {
        if (health > 0 && effect < 0 || health < totalHealth && effect > 0) {
            health += effect * totalHealth;
        }
    }

    /*Pre: manaEffect is called from the cycle method in Background in various cases
     Post: adds or takes away from the mana variable, restricted by the totalMana (base maximum mana)*/
    public void manaEffect(double effect) {
        if (mana < totalMana) {
            mana += effect * totalMana;
        }
    }
    /*Pre: totalHealth is called multiple times in Background during cycling    
    Post: returns the value of totalHealth to Background*/
    public double totalHealth() {
        return (totalHealth);
    }

        /*Pre: health is called multiple times in Background during cycling    
    Post: returns the value of health to Background*/
    public double health() {
        return (health);
    }

        /*Pre: totalMana is called multiple times in Background during cycling    
    Post: returns the value of totalMana to Background*/
    public double totalMana() {
        return (totalMana);
    }

        /*Pre: mana is called multiple times in Background during cycling    
    Post: returns the value of mana to Background*/
    public double mana() {
        return (mana);
    }

    /*Pre: class is called in multiple instances from Background
     Post: returns the attackOn boolean for usage in Background class*/
    public boolean attackOn() {
        return attackOn;
    }

    /*Pre: class is called in multiple instances from Background
     Post: returns the attackOn boolean for usage in Background class*/
    public boolean defenseOn() {
        return defenseOn;
    }

    /*Pre: class is called in multiple instances from Background
     Post: returns the attackOn boolean for usage in Background class*/
    public boolean whirlOn() {
        return whirlOn;
    }

    /*Pre: class is called in cycle() method in Background class
     Post: returns Player class's stored direction value to Background's call*/
    public int direction() {
        return direction;
    }

    /*Pre: getTick is called from Background class and the animTick integer is passed in
     Post: sets Player's animTick integer equal to the passed in animTick from Background*/
    public void getTick(int num) {
        animTick = num;
    }

    /*Pre: dx is called from various sources in Background   
     Post: returns the value of dx stored in player to Background*/
    public int dx() {
        return (dx);
    }
    
    /*Pre: dy is called from varius sources in Background
     Post: returns the value of dy stored in player to Background*/
    public int dy() {
        return (dy);
    }

    public void doAnimation() {
        animCount--;
        if (animCount <= 0) {
            animCount = animDelay;
            animPos = animPos + animDir;
            if (animPos == (animCount - 1) || animPos == 0) {
                animDir = -animDir;
            }
        }
    }

    /*Pre: called from ActionListener after a down key is hit
    Post: sets Player image to a certain image based on the animTick*/
    private void drawDown() {
        switch (animTick) {
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
    }

        /*Pre: called from ActionListener after a up key is hit
    Post: sets Player image to a certain image based on the animTick*/
    private void drawUp() {
        switch (animTick) {
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

        /*Pre: called from ActionListener after a left key is hit
    Post: sets Player image to a certain image based on the animTick*/
    private void drawLeft() {
        switch (animTick) {
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

        /*Pre: called from ActionListener after a right key is hit
    Post: sets Player image to a certain image based on the animTick*/
    private void drawRight() {
        switch (animTick) {
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
    }

        /*Pre: called from ActionListener after a Q (attacK) key is hit
    Post: sets Player image to a certain attack image based on direction and animTick*/
    private void drawAttack() {
        switch (direction) {
            case 1:
                if (animTick == 1) {
                    setImage(DA1.getImage());
                } else {
                    setImage(DA2.getImage());
                }
                break;
            case 2:
                if (animTick == 1) {
                    setImage(UA1.getImage());
                } else {
                    setImage(UA2.getImage());
                }
                break;
            case 3:
                if (animTick == 1) {
                    setImage(LA1.getImage());
                } else {
                    setImage(LA2.getImage());
                }
                break;
            case 4:
                if (animTick == 1) {
                    setImage(RA1.getImage());
                } else {
                    setImage(RA2.getImage());
                }
                break;
            default:
                setImage(R1.getImage());
                break;
        }
    }

        /*Pre: called from ActionListener after W (defense) key is hit
    Post: sets Player image to a certain defense image based on the direction and animTick*/
    private void drawDefense() {
        switch (direction) {
            case 1:
                setImage(DD.getImage());
                break;
            case 2:
                setImage(UD.getImage());
                break;
            case 3:
                setImage(LD.getImage());
                break;
            case 4:
                setImage(RD.getImage());
                break;
            default:
                setImage(DD.getImage());
                break;
        }
    }

    /*Pre: setIdle is called when the ActionListener sees that a key is released after pressed
    Post: sets Player class image to an idle image based on the direction*/
    private void setIdle() {
        if (direction == 1) {
            setImage(D1.getImage());
        } else if (direction == 2) {
            setImage(U1.getImage());
        } else if (direction == 3) {
            setImage(L1.getImage());
        } else {
            setImage(R1.getImage());
        }
    }

    /*Pre: moveSuppressActions is called from the keylistener after a button is pressed
    Post: sets the action variables attackOn, defenseOn, and whirlOn to false*/
    public void moveSuppressActions() {
        attackOn = false;
        defenseOn = false;
        whirlOn = false;
    }

    /*Pre: keyPressed is called from TAdapter class in Background
    Post: changes certain variables and calls on certain draw classes based on key pressed*/
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_DOWN) {
            moveSuppressActions();
            dy = 2;
            drawDown();
            direction = 1;
        }

        if (key == KeyEvent.VK_UP) {
            moveSuppressActions();
            dy = -2;
            drawUp();
            direction = 2;
        }

        if (key == KeyEvent.VK_LEFT) {
            moveSuppressActions();
            dx = - 2;
            drawLeft();
            direction = 3;
        }

        if (key == KeyEvent.VK_RIGHT) {
            moveSuppressActions();
            dx = 2;
            drawRight();
            direction = 4;
        }

        if (key == KeyEvent.VK_Q) {
            attackOn = true;
            drawAttack();
        }

        if (key == KeyEvent.VK_W) {
            if (mana > 0) {
                defenseOn = true;
                mana -= 1;
                drawDefense();

            } else {
                defenseOn = false;
            }
        }

        if (key == KeyEvent.VK_E) {
            if (mana > 4) {
                whirlOn = true;
                mana -= 5;
            } else {
                whirlOn = false;
            }
        }

        if (key == KeyEvent.VK_R) {
            if (mana >= 50) {
                mana -= 50;
                totalHealth += 50;
                health = totalHealth;
                totalMana += 50;
            }
        }
    }

    /*Pre: keyReleased is called from TAdapter clas in Background, following a key being released
    Post: turns off certain values and can call setIdle based on which key was released*/
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_Q) {
            attackOn = false;
            setIdle();
        }

        if (key == KeyEvent.VK_W) {
            defenseOn = false;
            setIdle();
        }
        if (key == KeyEvent.VK_E) {
            whirlOn = false;
        }
    }
}
