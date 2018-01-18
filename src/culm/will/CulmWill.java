package culm.will;//package name

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CulmWill extends JPanel implements ActionListener, KeyListener {

    private Graphics dbg;
    Graphics2D draw;
    Image dbImage;
    Timer frame, count;
    boolean press[] = {false, false, false, false, false};
    double pos = 0;
    ArrayList<Integer> scores = new ArrayList<Integer>();
    int score = 0, screen = 0, menu = 2;
    ArrayList<Aircraft> planes = new ArrayList<Aircraft>();

    public CulmWill() {
        frame = new Timer(30, this); //sets the delay between frames
        frame.start(); /// starts the timer

        count = new Timer(30, new ActionListener() { // this will run the code inside ever 30ms
            @Override
            public void actionPerformed(ActionEvent e) {

                /// determine how long the image of the plane accelerates               
                if (planes.size() < 1 || Math.random() * 6000 < 10) { //if its off screen  
                    planes.add(new Aircraft(Math.random() * Math.PI * 2));
                }
                for (int a = 0; a < planes.size(); a++) {
                    if (planes.get(a).getColour() != "cringegrid.gif") {
                        planes.get(a).grow(true);
                    } else {
                        planes.get(a).grow(false);
                    }
                    if (planes.get(a).getAge() > 100) { //if its off screen  
                        planes.remove(a);
                    }
                }
            }
        });

        try {
            FileReader fr = new FileReader("scores.txt");
            BufferedReader br = new BufferedReader(fr); //reads map from text file
            for (int i = 0; i < 10; i++) {
                scores.add(Integer.parseInt(br.readLine()));
            }
            fr.close();
            br.close();
        } catch (IOException a) {
            System.out.println("Couldn't Load");
        }

        addKeyListener(this); //checks if keys are pressed
    }

    public static void main(String[] args) {
        CulmWill panel = new CulmWill(); /// create new CulmWill object panel
        JFrame f = new JFrame("Game"); /// create JFrame object j with titlename "Game"
        f.setResizable(false); /// make the window non resizeable
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); /// end program when user closes window
        f.add(panel); /// add the CulmWill panel object to the JFrame f object
        //f.setUndecorated(true); //Takes away the toolBar
        f.setSize(800, 600); /// Set the size to 800 width by 600 height
        f.setVisible(true); /// set the visibility to true
        f.setLocationRelativeTo(null); /// center the window

    }

    public void paint(Graphics g) { //double buffer
        dbImage = createImage(getWidth(), getHeight()); /// set Image dbImage to an image with 794 by 571 
        dbg = dbImage.getGraphics(); /// set the Graphics dbg to graphics of dbImage
        paintComponent(dbg); /// call paint component method with paramater dbg (graphics of dbImage

        g.drawImage(dbImage, 0, 0, this); // call Graphics method on g to drawImage dbImage at top left corner        
    }

    public void paintComponent(Graphics g) {
        draw = (Graphics2D) g; /// Graphics2D draw = Graphics g casted as a Graphics2D object
        draw.setFont(new Font("Consolas", Font.PLAIN, 20)); /// set the font for Graphics g to consolas
        switch (screen) {
            case (0): //Main Menu
                draw.drawString("Title Screen", getWidth() / 2 - (g.getFontMetrics().stringWidth("Title Screen")) / 2, getHeight() / 4 - 50);
                draw.drawString("High Scores", getWidth() / 2 - (g.getFontMetrics().stringWidth("High Scores")) / 2, getHeight() / 2 - 50);
                draw.drawString("Play", getWidth() / 2 - (g.getFontMetrics().stringWidth("Play")) / 2, getHeight() / 4 * 3 - 50);
                draw.drawString("Exit", getWidth() / 2 - (g.getFontMetrics().stringWidth("Exit")) / 2, getHeight() - 50);
                draw.drawRect(getWidth() / 2 - (g.getFontMetrics().stringWidth("Title Screen")) / 2 - 5, getHeight() / 4 * menu - 68, 140, 20);

                if (press[2] == true && menu > 2) {
                    menu -= 1;
                    press[2] = false;
                } else if (press[3] == true && menu < 4) {
                    menu += 1;
                    press[3] = false;
                } else if (press[4] == true) {
                    press[4] = false;
                    switch (menu) {
                        case (2):
                            screen = 1;
                            break;
                        case (3):
                            screen = 2;
                            score = 0;
                            count.start(); //starts the timer
                            break;
                        case (4):
                            try {
                                FileWriter fw = new FileWriter("scores.txt");//set place to write to in "Files"
                                PrintWriter pw = new PrintWriter(fw); //starts writing
                                for (int i = 0; i < 10; i++) {
                                    pw.println(scores.get(i));
                                }
                                pw.close(); //stop writing
                            } catch (IOException a) {
                                System.out.println("ERROR");
                            }
                            System.exit(0);
                            break;
                    }
                }
                break;
            case (1): //highscore
                draw.drawString("HIGHSCORES", 100, 30);
                for (int i = 0; i < 10; i++) {
                    draw.drawString(scores.get(i) + "", 100, 60 + 30 * i);
                }
                break;
            case (2): // Game
                /// ALL THE CODE BELOW WILL ROTATE        
                draw.rotate(pos, 400, 0); /// rotate Graphics2D draw in circle                
                draw.drawImage(new ImageIcon("space.png").getImage(), -550, -950, getWidth() * 2 + 300, getWidth() * 2 + 300, this); /// draw the background image

                for (int a = 0; a < planes.size(); a++) {
                    draw.drawImage(new ImageIcon(planes.get(a).getColour()).getImage(), planes.get(a).getPosistionX() - planes.get(a).getSizeX() / 2, planes.get(a).getPosistionY() - planes.get(a).getSizeY() / 2, planes.get(a).getSizeX(), planes.get(a).getSizeY(), this);
                }
                draw.rotate(-pos, 400, 0); //all the code below wont rotate

                /// ALL THE CODE BELOW WILL NOT ROTATE   
                draw.drawString("SCORE: " + score, 300, 30); /// draw a string that lists the score at 300 300
                draw.drawImage(new ImageIcon("cockpit.png").getImage(), 000, 200, 800, 400, this);

                draw.drawLine(400, 0, (int) (Math.cos(-0.15 + (Math.PI / 2)) * 800) + 400, (int) (Math.sin(-0.15 + (Math.PI / 2)) * 800));
                draw.drawLine(400, 0, (int) (Math.cos(0.15 + (Math.PI / 2)) * 800) + 400, (int) (Math.sin(0.15 + (Math.PI / 2)) * 800));

                if (press[0] == true) { //checks which key is being pressed
                    pos -= .04; //moves left
                    if (pos <= 0) {
                        pos = Math.PI * 2;
                    }
                } else if (press[1] == true) {
                    pos += .04; //moves right
                    if (pos >= Math.PI * 2) {
                        pos = 0;
                    }
                }

                if (press[2] == true) { //checks which key is being pressed
                    //shoot             
                    for (int a = 0; a < planes.size(); a++) {
                        if (pos <= planes.get(a).getAngle() + 0.15 && pos >= planes.get(a).getAngle() - 0.15 && (planes.get(a).getColour() != "cringegrid.gif")) { //checks if plane is within the radian range
                            score++;
                            planes.get(a).setColour("cringegrid.gif");
                        }
                    }
                } else if (press[3] == true) {
                    //power
                }
                for (int a = 0; a < planes.size(); a++) {
                    if (planes.get(a).getAge() > 99 && planes.get(a).getAge() < 110 && pos <= planes.get(a).getAngle() + 0.15 && pos >= planes.get(a).getAngle() - 0.15) { //if too close gameover
                        screen = 3;
                        score = 0;
                        System.out.println("GAMEOVER");
                    }
                }
                break;

            case (3): //add score
                scores.add(score);
                Collections.sort(scores, Collections.reverseOrder());
                screen = 4;
                break;
            case (4)://Game over sceen
                draw.drawString("GAME OVER", 300, 32);
                draw.drawString("SCORE: " + score, 250, 132);
                break;
        }
        super.paintComponents(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            press[0] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            press[1] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            press[2] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            press[3] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            press[4] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (screen == 0) {
                System.exit(1);
            } else if (screen == 2) {
                screen = 3;
            } else {
                screen = 0;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            press[0] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            press[1] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            press[2] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            press[3] = false;

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        requestFocus();
        setFocusTraversalKeysEnabled(false);
    }
}
