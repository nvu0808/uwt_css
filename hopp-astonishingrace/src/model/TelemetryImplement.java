/*
 * TCSS 305 - Astonishing Race
 */
package model;

/**
 * Telemetry for Race.
 * @author Hop Pham
 * @version Feb 6 2018
 */
public class TelemetryImplement implements Telemetry {
    
    /** Store racer ID. */
    private final int myID;
    /** Store distance. */
    private final double myDistance;
    /** Store racer lap. */
    private final int myLap;

    /**
     * Constructor.
     * @param theID the racer'id
     * @param theDistance the distance
     * @param theLap the lap
     */
    public TelemetryImplement(final int theID, final double theDistance, final int theLap) {
        myID = theID;
        myDistance = theDistance;
        myLap = theLap;
    }
    
    @Override
    public int getNumber() {
        return myID;
    }

    @Override
    public double getDistance() {
        
        return myDistance;
    }

    @Override
    public int getLap() {

        return myLap;
    }

}
