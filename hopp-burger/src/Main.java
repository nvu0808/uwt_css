/*
 * TCSS 342 - Burger
 */

import java.io.IOException;
import java.nio.file.Paths;
import java.util.EmptyStackException;
import java.util.Locale;
import java.util.Scanner;
import java.util.Stack;


/**
 * The main class use to read in the input file and test and run the Burger class.
 * @author Hop Pham
 * @version April 2018
 *
 */
public final class Main {
    
    /** String baron burger. */
    private static final String BARON_BURGER = "baron burger";
    
    /** String space. */
    private static final String SPACE = " ";
    /** String but. */
    private static final String BUT_STRING = "but";
    /** String burger. */
    private static final String BURGER = "burger";
    
    /** Double patty. */
    private static final String DOUBLE = "double";
    /** Triple patty. */
    private static final String TRIPLE = "triple";
    
    /** Chicken patty. */
    private static final String CHICKEN = "Chicken"; 
    /** Veggie patty. */
    private static final String VEGGIE = "Veggie";
    
    /** The string CHEESE. */
    private static final String CHEESE_STRING = "cheese";
    /** The string Veggies. */
    private static final String VEGGIES_STRING = "veggies";
    /** The string Sauce. */
    private static final String SAUCE_STRING = "sauce";
    
    /**
     * Private constructor to prevent construction of instances.
     */
    private Main() { }

    /**
     * The main method.
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        //final long startTime = System.nanoTime();   
        final long current = System.currentTimeMillis();
        // ... the code being measured ...
        
        //call reverseOrder function to read a text file then push to a Stack
        //which contains a few order.
        final MyStack<String> orders = reverseOrder(readOrderFromFile("src/order.txt"));
        //the code below will call processOrder method to read the order then passes the order 
        //to pareLine method to output Burger.
        processOrder(orders);
        
        System.out.println("---------------TEST BURGER----------------");
        //the method below will test all method for Burger class.
        //it also test invalid paramater.
        testBurger();
        System.out.println("---------------TEST MyStack----------------");
        //the line below will call the test method to test all method for MyStack class.
        //testMyStack method also test peek() method for an empty stack.
        //therefore an exception will throw out.
        //testMyStack();
        //final long estimatedTime = System.nanoTime() - startTime;
        System.out.println("Generations: ");
        //System.out.println("Running Time: " + estimatedTime / 1000000 + " milliseconds");
        final long result = System.currentTimeMillis() - current;
        System.out.println("Running Time: " + result + " milliseconds");
    }
    
    /**
     * Test method for Burger.
     * This method will test add method for MyStack class.
     * Test generic type, and the exception when peek an empty stack.
     */
    public static void testMyStack() {
        final MyStack<Integer> testMyStack = new MyStack<Integer>();
        testMyStack.push(1);
        testMyStack.peek();
        
        System.out.println("Test size: " + testMyStack.size() + ". Expected size 1.");
        final Stack<String> test = new Stack<String>();
        test.push("Test");
        System.out.println("Test tostring and push method result: " + test.toString());
        test.pop();
        System.out.println("Test size result: " + test.size() + ". Expected size 0.");
        
        System.out.println("Test isEmpty: " + test.isEmpty() + ". Expected false");
        try {
            final MyStack<Boolean> boolStack = new MyStack<Boolean>();
            System.out.println("Test empty: " + boolStack.isEmpty() + ". Expected true");
            boolStack.pop();
        } catch (final EmptyStackException ex) {
            System.out.println("Expected this output since pop an empty Stack."
                                        + ex.toString());
        }
    }
    
    /**
     * Test method for MyBurger.
     */
    public static void testBurger() {
        //test Double Chicken Burger with Veggies but Tomato 
        final Burger testBurger = new Burger(false); 
        final Burger testBaronBurger = new Burger(true);
        System.out.println("Expected a default Burger: " + testBurger.toString());
        System.out.println("Expected a default Baron Burger: " + testBaronBurger.toString());
        System.out.println("");
        System.out.println("Test addCategory, changePatties, addPatty, and RemoveIngredient:");
        testBurger.addCategory(VEGGIES_STRING);        
        testBurger.changePatties(CHICKEN);   
        System.out.println("--test add patty");
        testBurger.addPatty();
        System.out.println("end");
        testBurger.removeIngredient("Tomato");
        System.out.println("Expected Duble burger with veggies but no tomato: "
                                        + testBurger.toString());  
        System.out.println("");
        System.out.println("Test removeCategory and addIngredient method.");
        testBaronBurger.removeCategory(CHEESE_STRING);
        testBaronBurger.addIngredient("Chedder");
        //test add ingredient by passed invalid type.
        testBaronBurger.addIngredient(CHEESE_STRING); 
        System.out.println("Expected baron burger but no chedder: "
                                        + testBaronBurger.toString());
    }
    
    /**
     * Reads order information from a file and returns a List of Item objects.
     * @param theFile the name of the file to load into a List of Items
     * @return a List of order created from data in an input file
     */
    private static MyStack<String> readOrderFromFile(final String theFile) {
        final MyStack<String> orders = new MyStack<String>();
        try (Scanner input = new Scanner(Paths.get(theFile))) {
            while (input.hasNextLine()) {
                orders.push(input.nextLine());
            }
            input.close();
        } catch (final IOException e) {
            System.out.println("Cant read file: " + theFile);
        } 
        return orders;
    }
    
    /**
     * Reverse the order.
     * @param theOrder the order
     * @return the reverse order
     */
    private static MyStack<String> reverseOrder(final MyStack<String> theOrder) {
        final MyStack<String> orderRev = new MyStack<String>();
        while (!theOrder.isEmpty()) {
            orderRev.push(theOrder.pop());
        }
        return orderRev;
    }
    
    /**
     * Read order from stack then call processing method to process.
     * @param theOrder the orders.
     */
    private static void processOrder(final MyStack<String> theOrder) {
        int count = 0;
        while (!theOrder.isEmpty()) {
            System.out.println("Processing Order " + count + ": " + theOrder.peek());
            parseLine(theOrder.pop());
            count++;
            System.out.println(""); //seprate line
        }
    }
    
    /**
     * Parses a line of input from the file and outputs the burger.
     * @param theLine the order information.
     */
    public static void parseLine(final String theLine) {
        final String lineLowerCase = theLine.toLowerCase(Locale.ENGLISH);
        boolean barronBurger = false;        
        //check if the order is baron burger
        String mes = "";        
        if (lineLowerCase.contains(BARON_BURGER)) {
            barronBurger = true;
            mes = theLine.substring(lineLowerCase.indexOf(BARON_BURGER)
                                             + BARON_BURGER.length());
        } else {
            mes = theLine.substring(lineLowerCase.indexOf(BURGER)
                                             + BURGER.length());
        }        
        final Burger burger = new Burger(barronBurger);
        processCountPatty(lineLowerCase, burger);
        //in case the count patty is empty.
        processPatty(theLine, burger);
        processCondition(mes, burger);
        System.out.println(burger.toString());
    }
    
    /**
     * Helper method to add additions.
     * @param theMes the order information.
     * @param theBurger the working burger.
     */
    private static void processCondition(final String theMes,
                                             final Burger theBurger) {
        int butIndex = theMes.length();
        if (theMes.contains(BUT_STRING)) {
            butIndex = theMes.indexOf(BUT_STRING);
        }
        if (theMes.contains("with no")) {
            processOmissions(theMes, theBurger);
            processExceptions(theMes.substring(butIndex), theBurger, true);
        } else if (theMes.contains("with")) {
            processAdditions(theMes, theBurger);
            processExceptions(theMes.substring(butIndex), theBurger, false);
        }        
    }
    
    /**
     * Helper method to remove Category or Ingredient.
     * @param theTarget the category or ingredient
     * @param theBurger the burger
     */
    private static void remove(final String theTarget, final Burger theBurger) {
        if (theTarget.equalsIgnoreCase(CHEESE_STRING)) {
            theBurger.removeCategory(CHEESE_STRING);
        } else if (theTarget.equalsIgnoreCase(VEGGIES_STRING)) {
            theBurger.removeCategory(VEGGIES_STRING);
        } else if (theTarget.equalsIgnoreCase(SAUCE_STRING)) {
            theBurger.removeCategory(SAUCE_STRING);
        } else {
            theBurger.removeIngredient(theTarget);
        }
    }
    
    /**
     * Helper method to add Category or Ingredient.
     * @param theTarget the category or ingredient
     * @param theBurger the burger
     */
    private static void add(final String theTarget, final Burger theBurger) {
        if (theTarget.equalsIgnoreCase(CHEESE_STRING)) {
            theBurger.addCategory(CHEESE_STRING);
        } else if (theTarget.equalsIgnoreCase(VEGGIES_STRING)) {
            theBurger.addCategory(VEGGIES_STRING);
        } else if (theTarget.equalsIgnoreCase(SAUCE_STRING)) {
            theBurger.addCategory(SAUCE_STRING);
        } else {
            theBurger.addIngredient(theTarget);
        }
    }
    
    /**
     * Helper method to remove/add exceptions.
     * @param theMes the order information.
     * @param theBurger the working burger.
     * @param theException true if but and with no ortherwise false.
     */
    private static void processExceptions(final String theMes,
                                          final Burger theBurger, final boolean theException) {
        
        final String[] order = theMes.split(SPACE);
        int index = order.length - 1;
        if (theException) {
            while (index > 0) {
                add(order[index], theBurger);
                index--;
            }
        } else {
            while (index > 0) {                
                remove(order[index], theBurger);
                index--;
            }
        }
    }
    
    /**
     * Helper method to remove omissions.
     * @param theMes the order information.
     * @param theBurger the working burger.
     */
    private static void processOmissions(final String theMes,
                                         final Burger theBurger) {
        int index = 2;
        final String[] order = theMes.split(SPACE);
        while (index < order.length && !order[index].equalsIgnoreCase(BUT_STRING)) {
            remove(order[index], theBurger);
            index++;
        }
    }
    
    /**
     * Helper method to add additions.
     * @param theMes the order information.
     * @param theBurger the working burger.
     */
    private static void processAdditions(final String theMes,
                                         final Burger theBurger) {
        
        int index = 1;
        final String[] order = theMes.trim().split(SPACE);
        while (index < order.length && !order[index].equalsIgnoreCase(BUT_STRING)) {
            add(order[index], theBurger);
            index++;
        }
    }
    
    /**
     * Check the count patty to add to the burger.
     * @param thePattyCount the count patty or the patty.
     * @param theBurger the working burger.
     */
    private static void processCountPatty(final String thePattyCount,
                                          final Burger theBurger) {
        if (thePattyCount.contains(DOUBLE)) {
            theBurger.addPatty();
        } else if (thePattyCount.contains(TRIPLE)) {
            theBurger.addPatty();
            theBurger.addPatty();
        }
    }
    
    /**
     * Check the patty to change the burger's patty.
     * @param theOrderMessage the count patty or the patty.
     * @param theBurger the working burger.
     */
    private static void processPatty(final String theOrderMessage,
                                     final Burger theBurger) {
        final String toLower = theOrderMessage.toLowerCase(Locale.ENGLISH);
        if (toLower.contains(CHICKEN.toLowerCase(Locale.ENGLISH))) {
            theBurger.changePatties(CHICKEN);            
        } else if (toLower.contains(VEGGIE.toLowerCase(Locale.ENGLISH))
                        && toLower.indexOf(VEGGIE.toLowerCase(Locale.ENGLISH))
                        != toLower.indexOf(VEGGIES_STRING)) {
            theBurger.changePatties(VEGGIE);
        }
        
    }
}
