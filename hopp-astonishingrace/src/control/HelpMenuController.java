/*
 * TCSS 305 - Astonishing Race
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Decomposed from Menu, objects of this class represent the MenuBar 
 * for File Control.
 *    
 * @author Hop Pham
 * @version Jan 29 2018
 */
public class HelpMenuController extends JMenuBar {

    
    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = -4485817885051349950L;
    
    /** The icon for the frame.*/
    private static final String ICON_FRAME = "./images/ic_race.png";  
    
    /** The separator for formatted. */
    private static final String NEW_LINE = "\n";  
    
    
    /**
     * Construct a MenuBar for help menu.
     * @param theHeader the map store header information
     */
    public HelpMenuController(final Map<String, String> theHeader) {
        super();
        add(buildHelpMenu(theHeader));
    }

    /**
     * Build help menu.
     * @param theHeader the map store header information
     * @return menu item
     */
    private JMenu buildHelpMenu(final Map<String, String> theHeader) {
        final ImageIcon icon = new ImageIcon(ICON_FRAME, "Race");
        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem race = new JMenuItem("Race Info...");
          
        final String about = "Hop Pham " + NEW_LINE + " Autum 2017 "
                          + NEW_LINE + " TCSS 305 Assignment 4b"; 
        race.addActionListener(theEvent -> showRaceInfo(theHeader));
        helpMenu.add(race);
        helpMenu.add(showDialogForHelp("About...", about, "About!", icon));
        helpMenu.setName("The Help Menu");
        helpMenu.getMenuComponent(0).setEnabled(false);
        return helpMenu;
    }    
  
    /**
     * Builds a menu item. 
     * 
     * @param theMessage text appear on the dialog
     * @param theText the text to appear on the menu item
     * @param theTitle title for the dialog box.
     * @return a simple menu item
     * @param theIcon the icon
     */
    private JMenuItem showDialogForHelp(final String theText,
                    final String theMessage, final String theTitle,
                    final ImageIcon theIcon) {
        final JMenuItem item = new JMenuItem(theText);
      
        item.addActionListener(new ActionListener() {
          
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(null, theMessage, theTitle, 1, theIcon);
            }
        });
        item.setName(theText);
        return item;
    } 
  
    /**
    * Helper method to show Race's information.
    * @param theHeader the map store header information
    */
    private void showRaceInfo(final Map<String, String> theHeader) {
        final ImageIcon icon = new ImageIcon(ICON_FRAME, "Race Icon");
        final String raceInfo = theHeader.get("RACE") + NEW_LINE 
                    + "Track type: " + theHeader.get("TRACK") + NEW_LINE
                    + "Total time: " 
                    + HelperClass.formatTime(Long.parseLong(theHeader.get("TIME"))) 
                    + NEW_LINE + "Lap distance: " + theHeader.get("DISTANCE");
        JOptionPane.showMessageDialog(null, raceInfo, "Race Info", 1, icon);
    }
}
