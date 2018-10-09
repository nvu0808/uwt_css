/*
 * TCSS 305 - Astonishing Race
 */
package model;

import java.io.File;
import java.io.IOException;

/**
 * Define the actions that may be performed on a Race object. 
 * 
 * @author Charles Bryan
 * @version 1
 */
public interface RaceControls {

    /**
     * Advances the race's internal "clock" by 1 millisecond. All registered observers 
     * will be notified of both the "time" change and any messages that occur during this 
     * advance. If the race is advanced beyond its total amount of time, it will not throw an 
     * exception but notify all registered observer no time remains. 
     */
    void advance();
    
    /**
     * Advances the race's internal "clock" by n milliseconds where n is the value of
     * theMillsecond.  All registered observers will be notified of both the "time" change and 
     * any messages that occur during this advance. If the race is advanced beyond its 
     * total amount of time, it will not throw an exception but notify all registered 
     * observer no time remains. 
     * 
     * @param theMillisecond the amount of milliseconds to advance the race
     */
    void advance(int theMillisecond);
    
    /**
     * Move the Race's internal "clock" to n milliseconds where n is the value of 
     * theMillisecond. All registered observers will be notified of the "time" change. 
     * Starting at theMilliscond and working backward in "time," all register observers 
     * will be notified of the most recent leaderboard and telemetry (for all racers)
     * message. 
     * 
     * @param theMillisecond the time to move the race's internal "clock" to
     * @throws IllegalArgumentException when theMillisecond is negative or greater
     * than the length of the race. 
     */
    void moveTo(int theMillisecond);
    
    /**
     * Toggles participant information ON/OFF. When the race advances, it notifies
     * observers of messages that occur during the advance. Race participants may be 
     * toggled off or on such that, when on, telemetry messages relating to that 
     * participant id will be included in the notification(s). When off, telemetry 
     * messages relating to that participant id will NOT be included in the notification(s).  
     * 
     * @param theParticpantID the participant to toggle on/off.
     * @param theToggle toggle a participant on (true) or off (false)
     */
    void toggleParticipant(int theParticpantID, boolean theToggle);
        
    /**
     * Load a file containing race information. All registered observers will be notified 
     * or progress updates during the load. All registered observers will be notified of 
     * information in the header message when the loading process completes.  
     * See external documentation for the appropriate file format. 
     * 
     * @param theRaceFile the file to load.
     * @throws IOException when the file is not in the appropriate format
     */
    void loadRace(File theRaceFile) throws IOException;
    
}
