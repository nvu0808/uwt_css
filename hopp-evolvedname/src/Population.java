/*
 * TCSS 342 - EvolvedNames
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;



/**
 * @author Hop Pham.
 * @version April 2018.
 */
public class Population {

    /** Prevents Random object created and used only once. */
    private static final Random RANDOM = new Random();
    
    /** a data element that is equal to the most-fit Genome in the population. */
    public Genome myMostFit;

    /**
     * The number of Genomes.
     */
    private final Integer myNumGenomes;

    /** List of Genome. */
    private List<Genome> myListG;
    /**
     * 
     * @param theNumGenomes number of default genomes
     * @param theMutationRate the mutation rate
     */
    public Population(final Integer theNumGenomes, final Double theMutationRate) {
        if (theMutationRate > 1 || theMutationRate < 0 || theNumGenomes <= 0) {
            throw new IllegalArgumentException();
        }
        myNumGenomes = theNumGenomes;
        myListG = new ArrayList<Genome>();
        myMostFit = new Genome(theMutationRate); 
        myListG.add(new Genome(theMutationRate));
        for (int i = 1; i <= theNumGenomes - 1; i++) {
            myListG.add(new Genome(theMutationRate)); 
        }
    }

    /**
     * This method is called every breeding cycle.
     */
    public void day() {

        Collections.sort(myListG); //compareTo allowed.
        //use the code below for the case that the public method compareTo is not allowed.
        //using compareTo will faster 10% 
//        myListG.sort((s1, s2) -> s1.fitness() - s2.fitness()); 
        //update the most-fit Genome
        myMostFit = myListG.get(0);

        //delete the least-fit half of the population
        final List<Genome> newList = myListG.subList(0, myListG.size() / 2);
        myListG = newList;

        //create new genomes from the remaining population until the number of genomes
        //is restored.
        for (int i = myNumGenomes / 2; i < myNumGenomes; i++) {
            final Genome newGenome;
            if (RANDOM.nextBoolean()) {
                //pick a remaining genome at random
                newGenome = new Genome(myListG.get(new Random().nextInt(myListG.size())));
                newGenome.mutate();
            } else {
                //pick a remaining Genomes at random and clone it and then
                //cross over the clone with another remaining Genomes 
                //selected at random and then mutate the result.
                newGenome = new Genome(myListG.get(RANDOM.nextInt(myListG.size())));
                newGenome.crossover(myListG.get(RANDOM.nextInt(myListG.size())));
                newGenome.mutate();
            }
            myListG.add(newGenome);
        }
    }

    /**
     * Displays the entire population and most-fit individual in the population.
     * @return the string.
     */
    public String toString() {
        final StringBuilder result = new StringBuilder(128);
        for (final Genome g : myListG) {
            result.append(g);
            result.append('\n');
        }
        return result.toString();
    }
}