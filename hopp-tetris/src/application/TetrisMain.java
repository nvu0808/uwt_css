/*
 * TCSS 305 - Astonishing Race
 */

package application;

import controls.TetrisGUI;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.Board;




/**
 * Runs the Astonishing Race program.
 * 
 * @author Hop Pham
 * @version Feb 6 2018
 */

public final class TetrisMain {
    
    /**
     * Private constructor to prevent construction of instances.
     */
    private TetrisMain() {
        // do nothing
    }

    /**
     * Constructs the main GUI window frame.
     * @author Charles Bryan
     * @param theArgs Command line arguments (ignored).
     */
    public static void main(final String... theArgs) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (final IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (final InstantiationException ex) {
            ex.printStackTrace();
        } catch (final ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TetrisGUI(new Board()).run();

            }
        });
    }
}
