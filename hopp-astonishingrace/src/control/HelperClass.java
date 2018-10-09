/*
 * TCSS 305 - Astonishing Race
 */
package control;

import java.awt.Component;
import java.awt.Container;
import java.text.DecimalFormat;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;

/**
 * Helper class to format time.
 * for File Control.
 *    
 * @author Hop Pham
 * @version Jan 29 2018
 */
public final class HelperClass {
    
    /** The number of milliseconds in a second. */
    private static final long MILLIS_PER_SEC = 1000;

    /** The number of seconds in a minute. */
    private static final long SEC_PER_MIN = 60;

    /** The number of minute in a hour. */
    private static final long MIN_PER_HOUR = 60;

    /** The separator for formatted. */
    private static final String SEPARATOR = String.valueOf(Character.toChars(58));
    
//    /** The separator for formatted. */
//    private static final String NEW_LINE = "\n";
    

    /** A formatter to require at least 2 digits, leading 0s. */
    private static final DecimalFormat TWO_DIGIT_FORMAT = new DecimalFormat("00");
    
    /** A formatter to require at least 3 digits, leading 0s. */
    private static final DecimalFormat THREE_DIGIT_FORMAT = new DecimalFormat("000");
    
    /** Construct a private constructor to prevent outside. */
    private HelperClass() {
        
    }
    /**
     * This formats a positive integer into minutes, seconds, and millisecons. 
     * 00:00:000
     * @param theTime the time to be formatted
     * @author Charles Bryan
     * @return the formated string. 
     */
    public static String formatTime(final long theTime) {
        long time = theTime;
        final long milliseconds = time % MILLIS_PER_SEC;
        time /= MILLIS_PER_SEC;
        final long seconds = time % SEC_PER_MIN;
        time /= SEC_PER_MIN;
        final long min = time % MIN_PER_HOUR;
        time /= MIN_PER_HOUR;
        return TWO_DIGIT_FORMAT.format(min) + SEPARATOR
                       + TWO_DIGIT_FORMAT.format(seconds) 
                       + SEPARATOR + THREE_DIGIT_FORMAT.format(milliseconds);
    }
    
    /**
     * Helper method to enable component after loading file.
     * @param theContainer the container
     * @param theVal enable true false
     */
    public static void enableComponents(final Container theContainer,
                                        final boolean theVal) {
        final Component[] components = theContainer.getComponents();
        for (final Component component : components) {
            component.setEnabled(theVal);
            if (component instanceof Container) {
                enableComponents((Container) component, theVal);
            }
            
            if (component instanceof JTabbedPane) {
                ((JTabbedPane) component).setEnabledAt(1, theVal);
            } else if (component instanceof JMenu) {
                for (int i = 0; i < ((JMenu) component).getMenuComponentCount(); i++) {
                    ((JMenu) component).getMenuComponent(i).setEnabled(theVal);
                }
            } 
        }
    }

}
