/*
 * TCSS 305 - Tetris
 */

package controls; //NOPMD

import controls.action.AudioPlayer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import model.Block;
import model.Board;
import model.TetrisPiece;
import util.Scores;



/**
 * The graphical user interface for the Tetris program.
 * @author Hop Pham
 * @version Feb 28 2018
 */

public class TetrisGUI extends JPanel implements Observer {
    
    // static final fields (class constants)
   
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -2147229925977537569L;
    
    /** The default border size. */
    private static final int BORDER_SIZE = 0; 
    
    /** The icon for the frame.*/
    private static final String ICON_FRAME = "./images/ic_frame.png"; 
    
    /** The separator for formatted. */
    private static final String NEW_LINE = "\n";
    
    /** Amount of milliseconds between each call to the timer. */
    private static final int TIMER_FREQUENCY = 1000; 
    
    /** The icon exit. */
    private static final String ICON_EXIT = "./images/ic_exit.png";
    
    /** The default width size. */
    private static final int IC_WIDTH_MENU = 12;
 
    /** The default height size. */
    private static final int IC_HEIGHT_MENU = 12;
    
    /**
     * The panel ratio related to I.
     */
    private static final int I_RATIO = 4;
    
    /** The panel ratio related to any Tetrispiece excepted the I one. */
    private static final int RATIO = 3;
    
    /** The width - height of one square piece. */
    private static final int ONE_EDGE_SIZE = 40;
    
    /**
     * The panel width.
     */
    private static final int WIDTH = 300;

    /**
     * The panel height.
     */
    private static final int HEIGHT = 800;

    /**height for fit frame. */
    private static final int FITSIZE_VERTICAL = 80;
    // instance fields

    /** Name for newGame ItemMenu. */
    private static final String NEW_GAME = "New Game";
    /** Name for newGame ItemMenu. */
    private static final String END_GAME = "End Game";

    /** Icon for frame. */
    private static final ImageIcon ICONF = new ImageIcon(ICON_FRAME, "Tetris");
    /**width for fit frame. */
    private static final int FITSIZE_HORIZONTAL = 110;

    /** String text to display. */
    private static final String REPLAY_GAME = "Replay!";
    
    /** Observable object. */
    private final Board myBoard;
   
    /** The timer to control how often to advance the Time object. */ 
    private final Timer myTimer;
    
//    /** The timer to control how often to advance the Time object. */ 
//    private final Timer myTimerRecord;
    
    /** Center panel class where player plays game. */
    private final GameCenterControls myGameCenter;
    
    /** Right panel class which shows next piece. */
    private final NextPiecePanel myNextPiecePanel;
    
    /** Right bottom panel that class controls key and game mod.*/
    private final ShowGameDetail myGameDetail;
    
    /** The audio class to play music. */
    private final AudioPlayer myAudioPlayer;
    
    /** Score class. */
    private final Scores myScore;
    
    /** The current level. */
    private int myCurrentLevel;
    /** To replay the instant game. */
    private final Queue<Block[][]> myRecord;
    /** flag for replay. */
    private boolean myReplay;
    /**
     * Constructs.    
     * @param theBoard the color object this class controls
     */
    public TetrisGUI(final Board theBoard) {
        super(new BorderLayout());        
        myScore = new Scores();
        myBoard = theBoard;
        myTimer = new Timer(TIMER_FREQUENCY, theEvent -> handleTimer());         
        myGameCenter = new GameCenterControls(myBoard, myTimer);
        myNextPiecePanel = new NextPiecePanel();            
        myGameDetail = new ShowGameDetail(myScore);
        myAudioPlayer = new AudioPlayer();        
        myRecord = new LinkedList<Block[][]>();   
        myCurrentLevel = myScore.getLevel();
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
    
    /**
     * Event handler for the timer. 
     */
    private void handleTimer() {
        if (myReplay && !myRecord.isEmpty()) {
            final Block[][] temp = myRecord.poll();
            myGameCenter.pieceChanged(temp);
            myRecord.add(temp);
        } else {      
            myBoard.step();
        }
    }
    
    /**
     * Lay out the components.
     */
    public void setUpComponents() {
        final JPanel content = new JPanel();
        content.addMouseListener(theEvent -> playMusic());
        final JPanel rightPanel = new JPanel();
        content.setBorder(BorderFactory.createEmptyBorder(
                BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        rightPanel.setLayout(null);
        rightPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        rightPanel.add(myNextPiecePanel);
        rightPanel.add(myGameDetail);
        rightPanel.setBackground(Color.GRAY);
        myNextPiecePanel.setBounds(0, 0, ONE_EDGE_SIZE * RATIO, ONE_EDGE_SIZE * RATIO);
        myGameDetail.setBounds(0, WIDTH, WIDTH, HEIGHT);
        add(myGameCenter, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.LINE_END);
        
    }

    
    /**
     * Build main menu.
     * @param theFrame the frame
     * @return Menu bar
     */
    private JMenu buildGameMenu(final JFrame theFrame) {
        final JMenu menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_G);
        
        final JMenuItem newGame = new JMenuItem(NEW_GAME);
        newGame.setName(NEW_GAME);
        newGame.addActionListener(theEvent -> newGame(newGame));
        final JMenuItem replayItem = new JMenuItem(REPLAY_GAME);
        replayItem.setEnabled(false);
        replayItem.addActionListener(theEvent -> replayAction(replayItem));
        menu.add(newGame);
        menu.add(replayItem);
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
     * Handler control new game and end game.
     * @param theItem the menu item
     */
    private void newGame(final JMenuItem theItem) {
        if (theItem.getName().equals(NEW_GAME)) {
            myReplay = false;
            myGameDetail.start();
            myGameCenter.gameOver(false);
            myBoard.newGame();                
            myTimer.start();
            myRecord.clear();            
            playMusic();
            theItem.setText(END_GAME);
            theItem.setName(END_GAME);
            ((JPopupMenu) theItem.getParent()).getComponent(1).setEnabled(false);            
        } else {
            myTimer.stop();
            final int dialogResult = JOptionPane.showConfirmDialog(this, 
                               "Are you want to end this game?", "Warning",
                               0, 0, ICONF);            
            if (dialogResult == JOptionPane.YES_OPTION) {
                myTimer.stop();
                myAudioPlayer.stop();
                theItem.setText(NEW_GAME);
                theItem.setName(NEW_GAME);
                myGameCenter.gameEnd();
            } else {
                myTimer.start();
            }
            
        }
    }
    
    /**
     * Allow user to replay or stop the instant game.
     * @param theItem menu item
     */
    private void replayAction(final JMenuItem theItem) {
        if (theItem.getText().equals(REPLAY_GAME)) {
            myTimer.start();
            myReplay = true;
            theItem.setText("Stop");
        } else {
            myReplay = false;
            myTimer.stop();
            theItem.setText(REPLAY_GAME);
            ((JPopupMenu) theItem.getParent()).getComponent(0).setEnabled(true);
// System.out.println(((JPopupMenu) theItem.getParent()).getComponent(1).toString());
//System.out.println(((JPopupMenu) theItem.getParent()).getComponent(0).toString());
        }
    }
   
    /**
     * Build the menu bar for this GUI. 
     * @return the menu bar for this GUI
     */
    private JMenuBar createMenu() {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(buildGameMenu((JFrame) SwingUtilities.getRoot(this)));

        menuBar.add(buildHelpMenu());  
        return menuBar;
    }    
    
    /**
     * Build help menu.
     * 
     * @return menu item
     */
    private JMenu buildHelpMenu() {
        
        final JMenu helpMenu = new JMenu("Help");
        
        final String about = "Hop Pham " + NEW_LINE + " Winter 2018 "
                        + NEW_LINE + " TCSS 305 Assignment 5"; 

        helpMenu.add(showDialogForHelp("About...", about,
                                       "Tetris ^-^ 305 Winter 2018", ICONF));
        helpMenu.setName("The Help Menu");

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
     * Helper to play music.
     */
    private void playMusic() {
        final String audioFilePath = "./music/tetris-gameboy-01.wav";
        myAudioPlayer.play(audioFilePath);
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void initGUI() {

        //Create and set up the window.
        final JFrame frame = new JFrame("Tetris - Winter 2018 UWT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        final TetrisGUI contentPane = new TetrisGUI(myBoard);
        contentPane.setUpComponents();
        contentPane.setBorder(BorderFactory.createEmptyBorder(
                               BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        frame.setContentPane(contentPane);
        frame.setJMenuBar(contentPane.createMenu());
        frame.setIconImage(new ImageIcon(ICON_FRAME).getImage());
        myBoard.addObserver(contentPane);
        
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth(),
                 Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getHeight());
        frame.setPreferredSize(new  Dimension(WIDTH * 2 
                                    + FITSIZE_HORIZONTAL, HEIGHT + FITSIZE_VERTICAL));
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false); 
    } 
    
    @Override
    public void update(final Observable theObservable, final Object theArgument) {
        if (theArgument instanceof Block[][]) {  
            myGameCenter.pieceChanged((Block[][]) theArgument);
            myRecord.add((Block[][]) theArgument);
        } else if (theArgument instanceof Integer[]) {
            //complete a line
            myGameDetail.updateScore(false, ((Integer[]) theArgument).length);
        } else if (theArgument instanceof TetrisPiece) {
            showNextPiece(theArgument);
            myGameDetail.updateScore(true, 0);
            if (myScore.getLevel() > myCurrentLevel) {
                myCurrentLevel++;
                myTimer.setDelay(TIMER_FREQUENCY / myCurrentLevel);
            }
        } else if (theArgument instanceof Boolean) {
            myTimer.stop();
            myAudioPlayer.stop();            
            myGameCenter.gameOver(true);
            changeName(0, NEW_GAME);
            myGameDetail.end();
            replay();
        }         
    }
    
    /**
     * Change the item's name at the position provided.
     * @param theItemAt the position
     * @param theName the name
     */
    private void changeName(final int theItemAt, final String theName) {
        final JMenuItem item = (JMenuItem) ((JFrame) SwingUtilities.getRoot(this)).
                        getJMenuBar().getMenu(0).getMenuComponent(theItemAt); 
        item.setName(theName);
        item.setText(theName);
    }
    /**
     * Ask user to replay the instant game.
     * then replay if yes.    
     */
    private void replay() {
        final int dialogResult = JOptionPane.showConfirmDialog(this, 
                                 "Do you want to replay?", "Replay",
                                                               0, 0, ICONF);     
        final JFrame frame = (JFrame) SwingUtilities.getRoot(this);
        final JMenuItem temp = (JMenuItem) 
                        frame.getJMenuBar().getMenu(0).getMenuComponent(0);
        if (dialogResult == JOptionPane.YES_OPTION) {            
            temp.setText(NEW_GAME);
            temp.setName(NEW_GAME);
            temp.setEnabled(false);
            myGameCenter.gameOver(false);
            myReplay = true;
            myTimer.start();
            final JMenuItem replay = (JMenuItem) 
                            frame.getJMenuBar().getMenu(0).getMenuComponent(1);
            replay.setEnabled(true);
            replay.setText("Stop!");
        } else {            
            temp.setEnabled(true);
        }
    }
    
    /**
     * Helper method which calls the class to show next piece.
     * @param theArgument the TetrisPiece
     */
    private void showNextPiece(final Object theArgument) {
        int scale = RATIO;
        int heightScale = RATIO - 1;
        if (((TetrisPiece) theArgument).name().equals("I")) {
            scale = I_RATIO;
            heightScale = RATIO;
        } else if (((TetrisPiece) theArgument).name().equals("O")) {
            scale = I_RATIO;
            heightScale = I_RATIO;
        }
        myNextPiecePanel.setBounds((WIDTH - ONE_EDGE_SIZE * scale) / 2,
                                   (WIDTH - ONE_EDGE_SIZE * heightScale) / 2,
                                   ONE_EDGE_SIZE * scale, ONE_EDGE_SIZE * heightScale);
        myNextPiecePanel.nextPiece((TetrisPiece) theArgument);
    }
    /**
     * Run the program.
     */
    public void run() {
        initGUI();
    }
}