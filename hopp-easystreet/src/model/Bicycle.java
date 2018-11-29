/* 
 * TCSS 305 - Easy Street
 */
package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Hop Pham
 * @version Jan 29 2018
 */
public class Bicycle extends AbstractVehicle {
 
    /** The banned moves after collides. */
    private static final int DEATH_TIME = 30;
    
    /**
     * Initializes the instance fields.
     * 
     * @param theX the x-coordinate
     * @param theY the y-coordinate
     * @param theDir the direction
     */
    public Bicycle(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
    }
    
    /** {@inheritDoc} */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {        
        Direction choice = null;
        final List<Direction> canChoose = new LinkedList<>();
        for (final Map.Entry<Direction, Terrain> entry : theNeighbors.entrySet()) {
            final Direction availableDir = entry.getKey();
            if (entry.getValue() == Terrain.TRAIL
                            && availableDir != getDirection().reverse()) {
                choice = availableDir;                
            } else if ((entry.getValue() != Terrain.GRASS && entry.getValue() != Terrain.WALL)
                            && availableDir != getDirection().reverse()) {
                canChoose.add(availableDir);                
            }
        }
        choice = finalChoice(canChoose, choice);
        return choice;
    } 
    
    /** {@inheritDoc} */
    @Override
    public int getDeathTime() {
        return DEATH_TIME;
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean checkPass = false;
        if ((theLight == Light.GREEN || theTerrain != Terrain.WALL)
                        || theTerrain == Terrain.TRAIL) {
            checkPass = true;
        } else if (theTerrain != Terrain.LIGHT) {
            checkPass = true;
        } 
        return checkPass;
    }    
}