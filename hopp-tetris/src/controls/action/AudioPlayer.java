/*
 * TCSS 305 - Tetris
 */
package controls.action;
 
import java.io.File;
import java.io.IOException; 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
 
/**
 * This is class play back an audio file
 * using the Clip in Java Sound API.
 * @author Hop Pham www.codejava.net
 * @version Feb 28 2018
 */
public class AudioPlayer {
     
    /** Default loop to play the music. */
    private static final int LOOP = 100;
    /** Clip object to play music. */
    private Clip myAudioClip;
     
    /**
     * Play a given audio file.
     * @param theAudioFilePath Path of the audio file.
     */
    public void play(final String theAudioFilePath) {
        final File audioFile = new File(theAudioFilePath);
 
        try {
            final AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            final AudioFormat format = audioStream.getFormat();
 
            final DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            myAudioClip = (Clip) AudioSystem.getLine(info);
 
//            myAudioClip.addLineListener(this);
 
            myAudioClip.open(audioStream);
             
            myAudioClip.start();
            myAudioClip.loop(LOOP);
            
//            audioClip.close();
             
        } catch (final UnsupportedAudioFileException ex) {
            JOptionPane.showMessageDialog(null,
                                          "Un supported file:" + ex.getMessage());
        } catch (final LineUnavailableException ex) {
            JOptionPane.showMessageDialog(null,
                                          "Unavailable:" + ex.getMessage());
        } catch (final IOException ex) {
            JOptionPane.showMessageDialog(null,
                                          "Can't Processing File:" + ex.getMessage());
        }
         
    }
    
    /** Helper method to start music.*/
    public void start() {
        myAudioClip.start();
    }
    
    /** Helper method to stop music. */
    public void stop() {
        myAudioClip.stop();
    }
}