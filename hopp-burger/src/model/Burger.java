/**
 * 
 */
package model;


/**
 * @author tamnguyen
 *
 */
public class Burger {
	
	/* declare constant variables.*/
	private final static String BUN = "Bun";
	private final static String DEFAULT_PATTY = "Beef";
	private final static String MAYONNAISE = "Mayonnaise";
	private final static String BARON_SAUCE = "Baron-Sauce";
	private final static String PICKLE = "Pickle";
	private final static String LETTUCE = "Lettuce";
	private final static String TOMATO = "Tomato";
	private final static String ONIONS = "Onions";
	private final static String PEPPERJACK = "PepperJack";
	private final static String MOZZARELLA = "Mozzarella";
	private final static String CHEDDAR = "Cheddar";
	private final static String MUSHROOMS = "Mushrooms";
	private final static String MUSTARD = "Mustard";
	private final static String KETCHUP = "Ketchup";
	private final static String CHICKEN = "Chicken";
	private final static String VEGGIE = "Veggie";
	private final static String CHEESE = "Cheese";
	private final static String VEGGIES = "Veggies";
	private final static String SAUCE = "Sauce";
	
	 /* variable to make burger*/
	 private MyStack<String> myBurger;
	 private MyStack<String> mySampleBurger; 
	
	
	
	
	public Burger(boolean theWorks) {
		
		
		myBurger = new MyStack<String>();
		mySampleBurger = new MyStack<String>();
		
		if(theWorks == true) {		
			myBurger = baronBurgerRecipe();	
			mySampleBurger = baronBurgerRecipe();
		} else {		
			burgerRecipe();		
			mySampleBurger = baronBurgerRecipe();	
			
		}
			
	} // end of Burger constructor
	
	/**
	 * helper method to build baron burger.
	 */
	private MyStack<String> baronBurgerRecipe() {
	 final MyStack<String> baron = new MyStack<String>();
		baron.push(BUN);
	    baron.push(KETCHUP);
		baron.push(MUSTARD);
		baron.push(MUSHROOMS);
		baron.push(DEFAULT_PATTY);
		baron.push(CHEDDAR);
		baron.push(MOZZARELLA);
		baron.push(PEPPERJACK);
		baron.push(ONIONS);
		baron.push(TOMATO);
		baron.push(LETTUCE);
		baron.push(BARON_SAUCE);
		baron.push(MAYONNAISE);
		baron.push(BUN);
		baron.push(PICKLE);
		
		//System.out.println(baron);
		return baron;
	
	} // end of baron recipe
	
	/*
	 * private method to build regular burger.
	 */
	private void burgerRecipe() {	
		myBurger.push(BUN);
		myBurger.push("");
		myBurger.push("");
		myBurger.push("");
		myBurger.push(DEFAULT_PATTY);
		myBurger.push("");
		myBurger.push("");
		myBurger.push("");
		myBurger.push("");
		myBurger.push("");
	    myBurger.push("");
	    myBurger.push("");
	    myBurger.push("");
	    myBurger.push(BUN);
		myBurger.push("");	
	}
	
	/*
	 * this method will check to make sure
	 * the burger is not empty, and make it will stop 
	 * when it finds the default patty in the burger(beef)
	 * then it will take out the patty if it is not the one
	 * customer order.
	 */
	public void changePatties(String pattyType) {
		
		final MyStack<String> temp = new MyStack<String>();	
		while(!myBurger.isEmpty() && !myBurger.peek().equalsIgnoreCase(DEFAULT_PATTY)) {
			
			temp.push(myBurger.pop());
		}
		if(pattyType.equalsIgnoreCase(DEFAULT_PATTY)) {
			
			pushBackTheBurger(temp);	
			
		}else if(pattyType.equalsIgnoreCase(CHICKEN)) {
			
			myBurger.pop();
			//temp.push(myBurger.pop());
			myBurger.push(CHICKEN);
			pushBackTheBurger(temp);	
			
		}else if(pattyType.equalsIgnoreCase(VEGGIE)) {
			myBurger.pop();
			//temp.push(myBurger.pop());
			myBurger.push(VEGGIE);
			pushBackTheBurger(temp);	
		}
		
	} // end of change Patties.
	
	/*
	 * this will add patty to the burger.
	 * default to beef. will change when needed
	 * it will have to call method change patties
	 * to change.
	 */
	public void addPatty() {

		final MyStack<String> temp = new MyStack<String>();
		final MyStack<String> temp2 = new MyStack<String>();	
		while(!mySampleBurger.isEmpty() && !mySampleBurger.peek().equalsIgnoreCase(ONIONS)) {	
			 temp2.push(mySampleBurger.pop());
			 if (!myBurger.isEmpty()) {
			     temp.push(myBurger.pop());
			 }
		}		
		if (!myBurger.isEmpty()) {
		    temp.push(myBurger.pop());
		    myBurger.push(DEFAULT_PATTY);
		}
		pushBackTheBurger(temp);
		pushBackTheBurgerSample(temp2);	
	}
	
	/**
	 * helper method to push back the second
	 * half of the burger.
	 * 
	 * @param theItem temp stack.
	 */
	private void pushBackTheBurger(MyStack<String> theItem) {
		
		while(!theItem.isEmpty()) {
			myBurger.push(theItem.pop());
		}	
	}
	
	
	/**
	 * helper method to push back
	 * the items takes out from the main
	 * stack. this is for the sample stack,
	 * 
	 * @param theItem stack need to push back
	 */
	private void pushBackTheBurgerSample(MyStack<String> theItem) {
		
		while(!theItem.isEmpty()) {
			mySampleBurger.push(theItem.pop());
		}	
	}
	
	/**this method calls the addIngredient to
	 * add all the type to the burger called.
	 * 
	 * @param type type of category needs to add.
	 */
	public void addCategory(String type) {
		if (type.equalsIgnoreCase(CHEESE)) {
			addIngredient(PEPPERJACK);
			addIngredient(MOZZARELLA);
			addIngredient(CHEDDAR);
			
		} else if (type.equalsIgnoreCase(SAUCE)) {
			addIngredient(MAYONNAISE);
			addIngredient(BARON_SAUCE);
			addIngredient(MUSTARD);
			addIngredient(KETCHUP);		
			
		} else if (type.equalsIgnoreCase(VEGGIES)){
			addIngredient(PICKLE);
			addIngredient(LETTUCE);
			addIngredient(TOMATO);
			addIngredient(ONIONS);
			addIngredient(MUSHROOMS);
		}
		
	}
	
	
	/*
	 * this method will remove all the items 
	 * belongs to the category provides by
	 * the customer.
	 */
	public void removeCategory(String type) {
		if (type.equalsIgnoreCase(CHEESE)) {	
			removeIngredient(PEPPERJACK);
			removeIngredient(MOZZARELLA);
			removeIngredient(CHEDDAR);
			
		} else if (type.equalsIgnoreCase(SAUCE)) {
			removeIngredient(MAYONNAISE);
			removeIngredient(BARON_SAUCE);
			removeIngredient(MUSTARD);
			removeIngredient(KETCHUP);
						
		} else {
			removeIngredient(PICKLE);
			removeIngredient(LETTUCE);
			removeIngredient(TOMATO);
			removeIngredient(ONIONS);
			removeIngredient(MUSHROOMS);
		}
				
	}
	
	
	/**
	 * this method will check for the
	 * type of ingredient provided and will
	 * remove from sample burger so it
	 * can add to the burger at the right
	 * location.
	 * 
	 * @param type type of ingredient
	 */
	public void addIngredient(String type) {		
		final MyStack<String> temp = new MyStack<String>();
		final MyStack<String> temp2 = new MyStack<String>();
		while (!mySampleBurger.isEmpty() && !mySampleBurger.peek().equalsIgnoreCase(type)) {		
		    temp2.push(mySampleBurger.pop());
		    if(!myBurger.isEmpty()) {
		        temp.push(myBurger.pop());
			}			
		}	
		if (!myBurger.isEmpty()) {
		    myBurger.pop();
		    myBurger.push(type);
		}
		pushBackTheBurger(temp);
		pushBackTheBurgerSample(temp2);	
	}
 

	/**
	 * it will look for the provided ingredient
	 * and remove it.
	 * 
	 * @param type indicate what ingredient to be removed
	 */
	public void removeIngredient(String type) {	
	    final MyStack<String> temp = new MyStack<String>();
        while (!myBurger.isEmpty() && !myBurger.peek().equalsIgnoreCase(type)) {           
            //stop at the target or when the stack is empty.
            temp.push(myBurger.pop());            
        }  
        //make sure the stack is not empty. this meaning the while loop stop at 
        //the target position.
        if (!myBurger.isEmpty()) {
            myBurger.pop();
            myBurger.push("");
        }
        pushBackTheBurger(temp);    
	    
//		final MyStack<String> temp = new MyStack<String>();
//		while(!myBurger.isEmpty() && !myBurger.peek().equalsIgnoreCase(type)) {		
//			temp.push(myBurger.pop());				
//		}		
//		if(myBurger.peek().equalsIgnoreCase(type)) {
//			myBurger.pop();				
//		}
//		myBurger.push(" ");
//		pushBackTheBurger(temp);		
	}
	
	@Override
	public String toString() {
		final MyStack<String> temp = new MyStack<String>();
		while (!myBurger.isEmpty()) {		
			temp.push(myBurger.pop());
		}	
		
		while (!temp.isEmpty()) {
			if(temp.peek().equals("")) {		
				temp.pop();
				//System.out.println("this is burger"+ temp);
			} else {
				myBurger.push(temp.pop());
				
			}
		}
		return myBurger.toString();		
	}
	
} // end of class Burger.
