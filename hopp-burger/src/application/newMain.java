/*
 * TCSS 342
 * author TamNguyen
 * Version spring 2018
 * Assignment 1 -- Burger Baron 
 *
 */
package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import model.Burger;
import model.MyStack;

/**
 * @author tamnguyen
 *
 */
public class newMain {
    

    /* CONSTANT VARIABLES FOR PATTYE COUNT*/
    private final static String DOUBLE_PATTIES = "Double";
    private final static String TRIPLE_PATTIES = "Triple";
    /* CONSTANT VARIABLES FOR TYPE OF PATTIES.*/
    private final static String VEGGIE_PATTY = "Veggie";
    private final static String DEFAULT_PATTY = "Beef";
    private final static String CHICKEN_PATTY = "Chicken";
    
    
    
    
    
    
    
    /**main to process the order.
     * 
     * @param args 
     */
    public static void main(String[] args) throws IOException {
        

        
     final MyStack<String> orders = reverseOrder(readOrderFromFile(new File("src/order.txt")));
     readOrder(orders);
        
    
        //testBurger();
//      testMyStack();
    } // end of  main 
    
    /**read file.
     * 
     * @param theFile 
     * @return
     * @throws FileNotFoundException
     */
    private static MyStack<String> readOrderFromFile(final File theFile) throws FileNotFoundException{  
        MyStack<String> order = new MyStack<String>();
        String newOrder = "";
        Scanner sc = new Scanner(theFile);
        try {   
            while (sc.hasNextLine()) {      
                newOrder = (sc.nextLine());    
                order.push(newOrder);        
            }       
        } catch (Exception e) {
            e.printStackTrace();
        }   
        sc.close();     
        return order;   
    } // end of read order from file.
    
    
    private static MyStack<String> reverseOrder(MyStack<String> theOrder) {
        final MyStack<String> reverseOrder = new MyStack<String>();
        while (!theOrder.isEmpty()) {
            reverseOrder.push(theOrder.pop());
            
            //System.out.println(reverseOrder.peek());
        }
        
        return reverseOrder;
    }
    
    private static void readOrder(MyStack<String> str) {    
        int count = 0;
        String string = "";
        while(!str.isEmpty()) {
            string = str.pop();
            System.out.println("Processing Order " + count++ + ": " + string);
            //System.out.println();
            parseLine(string);  
            System.out.println();           
        } // end of while loop.     
    }
    
    
    /**this methods to read a string from
     * a file and out put the result.
     * in this case, it read the string
     * of cuxtomer order and output 
     * 
     * @param line to read
     */
    public static void parseLine(String line) {
        
        Burger burger;
        //sint numPatty = checkForPattyCount(line);
        boolean isBaronBurger = false;  
        if(line.contains("Baron Burger")) {
            isBaronBurger = true;       
        }   
        burger = new Burger(isBaronBurger);
        countPatty(line, burger);
        makeBurger(line,burger);
        System.out.println(burger.toString()); 
    }
    
    
    /**this method counts for how many patty in the order.
     * 
     * @param line to read the how many patty should added to the burger
     * @param theBurger this burger to add patty.
     */
    private static void countPatty(String line, Burger theBurger) { 
        if(line.contains(DOUBLE_PATTIES)){
            theBurger.addPatty();   
            typeOfPatty(line,theBurger);
            typeOfPatty(line,theBurger);
        } else if (line.contains(TRIPLE_PATTIES)) {
            theBurger.addPatty();   
            theBurger.addPatty();
            typeOfPatty(line,theBurger);
            typeOfPatty(line,theBurger);
            typeOfPatty(line,theBurger);
        } else {
            typeOfPatty(line,theBurger);        
        }   
    }
    
    /**this method looks for what type of
     * patty customers order and change it
     * if needed.
     * 
     * @param line to read for information
     * @param theBurger this burger
     */
    private static void typeOfPatty(String line, Burger theBurger) {
        if(line.contains("Chicken") || line.contains("chicken")){
            theBurger.changePatties(CHICKEN_PATTY);
            
        } else if (line.contains("Veggie") || line.contains("veggie")) {
            theBurger.changePatties(VEGGIE_PATTY);
        } else {
            theBurger.changePatties(DEFAULT_PATTY);
        }   
    }
    
    /**this to make addition when 
     * customer customized their burger.
     * 
     * @param line read order
     * @param theBurger this burger
     */
    private static void makeAddtion(String line, Burger theBurger) {
        
        MyStack<String> add = new MyStack<String>();
        int first, last;    
        if (line.contains("with") || line.contains("but")) {
            first = line.indexOf("with");
            last = line.indexOf("but");
            String[] temp = line.substring(first + 5, last - 1).split(" ");
            for( int i = 0; i < temp.length; i++) {
                add.push(temp[i]);
            }   
            
        } 
        while (!add.isEmpty()) {    
            if(add.peek().equalsIgnoreCase("CHEESE") || add.peek().equalsIgnoreCase("SAUCE") ||
               add.peek().equalsIgnoreCase("VEGGIES")) {    
                theBurger.addCategory(add.pop());   
                
            } else {    
                theBurger.addIngredient(add.pop());         
            }       
            
        } // end of while loop. 
    }
    
    /**remove all ingredient or 
     * categories when customer 
     * asked.
     * 
     * @param line order
     * @param theBurger this burger
     */
    private static void makeOmissions(String line, Burger theBurger) {  
        MyStack<String> add = new MyStack<String>();
        int first, last;    
        if (line.contains("with no")) {
            first = line.indexOf("no");
            last = line.indexOf("but");
            String[] temp = line.substring(first + 3, last - 1).split(" ");
            
            for( int i = 0; i < temp.length; i++) {
                add.push(temp[i]);
            }
        } while (!add.isEmpty()) {
            if(add.peek().equalsIgnoreCase("CHEESE") || add.peek().equalsIgnoreCase("SAUCE") ||
               add.peek().equalsIgnoreCase("VEGGIES")) {    
                theBurger.removeCategory(add.pop());    
            } else {            
                theBurger.removeIngredient(add.pop());          
            }       
        } // end of while loop. 
    }
    
    /**this will add or remove ingredient or
     * categories depend on the order of the customer 
     * 
     * @param line order to process
     * @param theBurger this burger
     * @param theBoo order with no or with
     */
    private static void makeException(String line, Burger theBurger, Boolean theBoo) {
        
        MyStack<String> add = new MyStack<String>();
        int first;  
        if (line.contains("but")) {
            first = line.indexOf("but");
            String[] temp = line.substring(first + 4).split(" ");
            for( int i = 0; i < temp.length; i++) {
                add.push(temp[i]);
            }               
        } 
        while (!add.isEmpty()) {        
            if(theBoo == true) {            
                if(add.peek().equalsIgnoreCase("CHEESE") || add.peek().equalsIgnoreCase("SAUCE") ||
                   add.peek().equalsIgnoreCase("VEGGIES")) {
                    theBurger.addCategory(add.pop());           
                } else {
                    theBurger.addIngredient(add.pop());
                }
            } // end of boo true
            else if (theBoo == false) {
                
                if(add.peek().equalsIgnoreCase("CHEESE") || add.peek().equalsIgnoreCase("SAUCE") ||
                   add.peek().equalsIgnoreCase("VEGGIES")) {
                    theBurger.removeCategory(add.pop());
                } else {
                    theBurger.removeIngredient(add.pop());
                }           
            }
        } // end of while loop.     
    }

    private static void makeBurger(String line, Burger theBurger) {
        if(line.contains("with no")) {
            makeOmissions(line, theBurger);
            makeException(line, theBurger, true);   
            
        } else if (line.contains("with")) {
            makeAddtion(line, theBurger);
            makeException(line, theBurger, false);      
        }       
    }
    
    
    
    public static void testMyStack() {
        
        MyStack<String> test = new MyStack<String>();
        test.push("1");
        test.push("2");
        test.push("3");
        test.push("4");
        
        System.out.println("This is test stack: " + test);
        System.out.println("This is test for peek method: " +test.peek());
        System.out.println("This is test for pop method: " +test.pop());    
        System.out.println("This is test stack: " + test);
    }
    
    public static void testBurger() {

        Burger test = new Burger(true); 
//      test.addCategory("cheese");
//      test.addCategory("veggies");
//      test.addCategory("sauce");
        //test.addIngredient(" ");
//      test.changePatties("chicken");
//      test.changePatties("chicken");
//      test.changePatties("chicken");
        System.out.println(test);
    }

} // end of class
