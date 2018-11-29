/*
 * TCSS 342 - Compressed Literature
 */

import java.util.Map;
import java.util.TreeMap;

/**
 * The Class will carry out all stages of Huffman's encoding algorithm.
 * @author Hop pham
 * @version May 2018
 */
public class CodingTree {
    
    /** a map of characters in the message to binary codes. */
    public final Map<Character, String> codes;
    
    /** The encoded message. */
    public String bits = "";
    
    /** The root node. */
    private Node myRoot;
    
    /** The priority queue. */
    private final MyPriorityQueue<Node> myPQueue;
    
    /** frequency of characters. */
    private final Map<Character, Integer> myFMap;    
    
    
    /**
     * Constructor that take the text of a message to be compressed.
     * @param theMessage the message to encoding.
     */
    public CodingTree(final String theMessage) {
        myPQueue = new MyPriorityQueue<Node>();
        myFMap = new TreeMap<Character, Integer>();
        codes = new TreeMap<Character, String>();
        
        createFrequencyMap(theMessage);
        buildHeap();
        buildTree();
        encode(myRoot, "");
        buildString(theMessage);
    }
    
    /**
     * This method will take the output of Huffman's encoding
     * and produce the original test.
     * @param theBits the encoded string
     * @param theCodes the encoded
     * @return the original text.
     */
    public String decode(final String theBits, final Map<Character, String> theCodes) {
        final StringBuilder sb = new StringBuilder();
        final Map<String, Character> swapKeyValue = new TreeMap<String, Character>();
        for (final char key:theCodes.keySet()) {
            final String code = theCodes.get(key);
            swapKeyValue.put(code, key);
        }
        final StringBuilder temp = new StringBuilder();
        for (int k = 0; k < theBits.length(); ++k) {
            temp.append(theBits.charAt(k));
            //the bits match the code
            if (swapKeyValue.containsKey(temp.toString())) {
                sb.append(swapKeyValue.get(temp.toString()));
                temp.delete(0, temp.length());
            }
        }
        return sb.toString();
    }
    
    /**
     * Helper method to build the encoded message to a string.
     * @param theMessage the message
     */
    private void buildString(final String theMessage) {
        final StringBuilder sb = new StringBuilder();
        final char[]chars = theMessage.toCharArray();
        for (final char c:chars) {
            sb.append(codes.get(c));
        }
        bits = sb.toString();
    }
    /**
     * Creates the frequency map.
     *
     * @param theStr the string to encode
     */
    private void createFrequencyMap(final String theStr) {        
        for (final char key : theStr.toCharArray()) {            
            if (!myFMap.containsKey(key)) {
                myFMap.put(key, 1);
                
            } else if (myFMap.containsKey(key)) {
                int count = myFMap.get(key);
                myFMap.put(key, ++count);
            }
        }
        
    }
    
    /**
     * Build the priority queue from the map of character and it's frequency.
     */
    private void buildHeap() {
        for (final char ch : myFMap.keySet()) {
            final int freq = myFMap.get(ch);
            final Node temp = new Node(ch, freq);
            myPQueue.offer(temp);
        }
    }
    
    /**
     * Helper method to build the Huffman's tree from the Priority Queue.
     */
    private void buildTree() {
        while (myPQueue.size() > 1) {
            myPQueue.offer(new Node(myPQueue.poll(), myPQueue.poll()));            
        }
        myRoot = myPQueue.poll();
    }
    
    /**
     * Helper method to encode string.
     * @param theRoot the root
     * @param theCode the code
     */
    private void encode(final Node theRoot, final String theCode) {
        if (theRoot.myLeft != null) {
            encode(theRoot.myLeft, theCode + '0');
        }
        if (theRoot.myRight != null) {
            encode(theRoot.myRight, theCode + '1');
        }
        if (theRoot.isLeaf()) {
            codes.put(theRoot.myChar, theCode);
        }
    }
    
    /**
     * The Class Node.
     */
    private class Node implements Comparable<Node> {
        
        /** The my char. */
        private char myChar;
        
        /** The my freq. */
        private final int myFreq;
        
        /** The left. */
        private Node myLeft;
        
        /** The right. */
        private Node myRight;

        /**
         * Initialize a new node.
         * @param theChar the the char
         * @param theFreq the the freq
         */
        public Node(final char theChar, final int theFreq) {
            myChar = theChar;
            myFreq = theFreq;
        }
        
        /**
         * Initialize a new node.
         * @param theLeft the left node
         * @param theRight the right node
         */
        public Node(final Node theLeft, final Node theRight) {
            myLeft = theLeft;
            myRight = theRight;
            myFreq = theLeft.myFreq + theRight.myFreq;                    
        }        
       
        
        
        /**
         * Return true if the node if the leaf, otherwise false.
         * @return true, false
         */
        private boolean isLeaf() {
            return myLeft == null && myRight == null;
        }
        
        @Override
        public int compareTo(final Node theOther) {
            return myFreq - theOther.myFreq;
        }
    }
}