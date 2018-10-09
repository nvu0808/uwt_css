/*
 * TCSS 305 - Astonishing Race
 */
package control;

import java.util.List;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * Decomposed from TimeController, objects of this class represent the ToolBar 
 * for TimeController.
 *    
 * @author Charles Bryan
 * @version Winter 2018
 */
public class TimeControllerToolBar extends JToolBar {

    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = 1205527494434145724L;

    /**
     * Construct a ToolBar based on the Actions provided. 
     * @param theActions the Actions to build this ToolBar from
     */
    public TimeControllerToolBar(final List<Action> theActions) {
        super();

        for (final Action a : theActions) {
            final JButton butt = new JButton(a);
            butt.setHideActionText(true);
            add(butt);
        }
    }
}
