/*
 * TCSS 305 - Astonishing Race
 */
package controller.actions;

import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 * An implementation of ToggleAction that starts and stops a timer 
 * during actionPerformed. 
 * @author Charles Bryan
 * @version Winter 2018
 */
public class TimerControllAction extends ToggleAction {

    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = 3772906050945544969L;
    
    /** The timer this Action will start or stop. */
    private final Timer myTimer;
    
    /** The action to perform. */
    private final Runnable myThingToDo;
    
    /**
     * Creates a ToggleAction that will control a timer.  
     * 
     * @param theFirstName the text of this Action in the original state
     * @param theSecondName the icon of this Action in the original state
     * @param theFirstIcon the text of this Action in the toggled state
     * @param theSecondIcon the icon of this Action in the toggled state
     * @param theTimer the timer to start/stop
     * @param theRun the running control.
     */
    public TimerControllAction(final String theFirstName, 
                        final String theSecondName, 
                        final ImageIcon theFirstIcon,
                        final ImageIcon theSecondIcon,
                        final Timer theTimer, final Runnable theRun) {
        super(theFirstName, theSecondName, theFirstIcon, theSecondIcon);
        myThingToDo = theRun;
        myTimer = theTimer;
        
    }
    
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        super.actionPerformed(theEvent);      
        if (myFlag) {
            myTimer.start();            
        } else {
            myTimer.stop();
        }
        myThingToDo.run();
    }
    
}
