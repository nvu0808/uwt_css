/*
 * TCSS 305 - Astonishing Race
 */

package control;

import controller.actions.TimerControllAction;
import controller.actions.UserAction;
import controller.actions.UserDefinedAction;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.Timer;
import model.ObserableRaceControls;
import model.ParticipantsContainer;
import model.RaceInformation;

/**
 * The graphical user interface for the Astonishing Race program.
 * @author Hop Pham
 * @version Feb 6 2018
 */

public class ControlRaceGUI extends JPanel implements Observer {
    
    // static final fields (class constants)
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -5563440505550236562L;    
    
    /** The expected length of message[] data. */
    private static final int EXPECT_LENGTH = 4;
  
    /** The default border size. */
    private static final int BORDER_SIZE = 0; 
    
    /** The width of output data stream area. */
    private static final int TIME_WIDTH = 500;
    
    /** The height of text to show time. */
    private static final int TEXT_HEIGHT = 40;
    
    /** The number of rows in the text area. */
    private static final int TEXT_AREA_ROWS = 10;
    
    /** The number of columns in the text area. */
    private static final int TEXT_AREA_COLS = 50;    
   
    /** Amount of milliseconds between each call to the timer. */
    private static final int TIMER_FREQUENCY = 1; 
    
    /** The speed mode. */
    private static final int TIMER_SPEED_MODE = 4;
      
    /** Text for the buttons used to start the timer. */
    private static final String BUTTON_TEXT_START = "Start";   
    
    /** Text for the buttons used to stop the timer. */
    private static final String BUTTON_TEXT_STOP = "Stop";  
    
    /** Text for reset buttons. */
    private static final String BUTTON_TEXT_RESTART = "Restart"; 
    
    /** Text for reset buttons. */
    private static final String BUTTON_TEXT_CLEAR = "Clear";
    
    /** Text for repeat buttons. */
    private static final String BUTTON_TEXT_REPEAT_ON = "Single Race";
    
    /** Text for loop repeat buttons. */
    private static final String BUTTON_TEXT_REPEAT_OFF = "Loop Race";
    
    /** Text for one time buttons. */
    private static final String BUTTON_TEXT_ONE = "Time One";
    
    /** Text for four time buttons. */
    private static final String BUTTON_TEXT_FOUR = "Time Four";
    
    /** The icon play. */
    private static final String ICON_PLAY = "./images/ic_play.png";
    
    /** The icon pause. */
    private static final String ICON_PAUSE = "./images/ic_pause.png";
    
    /** The icon restart. */
    private static final String ICON_RESTART = "./images/ic_restart.png";
    
    /** The icon repeat. */
    private static final String ICON_REPEAT = "./images/ic_repeat.png";
    
    /** The color icon repeat. */
    private static final String ICON_REPEAT_COLOR = "./images/ic_repeat_color.png";
    
    /** The icon one time. */
    private static final String ICON_ONE_TIME = "./images/ic_one_times.png";
    
    /** The icon four time. */
    private static final String ICON_FOUR_TIME = "./images/ic_four_times.png";
    
    /** The icon clear text. */
    private static final String ICON_CLEAR = "./images/ic_clear.png";
    
    /** The icon for the frame.*/
    private static final String ICON_FRAME = "./images/ic_race.png";     
    
    /**
     * The minor tick spacing for the FPS mySlider.
     */
    private static final int MINOR_TICK_SPACING = 1;
    /**
     * The major tick spacing for the FPS mySlider.
     */
    private static final int MAJOR_TICK_SPACING = 10;

    /** The separator for formatted. */
    private static final String SEPARATOR = String.valueOf(Character.toChars(58));
    
    /** The separator for formatted. */
    private static final String NEW_LINE = "\n"; 
   
    //instance field
    /** Racer go here.*/
    private JPanel myRacerOutput = new JPanel();
    /** Where the output goes! */
    private final JTextArea myTextArea;
    
    /** Where the time output goes! */
    private final JLabel myTimeLable;
    
    /** The Race Model.*/
    private final ObserableRaceControls myRace;
    
    /** The timer to control how often to advance the Time object. */ 
    private final Timer myTimer;
    
    /** Store the Actions used in this GUI. */
    private final List<Action> myActions;
    
    /** The flag.*/
    private boolean myRepeat;
    
    /** The speed mode. */
    private int mySpeedMode;
    
    /** The Slider. */
    private final JSlider mySlider;    
    
    /** The header info. */
    private RaceInformation myHeader;
   
    /**
     * Constructs a Race.    
     * @param theRace the color object this class controls
     */
    public ControlRaceGUI(final ObserableRaceControls theRace) {
        super(new BorderLayout());
        mySlider = new JSlider();
        myRace = theRace;
        mySpeedMode = TIMER_FREQUENCY;
        myTextArea = new JTextArea(TEXT_AREA_ROWS , TEXT_AREA_COLS);
        myTimeLable = new JLabel(); 
        myTimer = new Timer(TIMER_FREQUENCY, this::handleTimer);
        myActions = new ArrayList<>();
    }
    
    /**
     * Create and add the actions to the list. 
     */
    private void buildActions() {
        
        final Action reset = new UserDefinedAction(BUTTON_TEXT_RESTART,
                                             new ImageIcon(ICON_RESTART),
                                             this::handleReset);
        final Action clear = new UserDefinedAction(BUTTON_TEXT_CLEAR,
                                                   new ImageIcon(ICON_CLEAR),
                                                   this::handleClear);
        myActions.add(reset);
        
        
        myActions.add(new TimerControllAction(BUTTON_TEXT_START,
                                       BUTTON_TEXT_STOP,
                                       new ImageIcon(ICON_PLAY),
                                       new ImageIcon(ICON_PAUSE),
                                       myTimer, this::handleRun));
        
        myActions.add(new UserAction(BUTTON_TEXT_ONE,
                                     BUTTON_TEXT_FOUR,
                                     new ImageIcon(ICON_ONE_TIME),
                                     new ImageIcon(ICON_FOUR_TIME),
                                     this::handleSpeed));
        
        myActions.add(new UserAction(BUTTON_TEXT_REPEAT_ON,
                                              BUTTON_TEXT_REPEAT_OFF,
                                              new ImageIcon(ICON_REPEAT),
                                              new ImageIcon(ICON_REPEAT_COLOR),
                                              this::handleRepeat));
        myActions.add(clear);
    }    
   
    /**
     * Event handler for speed.
     */
    private void handleSpeed() {
        if (mySpeedMode == TIMER_FREQUENCY) {
            mySpeedMode = TIMER_SPEED_MODE;
        } else {
            mySpeedMode = TIMER_FREQUENCY;
        }
    }
    
    /** Event handler for run.*/
    private void handleRun() {
        if (myTimer.isRunning()) {
            mySlider.setEnabled(false);
        } else {
            mySlider.setEnabled(true);
        }
    }
    /**
     * Event handler for repeat.
     */
    private void handleRepeat() {
        myRepeat = !myRepeat;
    }
    
    /**
     * Event handler for reset.
     */
    private void handleReset() {
        myRace.moveTo(0);
    }
    
    /**
     * Event handler for clear.
     */
    private void handleClear() {
        myTextArea.setText("");
    }
    
    /**
     * Event handler for the timer. 
     * @param theEvent the fired event
     */
    private void handleTimer(final ActionEvent theEvent) { //NOPMD
        myRace.advance(TIMER_FREQUENCY * mySpeedMode);
    }
    
    /**
     * A helper method to make a ToolBar.
     * 
     * @return the example Tool Bar
     */
    private JToolBar createToolBar() {
        final JToolBar toolbar = new TimeControllerToolBar(myActions);
        HelperClass.enableComponents(toolbar, false);
        return toolbar;
    }
    
    /**
     * Lay out the components.
     */
    public final void setUpComponents() {
        final JPanel content = new JPanel(new BorderLayout());
        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Data Output Stream", buildDataOutputStream());
        tabbedPane.addTab("Race Participants", buildRacePract());
        tabbedPane.setEnabledAt(1, false);
        content.add(buildTimeFrame(), BorderLayout.NORTH);
        content.add(tabbedPane, BorderLayout.SOUTH);
        content.setBorder(BorderFactory.createEmptyBorder(
                BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        add(content, BorderLayout.CENTER);
        add(createToolBar(), BorderLayout.SOUTH);
    }
    /**
     * Build panel to display time.
     * @return Panel the panel
     */
    private JPanel buildTimeFrame() {
        final JPanel timeFrame = new JPanel();
        mySlider.setName("Time mySlider");
        mySlider.setMajorTickSpacing(MAJOR_TICK_SPACING);
        mySlider.setMinorTickSpacing(MINOR_TICK_SPACING);
        mySlider.setPaintTicks(false);
        mySlider.setPreferredSize(new Dimension(TIME_WIDTH, TEXT_HEIGHT));
        mySlider.addChangeListener(theEvent -> handleSlider());
        myTimeLable.setText(" 00:00:000 ");
        myTimeLable.setBorder(BorderFactory.createEtchedBorder());
        
        mySlider.setValue(0);        
        timeFrame.add(mySlider);
        timeFrame.add(myTimeLable);
        timeFrame.setLayout(new FlowLayout());
        timeFrame.setAlignmentX(Component.CENTER_ALIGNMENT);
        mySlider.setEnabled(false);
        return timeFrame;
    }
    
    /**
     * Handler Slider.
     */
    private void handleSlider() {
        myTimeLable.setText(HelperClass.formatTime(Long.valueOf(mySlider.getValue())));
        myRace.moveTo(mySlider.getValue());
    }
    /**
     * Build panel to display data.
     * @return Jpanel
     */
    private JPanel buildRacePract() {
        myRacerOutput = new JPanel(new FlowLayout(FlowLayout.LEFT));        
        myRacerOutput.setPreferredSize(new Dimension(TEXT_AREA_ROWS, TEXT_AREA_COLS));
        return myRacerOutput;
    }
    
    /**
     * Build panel to display data.
     * @return Panel
     */
    private JPanel buildDataOutputStream() {
        final JPanel dataOutput = new JPanel();
        final JScrollPane scrollPane = new JScrollPane(myTextArea,
                          JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                          JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                                               
        dataOutput.add(scrollPane);
        return dataOutput;
    }
    
    /**
     * Build the menu bar for this GUI. 
     * 
     * @param theFrame the containing JFrame of this menu bar
     * @param theActions list action
     * @return the menu bar for this GUI
     */
    private JMenuBar createMenu(final JFrame theFrame, final List<Action> theActions) {
        final JMenuBar menuBar = new JMenuBar();

        menuBar.add(new FileMenuController(theFrame, myRace));
        menuBar.add(new TimeControllerMenuBar(theActions));
//        menuBar.add(new HelpMenuController(myHeaderInfo));
        menuBar.add(buildHelpMenu());  
        return menuBar;
    }
    
    /**
     * Helper method to show Race's information.
     */
    private void showRaceInfo() {
        final ImageIcon icon = new ImageIcon(ICON_FRAME, "Race Icon");
        final String raceInfo = myHeader.getRaceName() + NEW_LINE 
                     + "Track type: " + myHeader.getTrackType() + NEW_LINE
                     + "Total time: " 
                     + HelperClass.formatTime(myHeader.getTotalTime()) 
                     + NEW_LINE + "Lap distance: " + myHeader.getTrackDistance();
        JOptionPane.showMessageDialog(null, raceInfo, "Race Info", 1, icon);
    }
    
    /**
     * Build help menu.
     * 
     * @return menu item
     */
    private JMenu buildHelpMenu() {
        final ImageIcon icon = new ImageIcon(ICON_FRAME, "Race");
        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem race = new JMenuItem("Race Info...");
        
        final String about = "Hop Pham " + NEW_LINE + " Autum 2017 "
                        + NEW_LINE + " TCSS 305 Assignment 4b"; 
        race.addActionListener(theEvent -> showRaceInfo());
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
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void initGUI() {

        //Create and set up the window.
        final JFrame frame = new JFrame("The Astonishing Race!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        final ControlRaceGUI contentPane = new ControlRaceGUI(myRace);
        contentPane.buildActions();
        contentPane.setUpComponents();
        frame.setContentPane(contentPane);
        frame.setJMenuBar(contentPane.createMenu(frame, contentPane.myActions));
        frame.setIconImage(new ImageIcon(ICON_FRAME).getImage());
        myRace.addObserver(contentPane);
        //Create a time panel to listen to and demonstrate our 
        //ObservableTime.
        //Create and set up the window.
        //frame.setComponentZOrder(moveAble, 1);
        //Display the window.           
        //set the window center the screen
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth(),
                 Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getHeight());
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false); 
    } 
    
    @Override
    public void update(final Observable theObservable, final Object theArgument) {
        if (theArgument instanceof RaceInformation) {
            myHeader = (RaceInformation) theArgument;
            mySlider.setMaximum(myHeader.getTotalTime());
        } else if (theArgument instanceof ParticipantsContainer) {
            new RacerCheckBoxController((ParticipantsContainer) theArgument,
                                        myRacerOutput, myRace);
        } else if (theArgument instanceof String) {
            final String[] result = ((String) theArgument).split(SEPARATOR);
            myTextArea.append((String) theArgument + NEW_LINE);
            if (((String) theArgument).split(SEPARATOR).length > EXPECT_LENGTH) {
                mySlider.setValue(Integer.parseInt(result[1]));
                myTimeLable.setText(HelperClass.formatTime(Integer.parseInt(result[1])));
                if ((Integer.parseInt(result[1]) == mySlider.getMaximum()) && myRepeat) {
                    myRace.moveTo(0);
                    mySlider.setValue(0);
                }
            }
        }
         
    }
    
   
    /**
     * Run the program.
     */
    public void run() {
        initGUI();
    }
}