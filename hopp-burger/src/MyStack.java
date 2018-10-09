/*
 * TCSS 342 - Burger
 */
import java.util.EmptyStackException;

/**
 * 
 * @author Hop pham
 * @version April 2018
 * @param <T> the data type
 */
public class MyStack<T> {
    /** Size of stack. */
    private int mySize;
    /** A node. */
    private Node<T> myNode;
    
    /**
     * Constructor.
     */
    public MyStack() {
        mySize = 0;
        myNode = new Node<T>(null);
    }
    
    /**
     * Return true if the Stack is empty false otherwise.
     * @return true/false.
     */
    public boolean isEmpty() {        
        return mySize == 0;
    }

    /**
     * Push data into stack.
     * @param theData the data
     */
    public void push(final T theData) {
        final Node<T> newNode = new Node<T>(theData);
        final Node<T> temp = myNode;
        mySize++;
        newNode.myPrevious = temp;
        myNode = newNode;
    }
    
    /**
     * Return the last item in stack.
     * @return the last item.
     */
    public T peek() {
        if (mySize == 0) {
            throw new EmptyStackException();
        }        
        return myNode.myData;
    }
        
    /**
     * Return and remove the value at top of stack.
     * @return the top value.
     */
    public T pop() {
        if (mySize == 0) {
            throw new EmptyStackException();
        } 
        final Node<T> temp = myNode;
        myNode = myNode.myPrevious;
        mySize--;
        return temp.myData;
    }
    
    /**
     * Returns the number of items in the.
     * @return the size.
     */
    public int size() {
        return mySize;
    }
    
    /**
     * Return the stack information.
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder message = new StringBuilder(128);
        Node<T> temp = myNode;
        message.append('[');
        while (temp.myPrevious.myPrevious != null) {
            message.append(temp.myData.toString());
            message.append(", ");
            temp = temp.myPrevious;
        }
        message.append(temp.myData.toString());
        message.append(']');
        return message.toString();
    }
    /**
     * Inner class Node. 
     * @author hopp
     * @param <T> the data type
     */
    @SuppressWarnings("hiding")
    private class Node<T> {
        
        /** The data. */
        private T myData;
        /** The under node. */
        private Node<T> myPrevious;
       
        /**
         * Constructor: Set the item.
         * @param theData The item content
         */
        protected Node(final T theData) {
            this(theData, null);
        }

        /**
         * Constructs a QuestionNode with the given data and links.
         * @param theData item content
         * @param theDown the below node
         */
        protected Node(final T theData, final Node<T> theDown) {
            this.myData = theData;
            this.myPrevious = theDown;
        }
        
    }
}
