/*
 * TCSS 305 - Tetris
 */

package controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Block;
import model.Board;


/**
 * Center panel which controls the game. * 
 * @author Hop Pham
 * @version Feb 28 2018
 */
public class GameCenterControls extends JPanel implements KeyListener {

    /**  A generated serial version UID for object Serialization. */
    private static final long serialVersionUID = 5944162416430906760L;

    /**
     * The default panel width.
     */
    private static final int WIDTH = 400;

    /**
     * The default panel height.
     */
    private static final int HEIGHT = 800;

    /**
     * The background color of the panel.
     */
    private static final Color BACKGROUND_COLOR = Color.BLACK;

    /** A new line. */
    private static final String NEW_LINE = "<br>";
    /**
     * The color to paint with.
     */
    private static final Color FOREGROUND_COLOR = Color.BLACK;

    /** The width - height of one square piece. */
    private static final int ONE_EDGE_SIZE = 40;

    /** Empty border. */
    private static final int BORDER_SIZE = 0;

    /** To flip y coordinate. */
    private static final int FLIP = 19;
    
    /** Time delay for hard mode. */
    private static final int HARD = 100;
    
    /** Time delay for easy mode. */
    private static final int EASY = 1000;

    /** Paused string. */
    private static final String PAUSED = "PAUSED";
    
    // Instance Fields
    
    /** List of base rectangle which will build the Tetris. */
    private final List<Rectangle2D> myShapes = new ArrayList<>();
    
    /** Observable object. */
    private final Board myBoard;

    /** Timer object. */
    private final Timer myTimer;
    // Constructor

    /** Game mode. */
    private boolean myMode = true;
    
    /** Map store runnable functions related to keyPress action. */
    private final Map<Integer, Runnable> myKeyMap;
    
//    /** To display when user pause. */
//    private final JPanel myDialog;

    /** for double keys pressed. */
    private int myCount;

    /** flag for hardest mode. */
    private boolean myHardest;

    /** Game status. */
    private final JLabel myMessage;
    /** A space for adjust center message. */
    private final JLabel mySpace;
    
    /**
     * Constructs a new game panel.
     * @param theBoard Observable
     * @param theTimer timer
     */
    public GameCenterControls(final Board theBoard, final Timer theTimer) {
        super(new BorderLayout());
        myHardest = false;
        myBoard = theBoard;
        myTimer = theTimer;
        myMessage = new JLabel(PAUSED);
        mySpace = new JLabel("");
        setComponents();
        myKeyMap = new HashMap<>();
        addKeyListener(this);
        buildKeyMap();
        
    }
    
    /** Helper method to setup component. */
    private void setComponents() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(
                     BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        this.setFocusable(true);
        myMessage.setForeground(Color.WHITE);
        myMessage.setFont(new Font("Serif", Font.BOLD, ONE_EDGE_SIZE));
        myMessage.setVisible(false);
        this.addMouseListener(theEvent -> showMe());
        add(mySpace, BorderLayout.LINE_START);
        add(myMessage, BorderLayout.CENTER);
    }

    private void showMe() {
        System.out.println("Mouse");
    }
    /**
     * Paints the current path.
     * 
     * @param theGraphics The graphics context to use for painting.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setPaint(FOREGROUND_COLOR);
       
        for (final Rectangle2D rec:myShapes) {
            g2d.setPaint(Color.RED);
            g2d.fill(rec);
        }
        
        if (myMode) {
            for (int i = 0; i < WIDTH / ONE_EDGE_SIZE; i++) {
                for (int j = 0; j < HEIGHT / ONE_EDGE_SIZE; j++) {
                    g2d.setPaint(Color.WHITE);
                    g2d.draw(new Rectangle2D.Double(i * ONE_EDGE_SIZE,
                                                    j * ONE_EDGE_SIZE,
                                                    ONE_EDGE_SIZE,
                                                    ONE_EDGE_SIZE));
                }
            }
        }

        
    } 
    
    /**
     * Repaint the tetris piece at new position.
     * @param theArgument the tetris block
     */
    public void pieceChanged(final Block[][] theArgument) {        
        myShapes.clear();
        for (int i = 0; i < theArgument.length; i++) {
            for (int j = 0; j < theArgument[i].length; j++) {
                int y = FLIP - i;
                if (myHardest) {
                    y = i;
                }
                if (theArgument[i][j] != null) {
                    myShapes.add(new Rectangle2D.Double(j * ONE_EDGE_SIZE,
                                                        y * ONE_EDGE_SIZE,
                                                        ONE_EDGE_SIZE, ONE_EDGE_SIZE));
                }
            }
        }
        repaint();
    }
    
    /** 
     * Show the game is over.
     * @param theOver over or new game. 
     */
    public void gameOver(final boolean theOver) {        
        if (theOver) {
            myMessage.setText("GAME OVER");
            myMessage.setVisible(theOver);
            mySpace.setText("........................");
        } else {
            myMessage.setText(PAUSED);
            myMessage.setVisible(theOver);
            mySpace.setText("........................................");
        }
    }
    
    /** 
     * Show the game is over, user end the game.
     */
    public void gameEnd() {        
        myMessage.setText("GAME OVER!");            
        myMessage.setVisible(true);
        mySpace.setText(".......................");
    }
    
    
    /**
     * Change the game mode.
     * Easy mode will display the base background, none otherwise.
     * @param theMode boolean
     */
    public void changeMode(final boolean theMode) {
        myMode = theMode;
        repaint();
    }
    
    /** drop the tetrispiece one. */
    private void drop() {
        if (myTimer.isRunning()) {
            myBoard.drop();
        }        
    }
    /** move the tetrispiece to the left. */
    private void left() {
        if (myTimer.isRunning()) {
            myBoard.left();
        }        
    }
    /** move the tetrispiece to the right. */
    private void right() {
        if (myTimer.isRunning()) {
            myBoard.right();
        }        
    }
    /** drop down the tetrispiece. */
    private void down() {
        if (myTimer.isRunning()) {
            myBoard.down();
        }
    }
    /** rotate CW the tetrispiece. */
    private void rotateCW() {
        if (myTimer.isRunning()) {
            myBoard.rotateCW();
        }
    }
    /** rotate CCW the tetrispiece. */
    private void rotateCCW() {
        if (myTimer.isRunning()) {
            myBoard.rotateCCW();
        }
    }
    /** pause or resume the current game. */
    private void pause() {
        if (myTimer.isRunning()) {
            myTimer.stop();            
            myMessage.setVisible(true);
//            myDialog.setVisible(true);
        } else if (myShapes.size() > 1 && myMessage.getText().equals(PAUSED)) {
            myTimer.start();
            myMessage.setVisible(false);
//            myDialog.setVisible(false);
        }
//        myDialog.setBackground(new Color(0, 0, 0, OPACITY));
    }
    /** build a map of actions which related to keyPress. */
    private void buildKeyMap() {
        myKeyMap.clear();
        myKeyMap.put(KeyEvent.VK_RIGHT, this::right);
        myKeyMap.put(KeyEvent.VK_KP_RIGHT, this::right);
        myKeyMap.put(KeyEvent.VK_D, this::right);
        myKeyMap.put(KeyEvent.VK_LEFT, this::left);
        myKeyMap.put(KeyEvent.VK_KP_LEFT, this::left);
        myKeyMap.put(KeyEvent.VK_A, this::left);
        myKeyMap.put(KeyEvent.VK_DOWN, this::down);
        myKeyMap.put(KeyEvent.VK_KP_DOWN, this::down);
        myKeyMap.put(KeyEvent.VK_S, this::down);
        myKeyMap.put(KeyEvent.VK_UP, this::rotateCW);
        myKeyMap.put(KeyEvent.VK_Q, this::rotateCCW);
        myKeyMap.put(KeyEvent.VK_KP_UP, this::rotateCW);
        myKeyMap.put(KeyEvent.VK_W, this::rotateCW);
        myKeyMap.put(KeyEvent.VK_SPACE, this::drop);     
        myKeyMap.put(KeyEvent.VK_P, this::pause);
    }
    
    @Override
    public void keyPressed(final KeyEvent theEvent) {
        if (myKeyMap.containsKey(theEvent.getKeyCode())) {
            myKeyMap.get(theEvent.getKeyCode()).run();
        }
        if (KeyEvent.VK_CONTROL == theEvent.getKeyCode()) {
            myCount = 1;
        }
        if (myCount == 1 && KeyEvent.VK_E == theEvent.getKeyCode()) {
            myMode = true;
            updateText(this.getParent(), "Easy");
            myTimer.setDelay(EASY);
            myHardest = false;
        }
        if (myCount == 1 && KeyEvent.VK_H == theEvent.getKeyCode()) {
            updateText(this.getParent(), "Hard");
            myTimer.setDelay(HARD);
            myMode = false;
            myHardest = false;
        }
        if (myCount == 1 && KeyEvent.VK_T == theEvent.getKeyCode()) {
            updateText(this.getParent(), "Hardest");
            myTimer.setDelay(HARD);
            myMode = false;
            myHardest = true;
        }
        
    }
    
    @Override
    public void keyReleased(final KeyEvent theEvent) {        
        if (KeyEvent.VK_CONTROL == theEvent.getKeyCode()) {
            myCount = 0;
        }
    }

    @Override
    public void keyTyped(final KeyEvent theEvent) {
        
        
    }
    
    /**
     * Helper method to enable component after loading file.
     * @param theContainer the container
     * @param theMode string represents mode
     */
    public void updateText(final Container theContainer, final String theMode) {
        
        final Component[] components = theContainer.getComponents();
        for (final Component component : components) {
            
            if (component instanceof Container) {
                updateText((Container) component, theMode);
            }
            if (component instanceof JLabel && ((JLabel) component).getName() != null
                            && ((JLabel) component).getName().equals("gameMode")) { 
                final String gameModeString = "<html><pre><b>Current mode:</b> " 
                                + theMode + NEW_LINE
                                + "User can switch the mode at anytime." + NEW_LINE
                                + "<b>Crlt + E:</b>   Esay mode" + NEW_LINE
                                + "Nomal speed and grid showed." + NEW_LINE
                                + "<b>Crlt + H:</b>   Hard mode" + NEW_LINE
                                + "No grid and speed increased." + NEW_LINE
                                + "<b>Crlt + T:</b>   Hardest mode (Opposite)" + NEW_LINE
                                + "Tetris piece will appear at bottom." + NEW_LINE
                                + "The next piece flipped horizontally." + NEW_LINE
                                + "This mode inherited hard mode." + NEW_LINE
                                + "</pre></html>";
                ((JLabel) component).setText(gameModeString);
            } 
            
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
