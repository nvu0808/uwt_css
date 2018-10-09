/*
 * TCSS 305 - Astonishing Race
 */
package controller.actions;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

/**
 * A implementation of Action that will "toggle" between two states. This class should
 * be extended to add functionality other than toggling the text and icon of the Action. 
 * A call to super.actionPerformed should be called at the beginning of extending classes 
 * actionPerformed method. This ensures that the toggle state is set correctly. 
 * 
 * @author Charles Bryan
 * @version Winter 2018
 */
public class ToggleAction extends AbstractAction {
    
    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = 3772900050945544969L;
    
    /** Determines the state of the "toggle". */
    protected boolean myFlag;

    /** Stores the text of this Action in the original state.*/
    private final String myFirstName;
    
    /** Stores the icon of this Action in the original state. */
    private final String mySecondName;
    
    /** Stores the text of this Action in the toggled state. */
    private final ImageIcon myFirstIcon;
    
    /** Stores the icon of this Action in the toggled state. */
    private final ImageIcon mySecondIcon;
    

    /**
     * Creates a ToggleAction. 
     * 
     * @param theFirstName the text of this Action in the original state
     * @param theSecondName the icon of this Action in the original state
     * @param theFirstIcon the text of this Action in the toggled state
     * @param theSecondIcon the icon of this Action in the toggled state
     */
    public ToggleAction(final String theFirstName, 
                 final String theSecondName, 
                 final ImageIcon theFirstIcon,
                 final ImageIcon theSecondIcon) {
        super(theFirstName);
        myFirstName = theFirstName;
        mySecondName = theSecondName;
        myFirstIcon = theFirstIcon;
        mySecondIcon = theSecondIcon;
        myFlag = false;
        setIcon(myFirstIcon);
        
    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        myFlag = !myFlag;
        if (myFlag) {
            setIcon(mySecondIcon);
            putValue(Action.NAME, mySecondName);
        } else {
            setIcon(myFirstIcon);
            putValue(Action.NAME, myFirstName);
        }
        
    }
    
    /**
     * Helper to set the Icon to both the Large and Small Icon values. 
     * @param theIcon the icon to set for this Action 
     */
    private void setIcon(final ImageIcon theIcon) {
        final ImageIcon icon = (ImageIcon) theIcon;
        final Image largeImage =
            icon.getImage().getScaledInstance(24, -1, java.awt.Image.SCALE_SMOOTH);
        final ImageIcon largeIcon = new ImageIcon(largeImage);
        putValue(Action.LARGE_ICON_KEY, largeIcon);
        
        final Image smallImage =
            icon.getImage().getScaledInstance(12, -1, java.awt.Image.SCALE_SMOOTH);
        final ImageIcon smallIcon = new ImageIcon(smallImage);
        putValue(Action.SMALL_ICON, smallIcon);
    }
    
}