import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * TCSS 342 - EvolvedNames
 */

/**
 * A controller for your evolutionary system that serves to run...
 * the evolutionary simulation 
 * and test the components of Genome and Population class.  
 * @author Hop Pham.
 * @version April 2018.
 */
public class Genome2 implements Comparable<Genome2> {
    
    /** Prevents Random object created and used only once. */
    private static final Random RANDOM = new Random();

    /** The default value. */
    private static final char A = 'A';
    
    /** The data element that is initialized to a name. */
    private static final String TARGET = "CHRISTOPHER PAUL MARRIOTT";
    
    /** Set of characters of virtual world .*/ 
    private static final char[] WORLD_CHARACTERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                                                    'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                                                    'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                                                    'Y', 'Z', ' ', '-', '\''}; 
    /** Length of the WORLD_CHARACTERS .*/
    private static final int WORLD_LENGTH = 29;
    
    /** the internal mutation rate. */
    private final double myMutationRate;
    
    /** The current string. */
    private List<Character> myString;

    /**
     * Constructors a Genome with value 'A' and assigns the internal mutation rate.
     * The mutationRate must be between zero and one.
     * @param theMutationRate the internal mutation rate
     */
    public Genome2(final double theMutationRate) {
        myString = new ArrayList<Character>();
        if (theMutationRate > 1 || theMutationRate < 0) {
            throw new IllegalArgumentException();
        }
        myMutationRate = theMutationRate;
        myString.add(A);
    }
    /**
     * Constructor that initializes a Genome with the same
     * values as the input gene.
     * @param theGenome the Genome
     */
    public Genome2(final Genome2 theGenome) {
        myString = theGenome.myString;
        myMutationRate = theGenome.myMutationRate;
    }
    
    /**
     *Mutates the string in this Genome using the following rules: <br>
     *○ with mutationRate chance add a randomly selected character to a randomly
     *selected position in the string. <br>
     *○ with mutationRate chance delete a single character from a randomly selected
     *position of the string but do this only if the string has length at least 2. <br>
     *○ for each character in the string: <br>
     *with mutationRate chance the character is replaced by a randomly
     *selected character.
     */
    public void mutate() {/*
        //add a randomly selected character
        if (RANDOM.nextDouble() < myMutationRate) {
            myString.add(new Random().nextInt(myString.size()),
                         WORLD_CHARACTERS[RANDOM.nextInt(WORLD_LENGTH)]);
        }
        //randomly remove a character
        if (Math.random() < myMutationRate && myString.size() > 1) {
            myString.remove(RANDOM.nextInt(myString.size()));
        }
        //randomly change a character
        final int size = myString.size();
        for (int i = 0; i < size; i++) {
            if (Math.random() < myMutationRate) {
                myString.set(RANDOM.nextInt(myString.size()),
                             WORLD_CHARACTERS[RANDOM.nextInt(WORLD_LENGTH)]);
            }
        }
        */
        final Random random = new Random();

        // how to generate random number between 0.0 and 1.0
        // for (Math.random() * (1.0 - 0.0) + 0.0)
        // http://java67.blogspot.com/2015/01/how-to-get-random-number-between-0-and-1-java.html

        // http://stackoverflow.com/questions/2482078/how-to-get-a-random-letter-from-a-list-of-specific-letters
        // for "random.nextInt(CHARACTERS.size())"

        // adds a randomly selected character to a randomly selected position in
        // the string.
        if (Math.random() * 1.0 < myMutationRate) {
            myString.add(random.nextInt(myString.size()), WORLD_CHARACTERS[RANDOM.nextInt(WORLD_LENGTH)]);
        }

        // deletes a single character from randomly selected position of the
        // string,
        // but do this only if the string has length at least 2.
        if (Math.random() * 1.0 < myMutationRate && myString.size() > 1) {
            myString.remove(random.nextInt(myString.size()));
        }

        // replaces each char in the string by a randomly selected char.
        for (int i = 0; i < myString.size(); i++) {
            if (Math.random() * 1.0 < myMutationRate) {
                myString.set(i, WORLD_CHARACTERS[RANDOM.nextInt(WORLD_LENGTH)]);
            }
        }
    }
    
    /**
     * This function will update the current Genome by crossing
     *it over with other. <br>
     *○ Create the new list by following these steps for each index in the string starting at
     *the first index:<br>
     *■ Randomly choose one of the two parent strings.<br>
     *■ If the parent string has a character at this index (i.e. it is long enough)<br>
     *copy that character into the new list. Otherwise end the new list her
     * @param theOther the other Genome
     */
    public void crossover(final Genome2 theOther) {
        int index = 0;
        boolean flag = true;
        final List<Character> newList = new ArrayList<Character>();
        while (flag) {
            final boolean pickParent = RANDOM.nextBoolean();
            //pick this string if next boolean is true, otherwise pick other
            if (pickParent && myString.size() - 1 >= index) {
                newList.add(myString.get(index));
            } else if (!pickParent && theOther.myString.size() - 1 >= index) {
                newList.add(theOther.myString.get(index));
            } else {
                flag = false;
            }
            index++;
        }
        myString = newList;
    }
   
    /**
     * Return the fitness of Genome calculated using provided alogrithm
     * or the Wagner-Fischer algorithm.
     * @return the fitness of Genome.
     */
    public int fitness() {
        return christoper();
//        return wagnerFischer();
    }
    /**
     * CHRISTOPHER alogrithm.
     * @return the fitness
     */
    private int christoper() {
        final int n = myString.size();
        final int m = TARGET.length();

        //l be the max(n, m);
        final int l = Math.max(n, m);

        //f be initialized to |m-n|
        int f = Math.abs(m - n); // -> length penalty

        //For each character position i <= i <= l, add one to f if
        //the character in the current string is different from the character
        //in the target string (or if one of the two characters does not exist)
        for (int i = 0; i < l; i++) {
            if (i < myString.size() && i < m && myString.get(i) != TARGET.charAt(i)) {
                f++;
            }
            if (myString.size() + i < l) {
                f++;
            }
        }
        return f;
    }
    /** 
    * Wagner-Fischer algorithm.
    * @return the fitness
    */
    private int wagnerFischer() {
        final int currentLength = myString.size();
        final int targetLength = TARGET.length();
    
         //create matrix D[n+1][m+1], initialized with 0s
        final int[][] arr = new int[currentLength + 1][targetLength + 1];
    
         //fill 1st row with column indices and fill the 1st column with
         // row indices
        for (int i = 0; i <= currentLength; i++) {
            arr[i][0] = i;
        }
        for (int i = 1; i <= targetLength; i++) { 
            arr[0][i] = i;
        }
    
        //implement 'the nested loop' to fill in the rest of the matrix
        //(provided by Dr.Marriott)
        for (int i = 1; i <= currentLength; i++) {
            for (int j = 1; j <= targetLength; j++) {
                if (myString.get(i - 1) == TARGET.charAt(j - 1)) {
                    arr[i][j] = arr[i - 1][j - 1];
                } else {
                    arr[i][j] = Math.min(Math.min(arr[i - 1][j] + 1,
                                                     arr[i][j - 1] + 1),
                                                     arr[i - 1][j - 1] + 1);
                }
            }
        }
    
        return arr[currentLength][targetLength] 
                        + (Math.abs(currentLength - targetLength) + 1) / 2;
    }
    
    /**
     * Will display the Genome’s character string and fitness in
     * an easy to read format.
     * @return the Genome's character.
     */
    public String toString() {
        final StringBuilder result = new StringBuilder(128);
        result.append("(\"");
        for (final Character c : myString) {
            result.append(c);
        }        
        result.append("\", ");
        result.append(fitness());
        result.append(')');
        return result.toString();
    }
    @Override
    public int compareTo(final Genome2 theOther) {
        return this.fitness() - theOther.fitness();
    }
}
