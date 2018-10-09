import java.util.Locale;

/*
 * TCSS 342 - Burger
 */

/**
 * 
 * @author Hop pham
 * @version April 2018
 *
 */
public class Burger {
    
    /** The max patty can a burger has. */
    private static final int LIMIT_PATTY = 3;
    
    /** The max patty can a burger has. */
    private static final int NEW_PATTY_POSITION = 8;
    
    /** The string bun.*/
    private static final String BUN = "Bun";
    
    /** The default patty.*/
    private static final String PATTY = "Beef";
    
    /** The string cheddar. */
    private static final String CHEDDAR = "Cheddar";
    
    /** The string Mozzarella. */
    private static final String MOZZARELLA = "Mozzarella";
    
    /** The string Pepperjack. */
    private static final String PEPPERJACK = "Pepperjack";
    
    /** The string Ketchup. */
    private static final String KETCHUP = "Ketchup";
    
    /** The string Mustard. */
    private static final String MUSTARD = "Mustard";
    
    /** The string Mayonnaise. */
    private static final String MAYONNAISE = "Mayonnaise";    
    
    /** The string Baron-Souce. */
    private static final String BARRON_SOUCE = "Baron-Sauce";    
    
    /** The string Lettuce. */
    private static final String LETTUCE = "Lettuce";
    
    /** The string Tomato. */
    private static final String TOMATO = "Tomato";
    
    /** The string Onions. */
    private static final String ONIONS = "Onions";
    
    /** The string Pickle. */
    private static final String PICKLE = "Pickle";
    
    /** The string Mushrooms. */
    private static final String MUSHROOMS = "Mushrooms";

    /** The string CHEESE. */
    private static final String CHEESE_STRING = "cheese";
    /** The string Veggies. */
    private static final String VEGGIES_STRING = "veggies";
    /** The string Sauce. */
    private static final String SAUCE_STRING = "sauce";
    
    /** CHEESE category.*/
    private static final MyStack<String> CHEESE = new MyStack<String>();
    /** Sauce category.*/
    private static final MyStack<String> SAUCE = new MyStack<String>();
    /** Veggies category.*/
    private static final MyStack<String> VEGGIES = new MyStack<String>();
    
    static {
        CHEESE.push(PEPPERJACK);
        CHEESE.push(MOZZARELLA);
        CHEESE.push(CHEDDAR);
        
        SAUCE.push(MAYONNAISE);
        SAUCE.push(BARRON_SOUCE);
        SAUCE.push(MUSTARD);
        SAUCE.push(KETCHUP);
        
        VEGGIES.push(PICKLE);
        VEGGIES.push(LETTUCE);
        VEGGIES.push(TOMATO);
        VEGGIES.push(ONIONS);
        VEGGIES.push(MUSHROOMS);
    }
    
    /** Count Patty. */
    private int myCountPatty;
    /** The stack will store customer's burger item. */
    private MyStack<String> myBurger;
    
    /** The stack will store sample burger. */
    private final MyStack<String> mySampleBurger;
    
    /** String Patty. */
    private String myPatty;
    
    
    /**
     * Constructs a Burger.
     * Only a bun and  patty if theWorks is false and a Baron Burger if theWorks is true.
     * @param theWorks the works
     */
    public Burger(final boolean theWorks) {
        myCountPatty = 1;
        myPatty = PATTY;        
        myBurger = new MyStack<String>();
        mySampleBurger = buildBaronBurger();
        if (theWorks) {
            myBurger = buildBaronBurger();            
        } else {
            myBurger = buildBurger();            
        }        
    }
       
    /**
     * Changes all patties in the Burger to the pattyType.
     * @param theType type of the Patty
     */
    public void changePatties(final String theType) {        
        final MyStack<String> temp = new MyStack<String>();
        while (!myBurger.isEmpty()) {
            if (myBurger.peek().equalsIgnoreCase(myPatty)) {
                temp.push(theType);
                myBurger.pop();
            } else {
                temp.push(myBurger.pop());
            }
        }
        myPatty = theType;
        pushBackStack(temp, myBurger);
    }
    
    /**
     * Add a patty to the burger.
     */
    public void addPatty() {
        if (myCountPatty < LIMIT_PATTY) {
            myCountPatty++;
            addNewPatty();
        }        
    }
    
    /**
     * Helper method to add patty.
     */
    private void addNewPatty() {
        final MyStack<String> temp = new MyStack<String>();
        final MyStack<String> tempSample = new MyStack<String>();
        for (int i = 1; i <= NEW_PATTY_POSITION; i++) {
            temp.push(myBurger.pop());
            tempSample.push(mySampleBurger.pop());            
        }
        myBurger.push(myPatty);
        mySampleBurger.push(myPatty);
        pushBackStack(tempSample, mySampleBurger);
        pushBackStack(temp, myBurger);
    }
    
    /**
     * Helper method to push the temp burger back to the building Burger.
     * @param theTarget the temp burger.
     * @param theDes 
     */
    private void pushBackStack(final MyStack<String> theTarget, final MyStack<String> theDes) {
        while (!theTarget.isEmpty()) {
            theDes.push(theTarget.pop());
        }
    }
        
    /**
     * This method will add items of the type to the Burger in the proper locations.
     * @param theType the category type
     */
    public void addCategory(final String theType) {
        switch (theType.toLowerCase(Locale.ENGLISH)) {
            case CHEESE_STRING:
                addSpecificCategory(CHEESE);
                break;
            case VEGGIES_STRING:
                addSpecificCategory(VEGGIES);
                break;
            case SAUCE_STRING:
                addSpecificCategory(SAUCE);
                break;
            default:
                break;
        }
    }
    
    /**
     * Helper method to add a cheese category to the burger.
     * @param theCategory the category.
     */
    private void addSpecificCategory(final MyStack<String> theCategory) {
        final MyStack<String> tempCategory = new MyStack<String>();        
        while (!theCategory.isEmpty()) {
            addIngredient(theCategory.peek());
            tempCategory.push(theCategory.pop());            
        }
        pushBackStack(tempCategory, theCategory);
    }    
   
    /**
     * This method will remove all items of the type from the Burger.
     * @param theType the category type
     */
    public void removeCategory(final String theType) { 
        switch (theType) {
            case CHEESE_STRING:
                removeSpecificCategory(CHEESE);
                break;
            case VEGGIES_STRING:
                removeSpecificCategory(VEGGIES);
                break;
            case SAUCE_STRING:
                removeSpecificCategory(SAUCE);
                break;
            default:
                break;
        }        
    }
    
    /**
     * Helper method to remove a specific category.
     * @param theCategory the category
     */
    private void removeSpecificCategory(final MyStack<String> theCategory) {
        final MyStack<String> tempCategory = new MyStack<String>();
        while (!theCategory.isEmpty()) {           
            removeIngredient(theCategory.peek());
            tempCategory.push(theCategory.pop());            
        }  
        pushBackStack(tempCategory, theCategory);
    }
    
    /**
     * Removes the ingredient type from the Burger.
     * @param theType the ingredient type.
     */
    public void removeIngredient(final String theType) {
        final MyStack<String> temp = new MyStack<String>();
        while (!myBurger.isEmpty() && !myBurger.peek().equalsIgnoreCase(theType)) {           
            //stop at the target or when the stack is empty.
            temp.push(myBurger.pop());            
        }  
        //make sure the stack is not empty. this meaning the while loop stop at 
        //the target position.
        if (!myBurger.isEmpty()) {
            myBurger.pop();
            myBurger.push("");
        }
        pushBackStack(temp, myBurger);    
    }
    /**
     * This method adds the ingredient type to the Burger in the proper location.
     * @param theType the ingredient.
     */
    public void addIngredient(final String theType) {
        final MyStack<String> tempBurger = new MyStack<String>();   
        final MyStack<String> tempSampleBurger = new MyStack<String>();
        while (!mySampleBurger.isEmpty()
                       && !mySampleBurger.peek().equalsIgnoreCase(theType)) {
            tempBurger.push(myBurger.pop());
            tempSampleBurger.push(mySampleBurger.pop());
        }
        if (!mySampleBurger.isEmpty()) {
            myBurger.pop();
            myBurger.push(theType);
        }
        pushBackStack(tempBurger, myBurger);
        pushBackStack(tempSampleBurger, mySampleBurger);
    }
    
    /**
     * This method converts the Burger to a String for display.
     * @return string Burger.
     */
    @Override
    public String toString() {
        final MyStack<String> tempBurger = new MyStack<String>();
        final MyStack<String> currentBurger = new MyStack<String>();
//        final MyStack<String> returnBurger = new MyStack<String>();
        while (!myBurger.isEmpty()) {            
            if (myBurger.peek().length() >= 1) {
                currentBurger.push(myBurger.peek());
            }
            tempBurger.push(myBurger.pop());
        }
        pushBackStack(tempBurger, myBurger);        
        return currentBurger.toString();
    }
    /**
     * Helper method to build a default burger.
     * @return the burger
     */
    private MyStack<String> buildBurger() {
        final MyStack<String> burger = new MyStack<String>();
        burger.push("");
        burger.push(BUN);
        burger.push("");
        burger.push("");
        burger.push("");
        burger.push("");
        burger.push("");
        burger.push("");
        burger.push("");
        burger.push("");
        burger.push(PATTY);
        burger.push("");
        burger.push("");
        burger.push("");
        burger.push(BUN);
        return burger;
    }
    
    /**
     * Helper method to build The Burger Baron’s official recipe.
     * @return the Baron Burger
     */
    private MyStack<String> buildBaronBurger() {
        final MyStack<String> baronBurger = new MyStack<>();
        baronBurger.push(PICKLE);
        baronBurger.push(BUN);
        baronBurger.push(MAYONNAISE);
        baronBurger.push(BARRON_SOUCE);
        baronBurger.push(LETTUCE);
        baronBurger.push(TOMATO);
        baronBurger.push(ONIONS);
        baronBurger.push(PEPPERJACK);
        baronBurger.push(MOZZARELLA);
        baronBurger.push(CHEDDAR);
        baronBurger.push(PATTY);
        baronBurger.push(MUSHROOMS);
        baronBurger.push(MUSTARD);
        baronBurger.push(KETCHUP);
        baronBurger.push(BUN);
        return baronBurger;
    }
    
}
