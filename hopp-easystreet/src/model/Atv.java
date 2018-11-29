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
public class Atv extends AbstractVehicle {
 
    /** The banned moves after collides. */
    private static final int DEATH_TIME = 20;
    
    /**
     * Initializes the instance fields.
     * 
     * @param theX the x-coordinate
     * @param theY the y-coordinate
     * @param theDir the direction
     */
    public Atv(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
    }
    
    /** {@inheritDoc} */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {        
        Direction choice = null;
        final List<Direction> canChoose = new LinkedList<>();
        for (final Map.Entry<Direction, Terrain> entry : theNeighbors.entrySet()) {
            if (entry.getValue() != Terrain.WALL
                            && entry.getKey() != getDirection().reverse()) {
                canChoose.add(entry.getKey());                
            }
        }
        choice = canChoose.get(getRandom(canChoose.size()));
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
        boolean checkPass = true;
        if (theTerrain == Terrain.WALL) {
            checkPass = false;
        }   
        return checkPass;
    }
    
}