/* 
 * TCSS 305 - Easy Street
 */
package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import model.Atv;
import model.Direction;
import model.Light;
import model.Terrain;
import model.Truck;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit tests for class Human.
 * @author Hop Pham
 * @version Jan 29 2018
 */
public class TruckTest {

    /**
     * The number of trial.
     */
    private static final int TRIAL = 200;
    
    /** The item that I will use in the tests. */
    private Truck myTruck;
    /**
     * Test for constructor {@link model.Truck#Truck(int, int, model.Direction)}.
     */
    
    /**
     * A method to initialize the test fixture before each test.
     */
    @Before
    public void setUp() {
        myTruck = new Truck(22, 8, Direction.SOUTH);
    }    
   
    /**
     * Test method for {@link model.Truck#canPass(model.Terrain, model.Light)}.
     */
    @Test
    public void testCanPass() {
//        Trucks randomly select to go straight, turn left, or turn right.  
//        As a last resort, if none of these three directions is legal 
//        (all not streets, lights, or crosswalks), the truck turns around. 
//        Trucks drive through all traffic lights without stopping!
//        Trucks stop for red crosswalk lights, 
//        but drive through yellow or green crosswalk lights without stopping.
        
        final List<Terrain> canChoose = new LinkedList<>();
        canChoose.add(Terrain.STREET);
        canChoose.add(Terrain.CROSSWALK);
        canChoose.add(Terrain.LIGHT);
                
        // test each terrain type as a destination
        for (final Terrain destinationTerrain : Terrain.values()) {
            // try the test under each light condition
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.STREET) {
                
                    // Truck can pass STREET under any light condition
                    assertTrue("Truck should be able to pass STREET"
                               + ", with light " + currentLightCondition,
                               myTruck.canPass(destinationTerrain, currentLightCondition));
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                           // Truck can pass CROSSWALK
                           // if the light is YELLOW or GREEN but not RED
                    if (currentLightCondition == Light.RED) {
                        assertFalse("Truck should NOT be able to pass " + destinationTerrain
                            + ", with light " + currentLightCondition,
                            myTruck.canPass(destinationTerrain,
                                          currentLightCondition));
                    } else { // light is yellow or green
                        assertTrue("Truck should be able to pass " + destinationTerrain
                            + ", with light " + currentLightCondition,
                            myTruck.canPass(destinationTerrain,
                                          currentLightCondition));
                    }
                } else if (!canChoose.contains(destinationTerrain)) {
 
                    assertFalse("Truck should NOT be able to pass " + destinationTerrain
                        + ", with light " + currentLightCondition,
                        myTruck.canPass(destinationTerrain, currentLightCondition));
                }
            } 
        }
    }

    /**
     * Test method for {@link model.Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirection() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        neighbors.put(Direction.WEST, Terrain.STREET);
        
        myTruck.setX(11);
        myTruck.setY(5);
        myTruck.setDirection(Direction.EAST);
        boolean seenWest = false;
        boolean seenNorth = false;
        boolean seenEast = false;
        boolean seenSouth = false;
        int trial = 0;
        for (trial = 0; trial < TRIAL; trial++) {
            final Direction choice = myTruck.chooseDirection(neighbors);            
            if (choice == Direction.WEST) {
                seenWest = true;
            } else if (choice == Direction.NORTH) {
                seenNorth = true;
            } else if (choice == Direction.EAST) {
                seenEast = true;
            } else if (choice == Direction.SOUTH) { // this should NOT be chosen
                seenSouth = true;
            }
        }
 
        assertTrue("Truck chooseDirection() fails to select randomly "
                   + "among all possible valid choices!",
                   seenSouth && seenNorth && seenEast);
            
        assertFalse("Human chooseDirection() reversed direction when not necessary!",
                    seenWest);
    }

    /**
     * Test method for {@link model.Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseReverseDirection() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.GRASS);
        neighbors.put(Direction.EAST, Terrain.WALL);
        neighbors.put(Direction.WEST, Terrain.TRAIL);

        assertEquals("Truck must reverse", Direction.NORTH,
                     myTruck.chooseDirection(neighbors));        
    }
    
    /**
     * Test method for {@link model.Truck#getDeathTime()}.
     */
    @Test
    public void testGetDeathTime() {
        assertEquals("Wrong death time!", 0, myTruck.getDeathTime());
    }

    /**
     * Test method for {@link model.AbstractVehicle#getImageFileName()}.
     */
    @Test
    public void testGetImageFileName() {
        assertEquals("Wrong image name", "truck.gif", myTruck.getImageFileName());
    }

    /**
     * Test method for {@link model.AbstractVehicle#getDirection()}.
     */
    @Test
    public void testGetDirection() {
        assertEquals("Wrong direction!", Direction.SOUTH, myTruck.getDirection());
    }

    /**
     * Test method for {@link model.AbstractVehicle#getX()}.
     */
    @Test
    public void testGetX() {
        assertEquals("Wrong x-coordinate!", 22, myTruck.getX());
    }

    /**
     * Test method for {@link model.AbstractVehicle#getY()}.
     */
    @Test
    public void testGetY() {
        assertEquals("Wrong y-coordinate!", 8, myTruck.getY());
    }

    /**
     * Test method for {@link model.AbstractVehicle#isAlive()}.
     */
    @Test
    public void testIsAlive() {
        assertTrue("alive status wrong!", myTruck.isAlive());
    }

    /**
     * Test method for {@link model.AbstractVehicle#poke()}.
     */
    @Test
    public void testPoke() {
        final Atv aTV = new Atv(1, 1, Direction.NORTH);
        myTruck.collide(aTV);
        myTruck.poke();        
        assertEquals("Wrong result!", true, myTruck.isAlive());
        aTV.collide(myTruck);
        aTV.poke();
        assertEquals("Wrong result!", false, aTV.isAlive());
        for (int i = 0; i < 30; i++) {
            aTV.poke();
        }
        assertEquals("Wrong result!", true, aTV.isAlive());
    }

    /**
     * Test method for {@link model.AbstractVehicle#reset()}.
     */
    @Test
    public void testReset() {
        myTruck.setX(12);
        myTruck.setY(4);
        myTruck.setDirection(Direction.NORTH);
        myTruck.reset();
        assertEquals("Wrong x-coordinate!", 22, myTruck.getX());
        assertEquals("Wrong y-coordinate!", 8, myTruck.getY());
        assertEquals("Wrong y-coordinate!", Direction.SOUTH, myTruck.getDirection());
    }

    /**
     * Test method for {@link model.AbstractVehicle#setDirection(model.Direction)}.
     */
    @Test
    public void testSetDirection() {
        myTruck.setDirection(Direction.NORTH);
        assertEquals("Wrong y-coordinate!", Direction.NORTH, myTruck.getDirection());
    }

    /**
     * Test method for {@link model.AbstractVehicle#setX(int)}.
     */
    @Test
    public void testSetX() {
        myTruck.setX(12);
        assertEquals("Wrong x-coordinate!", 12, myTruck.getX());
    }

    /**
     * Test method for {@link model.AbstractVehicle#setY(int)}.
     */
    @Test
    public void testSetY() {
        myTruck.setY(8);
        assertEquals("Wrong y-coordinate!", 8, myTruck.getY());
    }
    
    /**
     * Test method for {@link model.AbstractVehicle#collide(theOther vehicle)}.
     */
    @Test
    public void testcollide() {
        final Atv aTV = new Atv(1, 1, Direction.NORTH);
        myTruck.collide(aTV);
        assertEquals("Wrong result!", true, myTruck.isAlive());
        aTV.collide(myTruck);
        assertEquals("Wrong result!", false, aTV.isAlive());
    }
}