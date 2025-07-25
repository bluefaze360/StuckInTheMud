import java.io.File;
import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;


/**
 * SoundFile stores a .wav file, then has the ability to play the sound
 * @see <p>https://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html</p>
 */
public class SoundFile {
    /**
     * Stores the clip information
     */
    private Clip clip;

    /**
     *
     * @param fileName the file name of the .wav file
     */
    public SoundFile(String fileName) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (fileName.isBlank() || fileName == null) {
            throw new IllegalArgumentException("filename cannot be null or blank");
        }

        File soundFile = new File(fileName);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
        setClip(AudioSystem.getClip());
        clip.open(audioIn);
    }


    /**
     * Retrieves the clip's info
     * @return the sound file's clip
     */
    public Clip getClip() {
        return clip;
    }


    /**
     * Sets the clip
     * @param clip
     */
    public void setClip(Clip clip) {
        this.clip = clip;
    }


    /**
     * Plays the sound, but also delays so long as the sound plays
     * @param loops the amount of times the clip should be played
     */
    public void playSound(int loops) {
        clip.loop(loops);
        sleep((clip.getMicrosecondLength()/1000)*loops);
        clip.stop();
    }


    /**
     * Causes the currently executing thread to sleep (temporarily cease
     * execution) for the specified number of milliseconds, subject to
     * the precision and accuracy of system timers and schedulers. The thread
     * does not lose ownership of any monitors. - from Thread.sleep()
     * <br> <br>
     * This method, in comparison to Thread.sleep(), does not throw any exceptions,
     * as it isn't expected for the thread to be interrupted anyway.
     *
     * @param milliseconds the amount of milliseconds to pause the thread. Remember: 1000 milliseconds is one second.
     */
    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException _) {

        }

    }

}
