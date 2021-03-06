package culm.will;//package name

import java.awt.BasicStroke;
import java.awt.Color;
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
    Timer frame, count, fire;
    int press[] = {0, 0, 0, 0, 0};
    double pos = 0;
    ArrayList<Integer> scores = new ArrayList<Integer>();
    int score = 0, screen = 0, menu = 3;
    ArrayList<Aircraft> planes = new ArrayList<Aircraft>();
    Sound play;
    Color colour;

    /**
     * CulmWill constructor
     *
     * Creates CulmWill object as well as other necessary startup processes such
     * as setting up the timer, filereader, and tracklist among others
     */
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
                    if (planes.get(a).getColour() != "resources//Explode_fire_1.gif") {
                        planes.get(a).grow(true);
                    } else {
                        planes.get(a).grow(false);
                    }
                    if (planes.get(a).getAge() > 150) { //if its off screen  
                        planes.remove(a);
                    }
                }
            }
        });
        fire = new Timer(500, new ActionListener() { // this will run the code inside ever 30ms
            @Override
            public void actionPerformed(ActionEvent e) {
                if (press[2] == 1) {
                    press[2] = 2;
                } else {
                    press[2] = 0;
                    fire.stop();
                }
            }
        });
        try {
            FileReader fr = new FileReader("resources//scores.txt");
            BufferedReader br = new BufferedReader(fr); //reads map from text file
            for (int i = 0; i < 10; i++) {
                scores.add(Integer.parseInt(br.readLine()));
            }
            fr.close();
            br.close();
        } catch (IOException a) {
            System.out.println("Couldn't Load");
        }

        String[] rawr = {"songGreenCalx.wav", "songBlackjack.wav", "songWeHaveExplosives.wav", "songBlackdice.wav", "songGuns.wav", "songDeathGrips2.wav", "songBreathe.wav", "songPluto.wav", "songRockNroll.wav"}; // create a new File object with the directory of the audio as the parameter
        play = new Sound(rawr);

        addKeyListener(this); //checks if keys are pressed
        colour = new Color((int) (Math.random() * 0x1000000));
    }

    /**
     * Main class for project that calls constructors and creates jRrame
     *
     * @param args
     */
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

    /**
     * paint method f Double buffers meaning it writes it offscreen and then
     * writes it again and compares. consult with Nathan for what that means he
     * can elaborate.
     *
     * @param g
     */
    public void paint(Graphics g) { //double buffer
        dbImage = createImage(getWidth(), getHeight()); /// set Image dbImage to an image with 794 by 571 
        dbg = dbImage.getGraphics(); /// set the Graphics dbg to graphics of dbImage
        paintComponent(dbg); /// call paint component method with paramater dbg (graphics of dbImage

        g.drawImage(dbImage, 0, 0, this); // call Graphics method on g to drawImage dbImage at top left corner        
    }

    /**
     * paintComponent
     *
     * Paints to the screen.
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        draw = (Graphics2D) g; /// Graphics2D draw = Graphics g casted as a Graphics2D object
        draw.setFont(new Font("Consolas", Font.PLAIN, 20)); /// set the font for Graphics g to consolas
        switch (screen) {
            case (0): //Main Menu
                draw.setXORMode(colour); // set XOR mode with pinkf
                draw.drawImage(new ImageIcon("resources//spaceM.gif").getImage(), 0, 0, getWidth(), getWidth(), this);
                draw.drawString("ProtoBlaster 2070: Xortors Prime", getWidth() / 2 - (g.getFontMetrics().stringWidth("Title Screen")) / 2, getHeight() / 4 - 50);
                draw.drawString("Flight Records", getWidth() / 2 - (g.getFontMetrics().stringWidth("Title Screen")) / 2, getHeight() / 2 - 52);
                draw.drawString("Engage", getWidth() / 2 - (g.getFontMetrics().stringWidth("Title Screen")) / 2, getHeight() / 4 * 3 - 53);
                draw.drawString("Eject", getWidth() / 2 - (g.getFontMetrics().stringWidth("Title Screen")) / 2, getHeight() - 55);
                draw.drawRect(getWidth() / 2 - 100, getHeight() / 4 * menu - 68, 20, 20);
                draw.dispose();

                if (press[2] == 1 && menu > 2) {
                    menu -= 1;
                    press[2] = 0;
                } else if (press[3] == 1 && menu < 4) {
                    menu += 1;
                } else if (press[4] == 1) {
                    press[4] = 0;
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
                                FileWriter fw = new FileWriter("resources//scores.txt");//set place to write to in "Files"
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
                press[3] = 0;
                break;
            case (1): //highscore
                draw.setXORMode(Color.green); // set XOR mode with pinkf
                draw.drawImage(new ImageIcon("resources//boppingHead.gif").getImage(), 0, 0, getWidth(), getWidth(), this);
                draw.drawString("HIGHSCORES", 100, 30);
                for (int i = 0; i < 10; i++) {
                    draw.drawString(scores.get(i) + "", 100, 60 + 30 * i);
                }
                draw.dispose();
                break;
            case (2): // Game
                /// ALL THE CODE BELOW WILL ROTATE        
                draw.rotate(pos, 400, 0); /// rotate Graphics2D draw in circle                
                draw.drawImage(new ImageIcon("resources//spaceM.gif").getImage(), -550, -950, getWidth() * 2 + 300, getWidth() * 2 + 300, this); /// draw the background image

                for (int a = 0; a < planes.size(); a++) {
                    draw.drawImage(new ImageIcon(planes.get(a).getColour()).getImage(), planes.get(a).getPosistionX() - planes.get(a).getSizeX() / 2, planes.get(a).getPosistionY() - planes.get(a).getSizeY() / 2, planes.get(a).getSizeX(), planes.get(a).getSizeY(), this);
                }
                draw.rotate(-pos, 400, 0); //all the code below wont rotate

                /// ALL THE CODE BELOW WILL NOT ROTATE 
                if (press[2] == 1) {
                    draw.setXORMode(Color.green);
                }
                draw.drawImage(new ImageIcon("resources//cockControls.gif").getImage(), 000, 200, 800, 400, this);
                draw.drawImage(new ImageIcon("resources//cockpit.png").getImage(), 000, 200, 800, 400, this);

                if (press[0] == 1) { //checks which key is being pressed
                    pos -= .04; //moves left
                    if (pos <= 0) {
                        pos = Math.PI * 2;
                    }
                } else if (press[1] == 1) {
                    pos += .04; //moves right
                    if (pos >= Math.PI * 2) {
                        pos = 0;
                    }
                }

                if (press[2] == 1) { //checks which key is being pressed
                    //Shoot             
                    play = new Sound("light-saber-on.wav");
                    draw.setColor(Color.white);
                    draw.setStroke(new BasicStroke(2));
                    draw.drawLine(400, 0, (int) (Math.cos(-0.15 + (Math.PI / 2)) * 400) + 400, (int) (Math.sin(-0.15 + (Math.PI / 2)) * 400));
                    draw.drawLine(400, 0, (int) (Math.cos(0.15 + (Math.PI / 2)) * 400) + 400, (int) (Math.sin(0.15 + (Math.PI / 2)) * 400));
                    draw.dispose();
                    for (int a = 0; a < planes.size(); a++) {
                        if (pos <= planes.get(a).getAngle() + 0.15 && pos >= planes.get(a).getAngle() - 0.15 && (planes.get(a).getColour() != "resources//Explode_fire_1.gif")) { //checks if plane is within the radian range
                            score++;
                            planes.get(a).setColour("resources//Explode_fire_1.gif");
                            play = new Sound("resources//explode.wav");
                        }
                    }
                }

                /// for each plane in the plane arrayList
                for (int a = 0; a < planes.size(); a++) {
                    /// if statement to tell when the game is over and end it                          
                    if (planes.get(a).getColour() != "resources//Explode_fire_1.gif" && /// if the "a"th plane in the planes array is the explosion animation
                            planes.get(a).getAge() > 149 && /// and if that that plane's age is greater than 99
                            planes.get(a).getAge() < 160 && /// and if that age is less than 110
                            pos <= planes.get(a).getAngle() + 0.5 && /// and if the angle of that ship is less than + 0.15 radians away from the angle of the ship rotation
                            pos >= planes.get(a).getAngle() - 0.5) { /// and if the angle of that ship is less than + 0.15 radians away from the angle of the ship rotation
                        screen = 3; /// set the  to "3" which is the "add score" case???? (why isn't this done here?)
                    }
                }
                draw.setXORMode(Color.green);
                draw.drawString("SCORE: " + score, getWidth() / 2 - (g.getFontMetrics().stringWidth("SCORE: " + score)) / 2, 30); /// draw a string that lists the score at 300 300
                draw.dispose();
                break;

            case (3): // add score case
                scores.add(score); // add the current score to the score arrayList
                Collections.sort(scores, Collections.reverseOrder()); // sort the arrayList
                screen = 4; /// set the screen (gamestate?) to "4"???? which is the game over screen case
                colour = new Color((int) (Math.random() * 0x1000000));
                break;
            case (4)://Game over case "sceen"
                draw.setXORMode(colour); // set XOR mode with pink
                draw.drawImage(new ImageIcon("resources//title.png").getImage(), 0, 0, getWidth(), getWidth(), this);
                draw.setFont(new Font("MS Serif", Font.PLAIN, 300)); // set font size larger
                draw.drawString("GAME", getWidth() / 2 - g.getFontMetrics().stringWidth("GAME") / 2, 200); // draw "game " as text on screen
                draw.drawString("OVER", getWidth() / 2 - g.getFontMetrics().stringWidth("OVER") / 2, getHeight() / 2 + 280); // draw " over" as text on screen
                draw.setFont(new Font("Consolas", Font.PLAIN, 20)); // set font size to smaller
                draw.drawString("SCORE: " + score, getWidth() / 2 - g.getFontMetrics().stringWidth("SCORE: 999") / 2, getHeight() / 2); // draw "SCORE:" with the score on the screen 
                draw.dispose(); // end xormode
                break;
        }
        super.paintComponents(g);
    }

    /**
     * keyTyped method
     *
     * not used
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * keyPressed method
     *
     * presses the key
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            press[0] = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            press[1] = 1;
        }
        if (press[2] == 0 && (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)) {
            press[2] = 1;
            fire.start();
        } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            press[3] = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            press[4] = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (screen == 0) {
                System.exit(0);
            } else if (screen == 2) {
                screen = 3;
            } else {
                screen = 0;
            }
        }
    }

    /**
     * keyReleased method
     *
     * for movement
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) { // if the a key or the left arrow key is pressed
            press[0] = 0; // set the 
        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            press[1] = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            press[2] = 0;
            fire.stop();
        }
    }

    /**
     * action performed
     *
     * Sets the fps
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint(); // repaints 
        requestFocus(); //request focus
        setFocusTraversalKeysEnabled(false); ///?? No idea what this does
    }
}
