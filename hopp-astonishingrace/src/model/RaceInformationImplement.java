/*
 * TCSS 305 - Astonishing Race
 */
package model;

import java.util.Map;

/**
 * Header for Race.
 * @author Hop Pham
 * @version Feb 6 2018
 */
public class RaceInformationImplement implements RaceInformation {
    
    /** Store the header. */
    private final Map<String, String> myHeader;

    /**
     * Constructor.
     * @param theHeader the map
     */
    public RaceInformationImplement(final Map<String, String> theHeader) {
        myHeader = theHeader;
    }
    @Override
    public int getTotalTime() {
        return Integer.parseInt(myHeader.get("TIME"));
    }

    @Override
    public int getTrackDistance() {
        return Integer.parseInt(myHeader.get("DISTANCE"));
    }

    @Override
    public String getRaceName() {
        return myHeader.get("RACE");
    }

    @Override
    public String getTrackType() {
        return myHeader.get("TRACK");
    }

    @Override
    public int getHeight() {
        return Integer.parseInt(myHeader.get("HEIGHT"));
    }

    @Override
    public int getWidth() {
        return Integer.parseInt(myHeader.get("WIDTH"));
    }

}
