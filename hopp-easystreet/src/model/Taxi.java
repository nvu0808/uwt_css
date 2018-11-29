/* 
 * TCSS 305 - Easy Street
 */
package model;

import java.util.Map;

/**
 * 
 * @author Hop Pham
 * @version Jan 29 2018
 */
public class Taxi extends AbstractVehicle {
 
    /** The banned moves after collides. */
    private static final int DEATH_TIME = 10;
    
    /** The banned moves after collides. */
    private static final int STOP_REDCROSSWALK_CYCLES = 3;
    
    /** The clock which use to count the stoped cycle.*/
    private int myStopCount;
    /**
     * Initializes the instance fields.
     * 
     * @param theX the x-coordinate
     * @param theY the y-coordinate
     * @param theDir the direction
     */
    public Taxi(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
        myStopCount = 0;
    }
    
    /** {@inheritDoc} */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {        
        return validChoice(theNeighbors);
    } 
    
    /** {@inheritDoc} */
    @Override
    public int getDeathTime() {
        return DEATH_TIME;
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean checkPass = true;
        if (theLight == Light.RED && theTerrain == Terrain.LIGHT) {
            checkPass = false;
        } else if (theTerrain == Terrain.CROSSWALK 
                        && theLight == Light.RED) {
            checkPass = false;
            myStopCount++;
        } else if (theTerrain == Terrain.GRASS || theTerrain == Terrain.WALL
                        || theTerrain == Terrain.TRAIL) {
            checkPass = false;
        }
        if (myStopCount == STOP_REDCROSSWALK_CYCLES) {
            checkPass = true;
            myStopCount = 0;
        }
        return checkPass;
    }    
}