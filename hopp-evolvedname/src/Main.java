
/*
 * TCSS 342 - EvolvedNames
 */

/**
 * A controller for your evolutionary system that serves to run...
 * the evolutionary simulation and test the components of Genome and Population class.  
 * @author Hop Pham.
 * @version April 2018.
 */
public final class Main {
    
    /** The mutation rate which use to pass into Population. */
    private static final double MUTATION_RATE = 0.05; 

    /** The number of default genomes. */
    private static final int NUM_GENOMES = 100;
    
    /** The number of genomes to test. */
    private static final int NUM_GENOMES_TEST = 20;
    
    /** The number to run mutate. */
    private static final int NUM_MUTATE = 50;
    /**
     * Private constructor to prevent construction of instances.
     */
    private Main() { }
    
    /**
     * The main method.
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        final long start = System.currentTimeMillis();
        runEvolve();
        // runtime statistics
        System.out.println("Running Time: " + (System.currentTimeMillis() - start)
                           + " milliseconds");

        System.out.println("----Remove the command to Test for Genome and Population----");
        // tests Genome class.
//        testGenome();

        // tests Population class.
//        testPopulation();    
        
    }
    /** 
     * Helper method to instantiate a population and call day() method
     * until the target string is part of the population.
     */
    private static void runEvolve() {
        int generateCount = 0;
        final Population p = new Population(NUM_GENOMES, MUTATION_RATE);
        while (p.myMostFit.fitness() != 0) {
            p.day();
            generateCount++;
            System.out.println(p.myMostFit);
        }
        System.out.println("Generations: " + generateCount);
    }
    /**
     * This method tests the Genome class.
     */
    public static void testGenome() { //NOPMD
        System.out.println("---------------------- Test Genome ----------------------");

        //test instructor
        final Genome genome = new Genome(0.5);
        System.out.println("genome: initial " + genome.toString());

        //test mutate method, also mutate genome to test another method.
        for (int i = 1; i <= NUM_MUTATE; i++) {
            genome.mutate();
            System.out.println(i + "times mutated: " + genome.toString());
        }

        System.out.println();

        final Genome otherGenome = new Genome(0.5);
        System.out.println("otherGenome: initial " + otherGenome.toString());
        
        // mutate otherGenome.
        for (int i = 1; i <= NUM_MUTATE; i++) {
            otherGenome.mutate();
            System.out.println("The " + i + " mutated: " + otherGenome.toString());
        }

        // test crossover method
        genome.crossover(otherGenome);
        System.out.println("after genome & otherGenome crossover: " + genome.toString());
        
        try {
            final Genome nullGenome = null;
            final Genome illegalGenome = new Genome(1.1);
            illegalGenome.crossover(nullGenome);
        } catch (final IllegalArgumentException ex) {
            System.out.println("Expected this output: " + ex.toString());
        }
    }
    
    /**
     * This method tests the Population class.
     */
    public static void testPopulation() { //NOPMD
        System.out.println("---------------------- Test Population ----------------------");
        try {
            final Population illegalGenome = new Population(-1, -1.0);
            illegalGenome.day();
        } catch (final IllegalArgumentException ex) {
            System.out.println("Invalid input, Expected this output: " + ex.toString());
        }
        final Population pop = new Population(NUM_GENOMES_TEST, MUTATION_RATE);

        System.out.println("Population list: " + pop.toString());
        System.out.println("Most-fit: " + pop.myMostFit);

        // after call day() 6 times
        pop.day();
        pop.day();
        pop.day();
        pop.day();
        pop.day();
        pop.day();
        System.out.println("Expected the most-fit and population are updated");
        System.out.println("Population after day() called: " + pop.toString());        
        System.out.println("Most-fit after day() called: " + pop.myMostFit);
    }
}
