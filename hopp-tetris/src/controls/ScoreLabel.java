/*
 * TCSS 305 - Tetris
 */

package controls;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import util.SaveLoadGame;
import util.Scores;


/**
 * A helper class to paint the next Tetris piece.
 * 
 * @author Hop Pham
 * @version Feb 28 2018
 */
public class ScoreLabel extends JLabel {

    /**  A generated serial version UID for object Serialization. */
    private static final long serialVersionUID = 4953835749497953395L;
    
    /** A new line. */
    private static final String NEW_LINE = "<br>";

    /** Score class. */
    private final Scores myScore;
    
    /** Store the highest score.*/
    private int myHighest;
    
    /** Flag the new game for calculate score purpose. */
    private boolean myNewGame;

    /** Name of player. */
    private String myName;
    /**
     * Constructs a new game panel.
     * @param theScore the score object
     */
    public ScoreLabel(final Scores theScore) {
        super();
        myScore = theScore;
        myNewGame = false;
        setName("score");
        loadHighScore();
     
    }
    
    /**
     * Helper method to load and save score.
     */
    private void loadHighScore() {
        final SaveLoadGame load = new SaveLoadGame();
        final String[] data = load.loadScore().split(":");
        if (data.length > 1) {
            myHighest = Integer.parseInt(data[1]);
            myName = data[0];
        }        
        setUpComponent();
 
    }
    
    /**
     * Set up component.
     */
    private void setUpComponent() {  
        final String scoreString = "<html><pre><b>Highest Score:</b>   " 
                        + myHighest + " Player: " + myName + NEW_LINE 
                        + "<b>Current level:</b>   " + myScore.getLevel() + NEW_LINE
                        + "<b>Current score:</b>   " + myScore.getTotal() + NEW_LINE
                        + "<b>Completed lines:</b> " + myScore.getLine() + NEW_LINE
                        + "<b>Next level:</b>      " + myScore.getNext() + NEW_LINE + NEW_LINE
                        + "------------------------------------"
                        + "</pre></html>";
        setText(scoreString);
    }
    
    /**
     * Receives information to update current score.    
     * @param theCompleted a piece done.
     * @param theLine the number of completed line.
     */
    public void updateScore(final boolean theCompleted, final int theLine) {
        if (theCompleted && myNewGame) {
            myScore.setCompletedPiece();
        }
        
        if (theLine > 0) {
            myScore.setCompletedLines(theLine);
        }
        myNewGame = true;
        setUpComponent();
    }
    
    /**
     * Helper method to track a new game started.
     */
    public void setStart() {
        myNewGame = false;
        myScore.newGame();     
    }
    
    /**
     * Save the highest score.
     */
    public void saveHighScore() {
        if (myScore.getTotal() > myHighest) {            
            final String newName = JOptionPane.showInputDialog(null,
                                 "Enter your name to record your score:", "High score",
                                  JOptionPane.QUESTION_MESSAGE);
            if (newName != null) {
                myName = newName;
                myHighest = myScore.getTotal();
                final SaveLoadGame load = new SaveLoadGame();            
                load.updateScore(myName, myScore.getTotal());
                setUpComponent();
            }
        }
    }
}
