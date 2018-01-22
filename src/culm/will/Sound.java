/*
 * Description: Sound objects for playing sound 
 * example of working sound in my github https://github.com/wiwichips/JavaSoundExample/blob/master/src/testsound/TestSound.java
 * Author:      Will Pringle
 */
package culm.will;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.Timer;

/**
 *
 * @author wipri9236
 */
public class Sound implements LineListener {

    String[] tracks;
    int currentSong = 0;
    Timer c;

    public Sound(String[] tracks) {
        this.tracks = tracks;

        playSound(new File(tracks[currentSong++]), true);
    }

    public Sound(String effect) {
        playSound(new File(effect), false);
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

    @Override
    public void update(LineEvent event) {
        if (event.toString().startsWith("Stop")) {
            playSound(new File(tracks[currentSong]), true);
            if (currentSong + 1 < tracks.length) {
                currentSong++;
            } else {
                currentSong = 0;
            }
        }
    }
}
