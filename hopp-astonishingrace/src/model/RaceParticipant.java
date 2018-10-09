/*
 * TCSS 305 - Astonishing Race
 */
package model;

/**
 * Objects that wrap an individual racer's information need to implement this interface
 * in order for the view classes to know about the Racer.  Use this when loading
 * the Header information in the race file. 
 * 
 * @author Charles Bryan
 * @version Winter 2018
 */
public interface RaceParticipant {

    /**
     * Provides the name of the race participant.
     * @return the name of the race participant
     */
    String getName();

    /**
     * Provides the number of the race participant.
     * @return the number of the race participant
     */
    int getNumber();

    /**
     * Provides the starting distance of the racer.
     * @return the myStartingDistance the starting distance of the racer
     */
    int getStartingDistance();

}
