/* 
 * TCSS 305 - Easy Street
 */
package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * 
 * @author Hop Pham
 * @version Jan 29 2018
 */
public abstract class AbstractVehicle implements Vehicle {    
  
    // instance fields
   
    /** The x-coordinate of this Vehicle. */
    private int myX;
    
    /** The initial x-coordinate of this Vehicle. */
    private final int myInitialX;
    
    /** The initial y-coordinate of this Vehicle. */
    private final int myInitialY;
    
    /** The initial direction of this Vehicle. */
    private final Direction myInitialDirection;
    
    /** The y-coordinate of this Vehicle. */
    private int myY;
    
    /** The direction of this Vehicle. */
    private Direction myDirection;
    
    /** The living status of this Vehicle. */
    private boolean myStatus;    
    
    /** The poke to track. */
    private int myPoke;
    
    /**
     * Initialize the instance fields.
     * 
     * @param theX the x-coordinate
     * @param theY the y-coordinate
     * @param theDir the direction
     */
    public AbstractVehicle(final int theX, final int theY, final Direction theDir) {
        super();
        myX = theX;
        myInitialX = theX;
        myY = theY;
        myInitialY = theY;
        myInitialDirection = theDir;
        myDirection = theDir;
        myStatus = true;
        myPoke = 0;
    }    
 
    /** {@inheritDoc} */
    @Override
    public abstract boolean canPass(Terrain theTerrain, Light theLight);
    
    /** {@inheritDoc} */
    @Override
    public abstract Direction chooseDirection(Map<Direction, Terrain> theNeighbors);
    
    /** {@inheritDoc} */
    @Override
    public void collide(final Vehicle theOther) {
        if (getDeathTime() > theOther.getDeathTime() 
                        && myStatus && theOther.isAlive()) {
            myStatus = false;
        }
    }

    /** {@inheritDoc} */
    @Override
    public abstract int getDeathTime();

    /** {@inheritDoc} */
    @Override
    public String getImageFileName() {
        final StringBuilder builder = new StringBuilder(128);
        builder.append(getClass().getSimpleName().toLowerCase(Locale.ENGLISH));
        if (!isAlive()) {
            builder.append("_dead");
        }
        builder.append(".gif");
        
        return builder.toString();
    }

    /** {@inheritDoc} */
    @Override
    public Direction getDirection() {
        return myDirection;
    }

    /** {@inheritDoc} */
    @Override
    public int getX() {
        return myX;
    }

    /** {@inheritDoc} */
    @Override
    public int getY() {
        return myY;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isAlive() {
        return myStatus;
    }

    /** {@inheritDoc} */
    @Override
    public void poke() {
        if (!isAlive()) {
            myPoke++;
        }
        if (myPoke == getDeathTime()) {
            myStatus = true;
            myPoke = 0;
            myDirection = Direction.random();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void reset() {
        myX = myInitialX;
        myY = myInitialY;
        myDirection = myInitialDirection;
        myStatus = true;
    }

    /** {@inheritDoc} */
    @Override
    public void setDirection(final Direction theDir) {
        myDirection = theDir;
    }

    /** {@inheritDoc} */
    @Override
    public void setX(final int theX) {
        myX = theX;
    }

    /** {@inheritDoc} */
    @Override
    public void setY(final int theY) {
        myY = theY;
    }
    
    /**
     * Return the random number which relates to the limit.
     * @param theLimit how many possible choice
     * @return the integer
     */
    protected int getRandom(final int theLimit) {
        final Random rd = new Random();
        return rd.nextInt(theLimit);
    }
    
    /**
     * Return the final choice.
     * @param theList list of possible choice
     * @param theChoice a chosen direction
     * @return the final choice
     */
    protected Direction finalChoice(final List<Direction> theList,
                          final Direction theChoice) {
        Direction result = theChoice;
        if (theChoice == null && theList.isEmpty()) { //no option
            result = getDirection().reverse();
        } else if (theChoice == null && theList.contains(myDirection)) { //straight
            result = myDirection;
        } else if (theChoice == null 
                        && theList.contains(myDirection.left())) { //pick left if not straight
            result = myDirection.left();
        } else if (theChoice == null && theList.contains(myDirection.right())) {
            result = myDirection.right();
        }
        return result;
    }
    
    /**
     * Return the direction which yields straight, left then right priority.
     * @param theNeighbors The map of neighboring terrain
     * @return the direction
     */
    protected Direction validChoice(final Map<Direction, Terrain> theNeighbors) {
        Direction choice = null;
        final List<Direction> canChoose = new LinkedList<>();
        for (final Map.Entry<Direction, Terrain> entry : theNeighbors.entrySet()) {
            final Direction availableDir = entry.getKey();
            if ((entry.getValue() != Terrain.GRASS && entry.getValue() != Terrain.WALL
                            && entry.getValue() != Terrain.TRAIL)
                            && availableDir != getDirection().reverse()) {
                canChoose.add(availableDir);                
            }
        }
        choice = finalChoice(canChoose, choice);
        return choice;
    }
}
