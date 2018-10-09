/*
 * TCSS 305 - Astonishing Race
 */
package model;

/**
 * Racer for Race.
 * @author Hop Pham
 * @version Feb 6 2018
 */
public class RaceParticipantImplement implements RaceParticipant {
    /** The id. */
    private final int myID;
    /** The starting distance. */
    private final int myStart;
    /** The name. */
    private final String myName;
    
    /**
     * Constructor.
     * @param theName the name
     * @param theID the id
     * @param theStart the start distance
     */
    public RaceParticipantImplement(final int theID, final String theName,
                                    final int theStart) {
        myID = theID;
        myName = theName;
        myStart = theStart;
    }

    @Override
    public String getName() {
        
        return myName;
    }

    @Override
    public int getNumber() {
        
        return myID;
    }

    @Override
    public int getStartingDistance() {
        
        return myStart;
    }

}
