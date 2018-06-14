/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Background extends JPanel implements Runnable {

    private String whirl = "/Whirl.png";                 //Sets strings to image names
    private String background = "/Game Background.JPG";
    private String storyIntro = "/Story Intro.jpg";
    private String mission = "/Mission.jpg";
    private String instructions = "/Instructions.jpg";
    private String spellBar = "/SpellBar.jpg";
    private String menuscreen = "/Title Screen.jpg";
    private String death = "/Explosion.GIF";
    ImageIcon WW = new ImageIcon(this.getClass().getResource(whirl)); //Creates image icons to call on the image names
    ImageIcon ii = new ImageIcon(this.getClass().getResource(background));
    ImageIcon SB = new ImageIcon(this.getClass().getResource(spellBar));
    ImageIcon MS = new ImageIcon(this.getClass().getResource(menuscreen));
    ImageIcon SI = new ImageIcon(this.getClass().getResource(storyIntro));
    ImageIcon MI = new ImageIcon(this.getClass().getResource(mission));
    ImageIcon IS = new ImageIcon(this.getClass().getResource(instructions));
    ImageIcon DE = new ImageIcon(this.getClass().getResource(death));
    private Image imBackground; //Images to be painted
    private Image imspellBar;
    private Image menu;

    private Timer timer;    //The timer for the thread
    private Thread animator; //The animator to run the threads
    private Player player; //Creates references to the classes to be created later on in the program
    private Minion minion;
    private Boss boss;
    private Ogre ogre;
    private FireBall fireball;
    private ArrayList minions;
    private ArrayList ogres;
    private ArrayList fireballs;

    private int menuVisible = 4; //Number of this variable decides which screen of the menu is to be displayed
    private boolean gameOver = false;
    private int score = 0;
    private int manaPerc;
    private int healthPerc;

    private int animTick = 1;
    private int dx, dy;
    private int px, py;
    private int pdir;
    private String strx, stry;
    private int bgroundx, bgroundy;

    Font s = new Font("Papyrus", Font.PLAIN, 15);
    Font f = new Font("Papyrus", Font.BOLD, 30);

    private String filename = "Data.txt"; //Creates a filename to call on Data.txt, place of the highest score recorded
    private int pastScore;
    private int timeCount = 0;

    public Background() {
        addKeyListener(new TAdapter()); //Creates the TAdapter class to read for keys pressed
        setFocusable(true);
        setBackground(Color.black);
        setDoubleBuffered(true);

        gameInit();
        imBackground = ii.getImage(); //Sets the image equal to the imageicons
        imspellBar = SB.getImage();
        menu = MS.getImage();
        bgroundx = -43 - player.getX();
        bgroundy = -653 - player.getY();

        try { //Reads the score from the Data.txt
            readScore();
        } catch (IOException ex) {      // Regular IO issues
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {     // When the Data.txt score file is not present
            try {
                File file = new File(filename);
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write("0");
                writer.close();
            } catch (IOException e) { }
        }

        animator = new Thread(this); //Starts the Thread Animator
        animator.start();
    }

    /*Pre: gameInit is called from the main class of Background   
    Post: creates the player class instance, boss class instace, 30 minion class instances and 10 ogre class instances*/
    public void gameInit() {
        player = new Player(); //Creates the player
        boss = new Boss(); //Creates the boss

        minions = new ArrayList();
        for (int a = 0; a < 30; a++) {
            Minion minion = new Minion();
            minions.add(minion);
        }
        ogres = new ArrayList();
        for (int a = 0; a < 10; a++) {
            Ogre ogre = new Ogre();
            ogres.add(ogre);
        }
        fireballs = new ArrayList();
        for (int a = 0; a < 5; a++) {
            FireBall fireball = new FireBall ();
            fireballs.add(fireball);
        }
    }

    /*Pre: bgroundMove is called from Pain
    Post: changes the x and y values for background depending on where the player moved*/
    public void bgroundMove() {
        if (player.getX() > 165 && player.getX() < 750) {
            bgroundx -= 2 * dx;
        }
        if (player.getY() > -40 && player.getY() < 470) {
            bgroundy -= 2 * dy;
        }
    }

    /*Pre: paint is called from the cycle method   
    Post: draws/displays pretty much everything on the screen*/
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        if (menuVisible == 4) {
            menu = MS.getImage();
            g.drawImage(menu, 0, -20, this);
        } else if (menuVisible == 3) {
            menu = SI.getImage();
            g.drawImage(menu, 0, -20, this);
        } else if (menuVisible == 2) {
            menu = MI.getImage();
            g.drawImage(menu, 0, -20, this);
        } else if (menuVisible == 1) {
            menu = IS.getImage();
            g.drawImage(menu, 0, -20, this);
        } else {
            bgroundMove();
            g.drawImage(imBackground, bgroundx, bgroundy, this);

            player.doAnimation();
            drawPlayer(g2d);
            drawMinions(g2d);
            drawInterface(g);

        }
        if (gameOver == true) {
            gameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    /*Pre: drawPlayer is called from paint with Graphics g2d
    Post: draws the player iamge based on the player x and y*/
    public void drawPlayer(Graphics g2d) {
        if (player.whirlOn() == true) {
            g2d.drawImage(WW.getImage(), player.getX() - 85, player.getY() - 65, this);
        }
        if (player.isVisible() == true) {
            g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }
    }

    /*Pre: drawMinions is called from paint with Graphics g2d
    Post: draws all the minions, ogres, and the boss*/
    public void drawMinions(Graphics g2d) {
        Iterator it = minions.iterator();
        while (it.hasNext()) {
            Minion minion = (Minion) it.next();

            if (minion.isVisible()) {
                g2d.drawImage(minion.getImage(), minion.getX(), minion.getY(), this);
            }

            if (minion.isDying()) {
                minion.die();
            }
        }
        it = ogres.iterator();
        while (it.hasNext()) {
            Ogre ogre = (Ogre) it.next();

            if (ogre.isVisible()) {
                g2d.drawImage(ogre.getImage(), ogre.getX(), ogre.getY(), this);
            }

            if (ogre.isDying()) {
                ogre.die();
            }
        }
        it = fireballs.iterator();
        while (it.hasNext()) {
            FireBall fireball = (FireBall) it.next();

            if (fireball.isVisible()) {
                g2d.drawImage(fireball.getImage(), fireball.getX(), fireball.getY(), this);
            }

            if (fireball.isDying()) {
                fireball.die();
            }
        }
        g2d.drawImage(boss.getImage(), boss.getX(), boss.getY(), this);

        g2d.drawImage(imspellBar, 400 - 133, 600 - 110, this);
    }

    /*Pre: drawInterface is called from paint with Graphics g
    Post: draws the health and mana bars, the text displaying the health and mana, the score, and the timer*/
    public void drawInterface(Graphics g) {

        g.setColor(Color.red);
        healthPerc = (int) ((player.health() / player.totalHealth() * 100) * 2.67);
        g.fillRect(0, 525, healthPerc, 50);
        g.setFont(f);
        g.setColor(Color.white);
        String temp = player.health() + "/" + player.totalHealth();
        g.drawString(temp, 25, 555);

        g.setColor(Color.blue);
        manaPerc = (int) ((player.mana() / player.totalMana() * 100) * 2.67);
        g.fillRect(533, 525, manaPerc, 50); //267
        g.setColor(Color.white);
        temp = (int)player.mana() + "/" + player.totalMana();
        g.drawString(temp, 575, 555);

        g.setFont(s);
        g.drawString("Score: " + score, 0, 15);
        g.drawString("Time: " + (timeCount / 25), 0, 40);

        strx = "X: " + String.valueOf(player.getX());
        stry = "Y: " + String.valueOf(player.getY());
        g.drawString(strx, 690, 10);
        g.drawString(stry, 740, 10);

        g.setColor(Color.GRAY);
        g.fillRect(0, 520, 267, 5);
        g.fillRect(533, 520, 267, 5);
    }

    /*Pre: gameOver is called from paint with Graphics g
    Post: draws the game over screen, with score, time, and past high score with whether or not you beat it*/
    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(0, 0, 800, 600);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.white);
        String temp = "Gameover... You failed to defend the world.";
        g.drawString(temp, 250, 200);
        temp = "Your score was: " + score;
        g.drawString(temp, 250, 300);
        temp = "You lasted for a pathetic total of " + (timeCount / 25) + " seconds.";
        g.drawString(temp, 250, 315);
        if (score > pastScore) {
            temp = "My god... you have beaten the legendary high score...";
            g.drawString(temp, 250, 330);
        }
        temp = "The highest score before this...";
        g.drawString(temp, 250, 370);

        String strI = String.valueOf(pastScore);
        g.drawString(strI, 250, 400);
    }

    /*Pre: cycle is called from run
    Post: drives all the class to class interactions, including unit collision*/
    public void cycle() {
        if (menuVisible <= 0) {
            timeCount += 1;

            if (animTick == 1) {
                animTick = 2;
            } else {
                animTick = 1;
            }
            if (player.health() <= 0) {
                player.die();
            }
            player.getTick(animTick);
            player.act();
            player.manaEffect(0.0005);
            dx = player.dx();
            dy = player.dy();
            px = player.getX() + 10;
            py = player.getY() + 10;
            pdir = player.direction();

            Iterator it = minions.iterator();
            while (it.hasNext()) {
                Minion minion = (Minion) it.next();

                if (minion.isVisible() == false) {
                    minion.respawn(px, py);
                }

                minion.setTick(animTick);
                minion.moveDictate(px, py);
                minion.move(dx, dy, px, py);

                if (minion.getX() > px - 20 && minion.getX() < px + 20
                        && minion.getY() > py - 20 && minion.getY() < py + 20
                        && minion.isVisible() == true && player.defenseOn() == false) {
                    player.healthEffect(-0.01);

                }

                if (minion.getX() > px - 30 && minion.getX() < px + 30
                        && minion.getY() > py - 30 && minion.getY() < py + 30
                        && player.attackOn() == true) {
                    minion.destroyed();
                    score += 10;
                }

                if (minion.getX() > px - 100 && minion.getX() < px + 100
                        && minion.getY() > py - 100 && minion.getY() < py + 100
                        && player.defenseOn() == true) {
                    minion.bumpEffect(dx,dy,pdir);
                }

                if (minion.getX() > px - 100 && minion.getX() < px + 100
                        && minion.getY() > py - 100 && minion.getY() < py + 100
                        && player.whirlOn() == true) {
                    minion.destroyed();
                    score += 10;
                }

                if (minion.isDying()) {
                    minion.die();
                }
            }

            it = fireballs.iterator();
            while (it.hasNext()) {
                FireBall fireball = (FireBall) it.next();

                if (fireball.isVisible() == false) {
                    fireball.respawn(px, py);
                }

                fireball.moveDictate(px, py);
                fireball.move(dx, dy);

                if (fireball.getX() > px - 10 && fireball.getX() < px + 10
                        && fireball.getY() > py - 10 && fireball.getY() < py + 10
                        && fireball.isVisible() == true) {
                    fireball.destroyed();
                    if (player.defenseOn() == false) {
                        player.healthEffect(-0.05);
                    }
                }

                if (fireball.getX() > px - 30 && fireball.getX() < px + 30
                        && fireball.getY() > py - 30 && fireball.getY() < py + 30
                        && player.attackOn() == true) {
                    fireball.destroyed();
                    score += 20;
                }

                if (fireball.getX() > px - 100 && fireball.getX() < px + 100
                        && fireball.getY() > py - 100 && fireball.getY() < py + 100
                        && player.whirlOn() == true) {
                    fireball.destroyed();
                    score += 20;
                }

                if (fireball.isDying()) {
                    fireball.die();
                }
            }

            it = ogres.iterator();
            while (it.hasNext()) {
                Ogre ogre = (Ogre) it.next();

                if (ogre.isVisible() == false) {
                    ogre.respawn(px, py);
                }

                ogre.setTick(animTick);
                ogre.moveDictate(px, py);
                ogre.move(dx, dy, px, py);

                if (ogre.getX() > px - 20 && ogre.getX() < px + 20
                        && ogre.getY() > py - 20 && ogre.getY() < py + 20
                        && ogre.isVisible() == true && player.defenseOn() == false) {
                    player.healthEffect(-0.05);

                }

                if (ogre.getX() > px - 30 && ogre.getX() < px + 30
                        && ogre.getY() > py - 30 && ogre.getY() < py + 30
                        && player.attackOn() == true) {
                    ogre.HPEffect(-1, dx, dy, pdir);
                }

                if (ogre.getX() > px - 100 && ogre.getX() < px + 100
                        && ogre.getY() > py - 100 && ogre.getY() < py + 100
                        && player.defenseOn() == true) {
                    ogre.HPEffect(0, dx, dy, pdir);
                }

                if (ogre.getX() > px - 100 && ogre.getX() < px + 100
                        && ogre.getY() > py - 100 && ogre.getY() < py + 100
                        && player.whirlOn() == true) {
                    ogre.HPEffect(-5, dx, dy, pdir);
                }

                if (ogre.getHP() < 1) {
                    ogre.destroyed();
                    score += 50;
                }

                if (ogre.isDying()) {
                    ogre.die();
                }
            }

            if (boss.isVisible() == false) {
                boss.respawn(px, py);
            }
            boss.setTick(animTick);
            boss.moveDictate(px, py);
            boss.move(dx, dy, px, py);

            if (boss.getX() > px && boss.getX() < px + 60
                    && boss.getY() > py - 30 && boss.getY() < py + 10
                    && boss.isVisible() == true && player.defenseOn() == false && player.whirlOn() == false) {
                player.healthEffect(-0.05);

            }

            if (boss.getX() > px - 50 && boss.getX() < px + 10
                    && boss.getY() > py - 40 && boss.getY() < py + 10
                    && player.attackOn() == true) {
                boss.HPEffect(-1, px, py, pdir);
            }

            if (boss.getX() > px - 120 && boss.getX() < px + 110
                    && boss.getY() > py - 110 && boss.getY() < py + 110
                    && player.defenseOn() == true) {
                boss.HPEffect(0, px, py, pdir);
            }

            if (boss.getX() > px - 120 && boss.getX() < px + 110
                    && boss.getY() > py - 110 && boss.getY() < py + 110
                    && player.whirlOn() == true) {
                boss.HPEffect(-5, px, py, pdir);
            }

            if (boss.getHP() < 1) {
                boss.destroyed();
                score += 1000;
            }

            if (boss.isDying()) {
                boss.die();
            }

            if (px > 620 && px < 670 && py > 440 && py < 470) {
                if (score > 0 && player.health() < player.totalHealth() || score > 0 && player.mana() < player.totalMana()) {
                    player.healthEffect(0.01);
                    player.manaEffect(0.01);
                    score -= 10;
                }
            }

            if (player.health() <= 0) {
                gameOver = true;
                try {
                    if (score > pastScore) {
                        writeScore();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);

                }
            }

        }
        repaint();
    }

    /*Pre: is automatically runned with the Runnable Implementation
    Post: runs the threads with a given speed, calling cycle() with each loop*/
    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while (!gameOver) {
            //while (menuVisible == false) {
            cycle();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = 50 - timeDiff;  // num is delay
            if (sleep < 0) {
                sleep = 2;
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            beforeTime = System.currentTimeMillis();
            //}
        }
    }

    /*Pre: writeScore is called from cycle method if the score is larger than the past high score
    Post: writes out the new high score into Data.txt*/
    public void writeScore() throws IOException {
        PrintWriter fileOut = new PrintWriter(new FileWriter(filename));
        fileOut.print(score);
        fileOut.close();
    }

    /*Pre: readScore is called from the main method of Background
    Post: reads the score from Data.txt and stores the value to pastScore*/
    public void readScore() throws IOException {
        BufferedReader readFile = new BufferedReader(new FileReader(filename));
        //BufferedReader readFile = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(filename)));
        pastScore = Integer.parseInt(readFile.readLine());
    }

    /*public void actionPerformed(ActionEvent e) {            unused actionPerformed
    }*/

    /*Pre: TAdapter is called from main method of Background
    Post: creates the keyReleased and keyPressed listeners*/
    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) { //Pre: called when a key pressed is released
            player.keyReleased(e);            //Post: calls keyReleased method in Player with the key released
        }

        public void keyPressed(KeyEvent e) { //Pre: called when a key is pressed
            menuVisible -= 1;                //Post: calls keyPressed method in Player with the key pressed
            if (menuVisible <= 0) {
                player.keyPressed(e);
            }
        }
    }
}
