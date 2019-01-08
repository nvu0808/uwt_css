/*
 * TCSS 342 Data Structures
 * Assignment 5 - MazeGenerator
 */
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Runs the Maze program.
 * 
 * @author Hop Pham
 * @version May 2018
 */

public final class Main {
    
    /**
     * Private constructor to prevent construction of instances.
     */
    private Main() {
        // do nothing
    }

    /**
     * Constructs the main GUI window frame.
     * @param theArgs Command line arguments (ignored).
     */
    public static void main(final String... theArgs) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (final IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (final InstantiationException ex) {
            ex.printStackTrace();
        } catch (final ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // 5x5 maze with debugging on to show the steps of algorithm.
                final Maze mazeSmall = new Maze(5, 5, true);
                mazeSmall.display();
                //larger maze with debugging off
                final Maze mazeLarg = new Maze(30, 20, false);
                mazeLarg.display();
                mazeLarg.run();
            }
        });
        testMyHashTable();
    }
    /** 
     * This method will test for the MyHashTable class.
     */
    public static void testMyHashTable() { //NOPMD
        System.out.println("\n------------ Test MyHashTable ----------\n");
        final MyHashTable<String, String> data = new MyHashTable<String, String>(10);
//        final String original = "I like Data Structures course.";
        data.put(" ", "0");
        data.put(".", "1100");
        data.put("I", "1111");
        data.put("like", "101");
        data.put("course", "1110");
        data.put("Data", "100");
        data.put("Structures", "1101");
        System.out.println("Search key, Expected true: " + data.containsKey("I"));
        System.out.println("Get value of 'I': " + data.get("I"));
        System.out.println("Update value of the key 'I' to '0000': ");
        data.put("I", "0000");
        System.out.println("Expected value '0000' when get value of 'I': " + data.get("I"));
        System.out.println(data.toString());
        data.stats();
    }
}
