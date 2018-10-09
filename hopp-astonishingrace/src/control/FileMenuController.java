/*
 * TCSS 305 - Astonishing Race
 */
package control;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import model.ObserableRaceControls;

/**
 * Decomposed from Menu, objects of this class represent the MenuBar 
 * for File Control.
 *    
 * @author Hop Pham
 * @version Jan 29 2018
 */
public class FileMenuController extends JMenuBar {
    
    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = -7598457383183646632L;

    /** The separator for formatted. */
    private static final String NEW_LINE = "\n";
    
    /** The icon exit. */
    private static final String ICON_EXIT = "./images/ic_exit.png";
    
    /** The default width size. */
    private static final int IC_WIDTH_MENU = 12;
 
    /** The default height size. */
    private static final int IC_HEIGHT_MENU = 12;
    
    /**
     * Construct a MenuBar for choosing file and exit. 
     * @param theFrame the frame to build this MenuBar from
     * @param theRace Obserable
     */
    public FileMenuController(final JFrame theFrame,  final ObserableRaceControls theRace) {
        super();
       
        add(buildFileMenu(theFrame, theRace));
        
    }
    
    /**
     * Builds a menu with some options. 
     * @param theRace Obserable
     * @param theFrame the containing JFrame of this menu bar
     * @return a "file" menu with some menu items
     */
    private JMenu buildFileMenu(final JFrame theFrame, final ObserableRaceControls theRace) {
        final JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        final JMenuItem fileItem = new JMenuItem("Load Race...");
        
        fileItem.addActionListener(theEvent -> chooseFile(theFrame, theRace));

        menu.add(fileItem);
        menu.addSeparator();        
        final JMenuItem exitItem = new JMenuItem("Exit",
                           buildIcons(ICON_EXIT, IC_WIDTH_MENU, IC_HEIGHT_MENU));
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                theFrame.dispatchEvent(new WindowEvent(theFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
        menu.add(exitItem);
        return menu;
    }
    
    /**
     * Choose file to read then print out the content.
     * @param theRace Obserable
     * @param theFrame the frame.
     */
    private void chooseFile(final JFrame theFrame, final ObserableRaceControls theRace) {
        try {
            final JFileChooser loadFile = new JFileChooser();
            //loadFile.setCurrentDirectory("../race_files");
            final File workingDirectory = new File(Paths.get("./race_files").toString());
            loadFile.setCurrentDirectory(workingDirectory);

            //opens dialog if button clicked
            if (loadFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION 
                                && (loadFile.getSelectedFile().canRead() 
                                && loadFile.getSelectedFile().exists())) {
                theRace.loadRace(loadFile.getSelectedFile());
            }
        //catches input/output exceptions and all subclasses exceptions
        } catch (final IOException ex) {
            JOptionPane.showMessageDialog(this,
                                "Error Processing File:" + ex.getMessage() + NEW_LINE);
        }
        
        HelperClass.enableComponents(theFrame, true);

    }

    
    
    /**
     * Builds icon.
     * @param thePath icon address
     * @param theHeight the height
     * @param theWidth the width
     * @return the icon
     */
    private ImageIcon buildIcons(final String thePath, 
                                 final int theHeight, final int theWidth) {
        ImageIcon icon = new ImageIcon(thePath);
        final Image smallImage = icon.getImage().getScaledInstance(
                                  theWidth, theHeight, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(smallImage);
        return icon;
    }
}
