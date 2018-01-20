/*
 * Description: Sound objects for playing sound 
 * example of working sound in my github https://github.com/wiwichips/JavaSoundExample/blob/master/src/testsound/TestSound.java
 * Author:      Will Pringle
 */
package culm.will;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author wipri9236
 */
public class Sound {
    
    public Sound(){
        
    }
    
    
    
    /**
     * Method that plays a sound
     * @param sound 
     */
    public static void playSound(File sound){
       // SURROUND IN A TRY CATCH 
        try{ 
            Clip clip = AudioSystem.getClip(); // create Clip object clip and set it to AudioStream.getClip()
            clip.open(AudioSystem.getAudioInputStream(sound)); // open the audiostream with input stream
            clip.start(); // start the clip
            
            Thread.sleep(clip.getMicrosecondLength()/1000); // make the program sleep for the length of the clip before terminating // divide it by 1000 to get the right time
        }catch(Exception e){
            
        }
    }
}
