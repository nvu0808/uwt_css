/* 
 * TCSS 305 - Easy Street
 */
package model;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Hop Pham
 * @version Jan 29 2018
 */
public class Truck extends AbstractVehicle {
 
    /** The banned moves after collides. */
    private static final int DEATH_TIME = 0;
    
    /**
     * Initializes the instance fields.
     * 
     * @param theX the x-coordinate
     * @param theY the y-coordinate
     * @param theDir the direction
     */
    public Truck(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
    }
    
    /** {@inheritDoc} */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {        
        return validChoice(theNeighbors);
    } 
    
    /** Return a random choice. {@inheritDoc} */
    @Override
    protected Direction finalChoice(final List<Direction> theList,
                          final Direction theChoice) {
        final Direction result;
        if (theList.isEmpty()) {
            result = getDirection().reverse();
        } else {
            result = theList.get(getRandom(theList.size()));
        }
        return result;
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
        if (theLight == Light.RED && theTerrain == Terrain.CROSSWALK) {
            checkPass = false;
        } else if (theTerrain == Terrain.GRASS || theTerrain == Terrain.WALL
                        || theTerrain == Terrain.TRAIL) {
            checkPass = false;
        }
        return checkPass;
    }    
}