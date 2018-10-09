/*
 * TCSS 305 - Astonishing Race
 */
package model;

/**
 * Objects that wrap a race information need to implement this interface
 * in order for the view classes to know about the Race. Use this when loading
 * the Header information in the race file. 
 * 
 * @author Charles Bryan
 * @version Winter 2018
 */
public interface RaceInformation {

    /**
     * Provides he Total Time of the race.
     * @return the Total Time of the race
     */
    int getTotalTime();

    /**
     * Provides the length of the track as a distance.
     * @return the length of the track as a distance
     */
    int getTrackDistance();

    /**
     *  Provides the Race Name.
     * @return the Race Name
     */
    String getRaceName();

    /**
     * Provides the Track Type.
     * @return the Track Type
     */
    String getTrackType();

    /**
     * Provides the Height portion of the ratio of the track.
     * @return the Height portion of the ratio of the track
     */
    int getHeight();

    /**
     * Provides the Width portion of the ratio of the track.
     * @return the Width portion of the ratio of the track
     */
    int getWidth();

}
