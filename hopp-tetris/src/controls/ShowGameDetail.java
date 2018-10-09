/*
 * TCSS 305 - Tetris
 */

package controls;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.Scores;


/**
 * Class to control keys.
 * 
 * @author Hop Pham
 * @version Feb 28 2018
 */
public class ShowGameDetail extends JPanel {

    /**  A generated serial version UID for object Serialization. */
    private static final long serialVersionUID = 5944162416430906760L;

    /**
     * The panel width.
     */
    private static final int WIDTH = 300;

    /**
     * The panel height.
     */
    private static final int HEIGHT = 500;
  
    /** A new line. */
    private static final String NEW_LINE = "<br>";
  
    /** Space between two components. */
    private static final int SEPARATE = 25;
    
    /** Displays score and level. */
    private final ScoreLabel myScoreLabel;
    

    // Constructor
    
    /**
     * Constructs a new game panel.
     * @param theScore the score object
     */
    public ShowGameDetail(final Scores theScore) {
        super(new FlowLayout());
        myScoreLabel = new ScoreLabel(theScore);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setUpComponent();
    }
    
    /**
     * Helper method add component.
     * @param thePanel panel
     * @param theComp component
     * @param theFill to fill
     * @param theVGap the vertical gap
     * @param theHGap the horizontal gap
     */
    private void addComp(final JPanel thePanel, final JComponent theComp,
                         final int theFill, final int theVGap, final int theHGap) {
        
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;   
        gbc.insets = new Insets(theVGap, theHGap, theVGap, SEPARATE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = theFill;
        gbc.weightx = 1;
        gbc.weighty = 1;   
        
        thePanel.add(theComp, gbc);
    }
    
    /**
     * Set up component.
     */
    private void setUpComponent() {   
        final JPanel gridBagPanelKey = new JPanel(new GridBagLayout());
        gridBagPanelKey.setBorder(
            BorderFactory.createTitledBorder("Key Controls"));
        gridBagPanelKey.setOpaque(true);
        
        final JLabel keyInfo = new JLabel();
        keyInfo.setName("keyInfo");
        final String key = "<html><pre><b>Move Left:</b>     left arrow, A, and a" + NEW_LINE
                         + "<b>Move Right:</b>    right arrow, D, and d" + NEW_LINE
                         + "<b>Rotate CW:</b>     up arrow, W, and w" + NEW_LINE
                         + "<b>Rotate CCW:</b>    Q, and q" + NEW_LINE
                         + "<b>Move Down:</b>     down arrow, S, and s" + NEW_LINE
                         + "<b>Drop:</b>          space" + NEW_LINE
                         + "<b>Pause/Resume:</b>  p and P</pre>" + NEW_LINE
                         + "</html>";
        keyInfo.setText(key);
        
        
        addComp(gridBagPanelKey, keyInfo, GridBagConstraints.BOTH, 0, 0);
        
        add(gridBagPanelKey);
        final JPanel gridBagPanelMode = new JPanel(new GridBagLayout());
        gridBagPanelMode.setBorder(
            BorderFactory.createTitledBorder("Game Mode"));
        gridBagPanelMode.setOpaque(true);
        final JLabel modeString = new JLabel();
        modeString.setName("gameMode");
        final String gameModeString = "<html><pre><b>Current mode:</b> Easy" + NEW_LINE
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
        modeString.setText(gameModeString);
        
        addComp(gridBagPanelMode, modeString, GridBagConstraints.BOTH, 0, 0);
        
        add(gridBagPanelMode);   
        
        final JPanel gridBagPanelScore = new JPanel(new GridBagLayout());
        gridBagPanelScore.setBorder(
            BorderFactory.createTitledBorder("Score"));
        gridBagPanelScore.setOpaque(true);

        addComp(gridBagPanelScore, myScoreLabel, GridBagConstraints.BOTH, 0, 0);
        add(gridBagPanelScore);
    }
    
    /**
     * Receives information to update current score.    
     * @param theCompleted a piece done.
     * @param theLine the number of completed line.
     */
    public void updateScore(final boolean theCompleted, final int theLine) {
        myScoreLabel.updateScore(theCompleted, theLine);
    }
    
    /**
     * Helper method to track a new game started.
     */
    public void start() {
        myScoreLabel.setStart();
    }
    
    /**
     * Call to check if player get higher score then save it.
     */
    public void end() {
        myScoreLabel.saveHighScore();
    }
}
