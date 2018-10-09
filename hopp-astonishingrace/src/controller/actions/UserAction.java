/*
 * TCSS 305 - Astonishing Race
 */
package controller.actions;

import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

/**
 * An implementation of ToggleAction that enable-disable loop race. 
 * during actionPerformed. 
 * @author Hop Pham
 * @version Feb 6 2018
 */
public class UserAction extends ToggleAction {

    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = -1032189651279553207L; 
 
    /** The action to perform. */
    private final Runnable myThingToDo;
   
    /**
     * Creates a ToggleAction that will control a timer.  
     * 
     * @param theFirstName the text of this Action in the original state
     * @param theSecondName the icon of this Action in the original state
     * @param theFirstIcon the text of this Action in the toggled state
     * @param theSecondIcon the icon of this Action in the toggled state
     * @param theRepeatAction the action to perform
     */
    public UserAction(final String theFirstName, 
                        final String theSecondName, 
                        final ImageIcon theFirstIcon,
                        final ImageIcon theSecondIcon,
                        final Runnable theRepeatAction) {
        super(theFirstName, theSecondName, theFirstIcon, theSecondIcon);
        myThingToDo = theRepeatAction;

    }
    
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        super.actionPerformed(theEvent); 
        myThingToDo.run();
    }
    
}
