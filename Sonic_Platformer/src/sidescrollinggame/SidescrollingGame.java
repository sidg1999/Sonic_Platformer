package sidescrollinggame;

import bsh.Interpreter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SidescrollingGame extends JPanel implements ActionListener, KeyListener {

    double velX = 0;

    boolean finish = false;

    long start = 0, end = 0, elapsed = 0;

    double velY = 0, time = 2001, jumpAcc = -3;
 
    boolean jumped = false;

    boolean wasLeft = false;

    //global variables
    //create a timer to refresh actionlistener
    Timer tm = new Timer(17, this);

    //background image stored in bg
    ImageIcon i = new ImageIcon("C:\\Users\\malux\\Desktop\\hack\\SidescrollingGame\\src\\sidescrollinggame\\background.jpg");
    Image bg = i.getImage();

    //sonic image stored in sonicImage
    ImageIcon s = new ImageIcon("C:\\Users\\malux\\Desktop\\hack\\SidescrollingGame\\src\\sidescrollinggame\\sonic2.gif-c200");
    Image sonicImage = s.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
    Image sonicImageL = s.getImage().getScaledInstance(-50, 50, Image.SCALE_DEFAULT);

    ImageIcon f1 = new ImageIcon("C:\\Users\\malux\\Desktop\\hack\\SidescrollingGame\\src\\sidescrollinggame\\flag.png");
    Image f2 = f1.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);

    //instantiate a new character object
    Character sonic = new Character();
    int[] userReturn =new int [2];
    //x,y coodinates of sonic
    int x = 0, y = 585;
Interpreter intr;
    Background plats = new Background();

    //creates a new JPanel
    public SidescrollingGame() {
        setFocusable(true);
        addKeyListener(this);
        
         intr = new Interpreter();
        
        
        tm.start();
        
        
    }

    public static void main(String[] args) {
        //create a seperate jframe
        JFrame jf = new JFrame();
        jf.setTitle("Game");
        jf.setSize(1066, 800);

        //the main class is a jpanel. add the jpanel (main class) to the jframe. 
        SidescrollingGame t = new SidescrollingGame();
        jf.add(t);
        //set the jframe visible
        jf.setVisible(true);

    }

    //runs every 17 millis
    @Override
    public void actionPerformed(ActionEvent e) {
        velY += jumpAcc;
        y -= velY;

        int pWidth = 50, pHeight = 50;

        int platList[][] = plats.getPlatsList();
        int[] plat = plats.getPlat(0);//stores the current plat that is being checked
        for (int platNum = 0; platNum < 9; platNum++) {
            plat = platList[platNum];
            if (x + pWidth > plat[0] && plat[0] + plat[2] > x && y + pHeight > plat[1] && plat[1] + plat[3] > y) { //if true, there is a collision
                //find x and y overlaps to determine which side the player collided with                        
                int overlapX = (pWidth / 2 + plat[2] / 2) - Math.abs((x + pWidth / 2) - (plat[0] + plat[2] / 2));
                int overlapY = (pHeight / 2 + plat[3] / 2) - Math.abs((y + pHeight / 2) - (plat[1] + plat[3] / 2));

                if (overlapX >= overlapY) {//player hit top or bottom of plat
                    velY = 0;
                    if (y > plat[1]) {
                        y += overlapY;
                    } else {
                        y -= overlapY;
                    }
                } else//player hit sides of plat
                {
                    if (x < plat[0]) {
                        x -= overlapX;
                        if (velX > 0) {//if the player hit the platform from the left side, reduce its velocity to 0
                            velX = 0;
                        }
                    } else {
                        x += overlapX;
                        if (velX < 0) {//if the player hit the platform from the right side, reduce its velocity to 0
                            velX = 0;
                        }
                    }
                }
            }
        }

        x += velX;
        repaint();
    }

    //runs every 17 milis (like draw function in processing)
    @Override
    public void paintComponent(Graphics g) {

        //graphics object is used to draw
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //draws background
        g.drawImage(bg, 0, 0, this);
        

        //draws sonic
        if (velX > 0 || !wasLeft) {
            g.drawImage(sonicImage, x, y, this);
        } else if (velX < 0 || wasLeft) {
            //g.drawImage(sonicImage, x, y, this); 
            g2.drawImage(sonicImage, x + 50, y, -50, 50, this);
        }

        if (y > 800) {
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            g.setColor(Color.RED);
            g.drawString("GAME OVER, press 'enter' to try again", 200, 400);

        }

        g.drawImage(f2, 0, 170, this);

        if (x <= 90 && x >= 70) {
            if (y == 200) {
                finish = true;
                g.setFont(new Font("Arial", Font.PLAIN, 40));
                g.setColor(Color.RED);
                g.drawString("Congrats! You win! press 'enter' to try again", 200, 400);
                velX = 0;
            }
        }

    }

    
    public void horizMove(int x){
        
    }
    
    //moves sonic when key is pressed
    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        //if the use presses left key
        if (finish == true) {
            if (key == KeyEvent.VK_ENTER) {
                    x = 0;
                    y = 585;
                    velY = 0;
                    finish = false;
            }
        } else {
            if (key == KeyEvent.VK_LEFT) {
                velX = -10;
                wasLeft = true;
            }
            if (key == KeyEvent.VK_RIGHT) {
                velX = 10;
                wasLeft = false;
            }
            //the user wants to jump
            if (key == KeyEvent.VK_SPACE) {
                if (velY == 0) {
                    velY = 40;
                }
            }
            if (key == KeyEvent.VK_ENTER) {
                if (y > 800) {
                    x = 0;
                    y = 585;
                    velY = 0;
                }
            }
        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        //if the use presses left key
        if (key == KeyEvent.VK_LEFT) {
            velX = 0;
        }
        if (key == KeyEvent.VK_RIGHT) {
            velX = 0;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

}
