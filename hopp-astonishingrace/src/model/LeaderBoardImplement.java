/*
 * TCSS 305 - Astonishing Race
 */
package model;

/**
 * LeaderBoard for Race.
 * @author Hop Pham
 * @version Feb 6 2018
 */
public class LeaderBoardImplement implements LeaderBoard {

    /** The leaderboard. */
    private final int[] myLeaderBoard;
    
    /**
     * Constructor.
     * @param theLeaderBoard array store racer
     */
    public LeaderBoardImplement(final int[] theLeaderBoard) {
        myLeaderBoard = theLeaderBoard.clone();
    }
    
    @Override
    public int[] getLeaderBoard() {
        return myLeaderBoard.clone();
    }
}
