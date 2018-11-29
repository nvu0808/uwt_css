/*
 * TCSS 342 - Compressed Literature
 */
import java.util.Arrays;

/**
 * 
 * @author Hop pham
 * @version May 2018
 * @param <T> the data type
 */
public class MyPriorityQueue<T extends Comparable<T>> {
    /** The default initial capacity of the array. */
    private static final int DEFAULT_CAPACITY = 20;
    /** Factor to grow the array. */
    private static final int GROWFACTOR = 3;
    
    /** The null value. */
    private static final Object NULLVALUE = null;
    
    /** The length of the array. */
    private int myCapacity;    
  
    /** The size of the queue. */
    private int myQueueSize;
    
    /** Generic array to be used to store data for queue.*/
    private Object[] myArray;    
    
    /** 
     * Constructs an empty priority queue.
     */
    public MyPriorityQueue() {
        myCapacity = DEFAULT_CAPACITY;
        myQueueSize = 0;
        myArray = new Object[DEFAULT_CAPACITY];
    }
    
    /**
     * Return the stack information.
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder message = new StringBuilder(128);

        return message.toString();
    }
    
    /**
     * Return the size of the priority queue.
     * @return the size
     */
    public int size() {
        return myQueueSize;
    }
    
    /**
     * Add object to the queue.
     * @param theData the data
     * @return true
     */
    public boolean offer(final T theData) {
        if (myQueueSize == myCapacity) { 
            grow();
        }
        if (myQueueSize == 0) {
            myArray[0] = theData;
            myQueueSize++;
        } else {
            addNew(theData);
        }        
        return true;
    }
    
    /**
     * Return the elements of the priority queue. 
     * @return the array.
     */
    public Object[] toArray() {
        return Arrays.copyOf(myArray, myQueueSize);
    }
    
    /**
     * Return the root element, but not remove.
     * @return return root.
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        return (T) myArray[0];
    }
    
    /**
     * Grow the array 50%.
     */
    private void grow() {
        myCapacity = (myCapacity / 2) * GROWFACTOR; // grow 50%       
        myArray = Arrays.copyOf(myArray, myCapacity);
    }
    
    /**
     * Helper method to add new data to the queue.
     * @param theData the data
     */
    @SuppressWarnings("unchecked")
    private void addNew(final T theData) {
        int depth = myQueueSize;
        int parentPosition;
        while (depth > 0) {
            parentPosition = (depth - 1) >> 1;
            final T parent = (T) myArray[parentPosition];
            if (theData.compareTo(parent) >= 0) {
                break;
            }
            myArray[depth] = parent;
            depth = parentPosition;
        }
        myArray[depth] = theData;
        myQueueSize++;
    }
    /**
     * Retrieve and remove the head of this queue, or returns null if this queue is empty.
     * @return the head.
     */
    @SuppressWarnings("unchecked")
    public T poll() {
        T result = null;
        if (myQueueSize != 0) {
            final int s = --myQueueSize;
            result = (T) myArray[0];
            final T element = (T) myArray[s];
            myArray[s] = NULLVALUE;
            if (s != 0) {
                exchange(0, element);
            }
        }
        return result;
    }
    
    /**
     * exchange two elements.
     * @param theHead the head
     * @param theLast the last
     */
    @SuppressWarnings("unchecked")
    private void exchange(final int theHead, final T theLast) {
        final int half = myQueueSize >>> 1; // loop while a non-leaf
        int k = theHead;
        while (k < half) {
            // assume left child is least
            int child = (k << 1) + 1; 
            Object c = myArray[child];
            final int right = child + 1;
            if (right < myQueueSize && ((Comparable<T>) c).compareTo((T) myArray[right]) > 0) {
                child = right;
                c = myArray[child];
            }            
            if (theLast.compareTo((T) c) <= 0) {
                break;
            }
            myArray[k] = c;
            k = child;
        }
        myArray[k] = theLast;
    }
}