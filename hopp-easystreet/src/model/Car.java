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
public class Car extends AbstractVehicle {
 
    /** The banned moves after collides. */
    private static final int DEATH_TIME = 10;
    
    /**
     * Initializes the instance fields.
     * 
     * @param theX the x-coordinate
     * @param theY the y-coordinate
     * @param theDir the direction
     */
    public Car(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
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
                        && (theLight == Light.RED || theLight == Light.YELLOW)) {
            checkPass = false;
        } else if (theTerrain == Terrain.GRASS || theTerrain == Terrain.WALL
                        || theTerrain == Terrain.TRAIL) {
            checkPass = false;
        }
        return checkPass;
    }    
}