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
    final private int posistionX; // the horizontal posistion on the screen
    final private int posistionY; // the vertical posistion on the screen
    final private int radius = 400; // radius from center of the screen
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
        colour = "aircraft.png"; // set the colour as the default (purple)
        posistionX = angleInRadiansToPosistionX(angle); // set the initial horizontal posistion 
        posistionY = angleInRadiansToPosistionY(angle); // set the initial vertical posistion
        age = 0; // set the inital age of the aircraft to 0
        sizeX = 0; // sets the inital sizeX to 0
        sizeY = 0; // sets the inital sizeY to 0
        this.angle = angle; // sets the angle to what is put in
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
        age = 0; // set the inital age of the aircraft to 0
        sizeX = 0; // sets the inital sizeX to 0
        sizeY = 0; // sets the inital sizeY to 0
        this.angle = angle; // sets the angle to what is put in
    }

    /**
     * This method returns the horizontal position using the angle
     * @param angle
     * @return posistionX
     */
    private int angleInRadiansToPosistionX(double angle) {
        int posistionX; // variable for the horizontal posistion
        posistionX = (int) (Math.sin(angle) * radius + radius); // convert an angle in radians to a horizontal position on a plane with a set radius
        return posistionX; // return statement
    }

    /**
     * This method returns the vertical position using the angle
     * @param angle
     * @return posistionY
     */
    private int angleInRadiansToPosistionY(double angle) {
        int posistionY; // variable for the vertical posistion
        posistionY = (int) (Math.cos(angle) * radius); // convert an angle in radians to a vertical position on a plane with a set radius
        return posistionY; // return statement
    }

    /**
     * This method increases the size variable for x and y
     * @param size 
     */
    public void grow(boolean size) {
        if (size == true) {
            sizeX = (int) Math.pow(1.8, 0.15 * (age)); // grows the horizontal size
            sizeY = (int) Math.pow(1.8, 0.15 * (age)); // grows the vertical size
        }
        age++; // increments the age
    }

    /**
     * returns the horizontal position
     * @return posistionX
     */
    public int getPosistionX() {
        return posistionX;
    }

    /**
     * returns the vertical position
     * @return posistionY
     */
    public int getPosistionY() {
        return posistionY;
    }

    /**
     * returns the horizontal size
     * @return sizeX
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * return the vertical size
     * @return sizeY
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * returns the angle
     * @return angle
     */
    public double getAngle() {
        return angle;
    }

    /**
     * returns the the time the object has been alive
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * return the variation of the aircraft type
     * @return colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * set the type of aircraft
     * @param colour 
     */
    public void setColour(String colour) {
        this.colour = colour;

    }
}