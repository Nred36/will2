/*
 * Description: Aircraft class for enemy aircraft objects in the game
 * Author:      Will Pringle
 */
package culm.will;

/**
 *
 * @author wipri9236
 */
public class Aircraft {

    // Variables involving posistion
    private double angle;
    private int posistionX; // the horizontal posistion on the screen
    private int posistionY; // the vertical posistion on the screen
    private int radius = 400; // radius from center of the screen
    // Variables involving size
    private int sizeX; // horizontal size
    private int sizeY; // vertical size
    // Variables involving time 
    private int age; // the time since the aircraft has been spawned
    // Variables involving aircraft aesthetics
    private String colour; // the colour of the aircraft, either "purpleAircraft.png" or "yellowAirCraft.png"

    /**
     * Aircraft Constructor (purple)
     *
     * @param angle
     */
    public Aircraft(double angle) {
        colour = "purpleAircraft.png"; // set the colour as the default (purple)
        posistionX = angleInRadiansToPosistionX(angle); // set the initial horizontal posistion 
        posistionY = angleInRadiansToPosistionY(angle); // set the initial vertical posistion
        age = 0; // set the inital age of the aircraft to 0
        sizeX = 0;
        sizeY = 0;
        this.angle = angle;
    }

    /**
     * Aircraft Constructor with color
     *
     * @param angle
     * @param colour
     */
    public Aircraft(double angle, String colour) {
        this.colour = colour; // set the colour, either "purpleAircraft.png" or "yellowAirCraft.png"
        posistionX = angleInRadiansToPosistionX(angle);
        posistionY = angleInRadiansToPosistionY(angle);
        age = 0;
        sizeX = 0;
        sizeY = 0;
    }

    private int angleInRadiansToPosistionX(double angle) {
        int posistionX; // variable for the horizontal posistion
        posistionX = (int) (Math.sin(angle) * radius + radius); // convert an angle in radians to a horizontal position on a plane with a set radius
        return posistionX; // return statement
    }

    private int angleInRadiansToPosistionY(double angle) {
        int posistionY; // variable for the vertical posistion
        posistionY = (int) (Math.cos(angle) * radius); // convert an angle in radians to a vertical position on a plane with a set radius
        return posistionY; // return statement
    }

    public void grow() {
        sizeX = (int) Math.pow(2, 0.15 * (age));
        sizeY = (int) Math.pow(2, 0.15 * (age));
        age++;
    }

    public void setPosistionX(int posistionX) {
        this.posistionX = posistionX;
    }

    public void setPosistionY(int posistionY) {
        this.posistionY = posistionY;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public int getPosistionX() {
        return posistionX;
    }

    public int getPosistionY() {
        return posistionY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public double getAngle() {        
        return angle;
    }

    public int getAge() {
        return age;
    }

    public String getColour() {
        return colour;
    }
}
