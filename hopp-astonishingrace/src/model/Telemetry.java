/*
 * TCSS 305 - Astonishing Race
 */
package model;

/**
 * Objects that wrap $T (telemetry) information need to implement this interface
 * in order for the view classes to know about telemetry changes.  Use this for
 * $T lines in the race file. 
 * 
 * @author Charles Bryan
 * @version Winter 2018
 */
public interface Telemetry {

    /**
     * @return the racer's number
     */
    int getNumber();

    /**
     * @return the racer's distance around the track
     */
    double getDistance();

    /**
     * @return the racers current lap
     */
    int getLap();

}
