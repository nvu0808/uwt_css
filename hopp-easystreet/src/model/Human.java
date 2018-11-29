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
public class Human extends AbstractVehicle {
 
    /** The banned moves after collides. */
    private static final int DEATH_TIME = 50;
    
    /**
     * Initializes the instance fields.
     * 
     * @param theX the x-coordinate
     * @param theY the y-coordinate
     * @param theDir the direction
     */
    public Human(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
    }
    
    /** {@inheritDoc} */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {        
        Direction choice = null;
        final List<Direction> canChoose = new LinkedList<>();
        for (final Map.Entry<Direction, Terrain> entry : theNeighbors.entrySet()) {
            final Direction availableDir = entry.getKey();
            if (entry.getValue() == Terrain.CROSSWALK
                            && availableDir != getDirection().reverse()) {
                choice = availableDir;                
            } else if (entry.getValue() == Terrain.GRASS
                            && availableDir != getDirection().reverse()) {
                canChoose.add(availableDir);                
            }
        }
        choice = finalChoice(canChoose, choice);
        return choice;
    } 
    
    /** {@inheritDoc} */
    @Override
    protected Direction finalChoice(final List<Direction> theList,
                                  final Direction theChoice) {
        Direction result = theChoice;
        if (theChoice == null && theList.isEmpty()) { //no option
            result = getDirection().reverse();
        } 
        if (theChoice == null && !theList.isEmpty()) { //pick random direction
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
        boolean checkPass = false;
        if (theTerrain == Terrain.GRASS) {
            checkPass = true;
        } else if (theTerrain == Terrain.CROSSWALK) {
            checkPass = checkLight(theLight);
        }
        return checkPass;
    }
    
    /**
     * Check the Light.
     * @param theLight the light at current moment
     * @return true false
     */
    private boolean checkLight(final Light theLight) {
        return theLight == Light.RED || theLight == Light.YELLOW;
    }
    
}