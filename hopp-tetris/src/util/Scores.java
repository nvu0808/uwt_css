package util;


/**
 * Class to control keys.
 * 
 * @author Hop Pham
 * @version Feb 28 2018
 */

public class Scores {
    
    /** Score for 1 line cleared base on one level. */
    private static final int SCORE_LINE1 = 40;

    /** Score for 2 lines cleared base on one level. */
    private static final int SCORE_LINE2 = 100;
    
    /** Score for 3 lines cleared base on one level. */
    private static final int SCORE_LINE3 = 300;
    
    /** Score for 4 lines cleared base on one level. */
    private static final int SCORE_LINE4 = 1200;
    
    /** The cleared line need to reach next level. */
    private static final int LINES = 5;

    /** The number of line .*/
    private static final int THREE = 3;
    
    /** The number of line .*/
    private static final int FOUR = 4;
    
    /** Total score. */
    private int myScore;
    
    /** The number of cleared line. */
    private int myLines;
    
    /**
     * Construct Score.
     */
    public Scores() {
        myScore = 0;
        myLines = 0;
    }
    
   
    /**
     * Get total score.
     * @return the score.
     */
    public int getTotal() {
        return myScore;
    }
    
    /** 
     * Get total cleared line.
     * @return the total line.
     */
    public int getLine() {
        return myLines;
    }
    
    /**
     * Get the number of line which need to reach the next level.
     * @return the number of line needed.
     */
    public int getNext() {
        return LINES - myLines % LINES;
    }
    
    /**
     * Get the current level.
     * @return the level
     */
    public int getLevel() {
        int level = 1;
        if (myLines > 0) {
            level = myLines / LINES + 1;
        }
        return level;
    }
    
    /**
     * Update score and level after completed line.
     * @param theNumber the number of cleared line.
     */
    public void setCompletedLines(final int theNumber) {

        switch (theNumber) {
            case 1:
                myScore +=  SCORE_LINE1 * getLevel();
                break;
            case 2:
                myScore +=  SCORE_LINE2 * getLevel();
                break;
            case THREE:
                myScore +=  SCORE_LINE3 * getLevel();
                break;
            case FOUR:
                myScore +=  SCORE_LINE4 * getLevel();
                break;
            default:
                break;
        }
        myLines += theNumber;
    }
    /**
     * Update score after each piece done.
     */
    public void setCompletedPiece() {
        myScore += FOUR;
    }
    /**
     * Clear all current score.
     */
    public void newGame() {
        myScore = 0;
        myLines = 0;
    }
}
