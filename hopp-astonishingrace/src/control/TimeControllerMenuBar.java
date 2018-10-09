/*
 * TCSS 305 - Astonishing Race
 */
package control;

import java.util.List;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Decomposed from TimeController, objects of this class represent the MenuBar 
 * for TimeController.
 *    
 * @author Charles Bryan
 * @version Winter 2018
 */
public class TimeControllerMenuBar extends JMenuBar {
    
    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = 1205527494434145724L;

    /**
     * Construct a MenuBar based on the Actions provided. 
     * @param theActions the Actions to build this MenuBar from
     */
    public TimeControllerMenuBar(final List<Action> theActions) {
        super();

        final JMenu controls = new JMenu("Controls");
        controls.setName("The Controls");
        for (final Action a : theActions) {
            final JMenuItem item = new JMenuItem(a);
            item.setEnabled(false);
            controls.add(item);
        }

        add(controls);

    }

    
    
}
