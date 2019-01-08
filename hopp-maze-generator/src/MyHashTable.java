/*
 * TCSS 342 - Compressed Literature 2
 */

import java.util.Arrays;

/**
 * The Class is similar to HashTable of Java.
 * @author Hop pham
 * @version May 2018
 * @param <K> key
 * @param <V> value
 */
public class MyHashTable<K, V> {
    /** The factor. */
    private static final int FACTOR = 3;
    /** Store keys and values. */
    private final Entry<K, V>[] myEntry;
    /** Max number of entry. */
    private final int myCap;    
    /** Inserted entry. */
    private int myEntries;
    /** Probe counter. */
    private int myMaxProbe;
    /** Store probe history. */
    private int[] myProbes;

    /**
     * Initialize data.
     * @param theCapacity the capacity
     */
    @SuppressWarnings("unchecked")
    MyHashTable(final int theCapacity) {
        myEntries = 0;
        myMaxProbe = 0;
        myCap = theCapacity;
        myEntry = new Entry[theCapacity];
        myProbes = new int[100];
    }

    /**
     * Update or add the newValue to the bucket.
     * if hash(key) is full use linear probing to find the next available bucket. 
     * @param theKey the key
     * @param theVal the value
     */
    public void put(final K theKey, final V theVal) {
        int i = 0;
        int probe = 0;        
        for (i = hash(theKey); myEntry[i] != null; i = (i + 1) % myCap) {
            //update the value for the existed key.
            if (myEntry[i].myKey.equals(theKey)) {
                myEntry[i].myValue = theVal;
                return;
            }
            probe++;
        }
        //put new value into the corresponding bucket.
        myEntry[i] = new Entry<K, V>(theKey, theVal);
        if (probe > myMaxProbe) {
            myMaxProbe = probe;
        }
        myEntries++;
        if (probe >= myProbes.length) { //grow the array 50%
            myProbes = Arrays.copyOf(myProbes, probe * FACTOR / 2);
            myProbes[probe]++;
        }
        myProbes[probe]++;
    }
    
    /**
     * Return the current size.
     * @return an integer.
     */
    public int size() {
        return myEntries;
    }

    /**
     * Returns all objects in the hash table.
     *
     * @return the array list of objects
     */
    @SuppressWarnings("unchecked")
    public Entry<K, V>[] entrySet() {  
        final Entry<K, V>[] data = new Entry[myEntries];
        int position = 0;
        for (int i = 0; i < myCap; i++) {
            if (myEntry[i] != null) {
                data[position] = myEntry[i];
                position++;
            }
            if (position == myEntries) {
                break;
            }
        }
        return data;
    }
    
    /**
     * Return the value if the key is exited otherwise null.
     * Used linear probing alogrithm.
     * @param theKey the key
     * @return value of the key
     */
    public V get(final K theKey) {
        final int index = findIndex(theKey);
        V result = null;
        if (index >= 0) {
            result = myEntry[index].myValue;
        }
        return result;
    }
    
    /**
     * Helper method to find the corresponding position of the key.
     * Return -1 if the key doesnt existed.
     * @param theKey the key.
     * @return the index.
     */
    private int findIndex(final K theKey) {
        final int hash = hash(theKey);
        int index = hash;
        int result = -1;
        //find the position which right before the acture index.
        final int rightIndex = (index - 1) % myCap;
        //not run around and existed valid position.        
        while (rightIndex != index && myEntry[index] != null) {
            if (myEntry[index].myKey.equals(theKey)) {
                result = index;
                break;
            } else { // moves to the next position.
                index = ++index % myCap;
            }
        }
        return result;
    }

    /**
     * Return true if the key is existed otherwise false.
     * @param theKey the key
     * @return true false.
     */
    public boolean containsKey(final K theKey) {       
        return get(theKey) != null;    
    }

    /**
     * Displays the stat block for the data in hash table
     * A histogram of probes shows how many keys are found 
     * after a certain number of  probes.
     */
    public void stats() {
        final StringBuilder sb = new StringBuilder(256);
        System.out.println("Hash Table Stats\n=================");
        System.out.println("Number of Entries: " + myEntries);
        System.out.println("Number of Buckets: " + myCap);
        sb.append("\nHistogram of Probes: [");
        int total = 0;
        for (int i = 0; i <= myMaxProbe; i++) {
            sb.append(myProbes[i]);
            if (i < myMaxProbe) {
                sb.append(", ");
            }
            if (i == 5) {
                sb.append('\n');
            }            
            if (i > 0 && i %  20 == 0) {
                sb.append('\n');
            }
            total += myProbes[i] * i;
        }
        sb.append("]\nFill Percentage: ");
        sb.append(100.0 * myEntries / myCap + "%\n");
        sb.append("Max Linear Prob: ");
        sb.append(myMaxProbe);
        sb.append("\nAverage Linear Probe: ");
        sb.append((double) total / myEntries);
        System.out.println(sb.toString());     
    }

    /**
     * Converts the hash table contents to a String.
     * @return contents of hash table.
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        int counter = myEntries;
        int i = 0;
        while (counter != 0 && i < myCap) {
            if (myEntry[i] != null) {
                sb.append(myEntry[i].toString());
                if (counter > 1) {
                    sb.append(", ");
                } else {
                    sb.append(']');
                }
                counter--;
            }
            i++;
        }
        return sb.toString();
    }
    /**
     * Takes a key and returns an int in the range of 0 to capacity.
     * @param theKey the key
     * @return integer
     */
    private int hash(final K theKey) { 
        return Math.abs((theKey.hashCode() * FACTOR + myCap - 1) % myCap);
    }
    
    /**
     * This class will store the word population of the string.
     * @author Hop Pham
     *
     * @param <K> Key Word
     * @param <V> Value
     */
    public static class Entry<K, V> {
        /** The word. */
        private final K myKey;
        /** The value in binary. */
        private V myValue;   
        
        /**
         * Instructs the key and value.
         * @param theKey the word
         * @param theVal the value in binary
         */
        public Entry(final K theKey, final V theVal) {
            myKey = theKey;
            myValue = theVal;
        }
        
        /**
         * Return the value.
         * @return value
         */
        public V getValue() {
            return myValue;
        }
        @Override
        public String toString() {
            return "(" + myKey + ", " + myValue + ")";
        }

        /**
         * Return key.
         * @return key
         */
        public K getKey() {
            return myKey;
        }
    }

}
