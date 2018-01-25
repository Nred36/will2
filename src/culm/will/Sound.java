/*
 * Description: Sound objects for playing sound 
 * example of working sound in my github https://github.com/wiwichips/JavaSoundExample/blob/master/src/testsound/TestSound.java
 * Author:      Will Pringle
 */
package culm.will;

// IMPORTS
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 * Sound class for sound objects
 * @author wipri9236
 */
public class Sound implements LineListener {
    // INSTANCE VARIABLES
    private String[] tracks; // String array of tracks optionally used when constructing the object
    private int currentSong = 0;

    /**
     * Sound constructor for sound objects with more than one sound file to use
     * @param tracks 
     */
    public Sound(String[] tracks) {
        this.tracks = tracks;

        playSound(new File("src//"+tracks[currentSong++]), true);
    }

    /**
     * Sound constructor for sound objects with one sound file to use
     * @param effect 
     */
    public Sound(String effect) {
        playSound(new File("src//"+effect), false);
    }

    /**
     * Method that plays a sound
     *
     * @param sound
     */
    private void playSound(File sound, Boolean repeat) {
        // SURROUND IN A TRY CATCH 
        try {
            Clip clip = AudioSystem.getClip(); // create Clip object clip and set it to AudioStream.getClip()
            clip.open(AudioSystem.getAudioInputStream(sound)); // open the audiostream with input stream
            clip.start(); // start the clip
            if (repeat == true) {
                clip.addLineListener(this);
            }
        } catch (Exception e) {
        }
    }

    /**
     * Overridding the update method
     * @param event 
     */
    @Override
    public void update(LineEvent event) {
        if (event.toString().startsWith("Stop")) {
            playSound(new File("src//"+tracks[currentSong]), true);
            if (currentSong + 1 < tracks.length) {
                currentSong++;
            } else {
                currentSong = 0;
            }
        }
    }
}
