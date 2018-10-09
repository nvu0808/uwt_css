package controller.actions;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

/**
 * A generic concrete Action class. User code defines the action to 
 * perform by passing a Runnable object in the constructor. 
 * 
 * @author Charles Bryan
 * @version Winter 2018
 *
 */
public class UserDefinedAction extends AbstractAction {

    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = 3772900050945544969L;
    
    /** The action to perform. */
    private final Runnable myThingToDo;

    /**
     * Constructs an Action. 
     * 
     * @param theName the name for the action
     * @param theIcon the large AND small icon for the action
     * @param theResetAction the action to perform
     */
    public UserDefinedAction(final String theName, 
                       final ImageIcon theIcon,
                       final Runnable theResetAction) {
        super(theName);
        myThingToDo = theResetAction;
        final Image largeImage =
                        theIcon.getImage().
                        getScaledInstance(24, -1, java.awt.Image.SCALE_SMOOTH);
        final ImageIcon largeIcon = new ImageIcon(largeImage);
        putValue(Action.LARGE_ICON_KEY, largeIcon);
        
        final Image smallImage =
                        theIcon.getImage().
                        getScaledInstance(12, -1, java.awt.Image.SCALE_SMOOTH);
        final ImageIcon smallIcon = new ImageIcon(smallImage);
        putValue(Action.SMALL_ICON, smallIcon);
    }
    
    
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        myThingToDo.run();
        
    }

}
