/*
 * TCSS 305
 * Assignment 2 - Shopping Cart
 */

package model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * A ShoppingCartobject stores information about the customer's overall purchase.
 * 
 * @author Hop Pham
 * @version Jan 15 2018
 */
public class ShoppingCart {

    /**
     * The membership status.
     */
    private boolean myMembership;
    
    /**
     * The map items.
     */
    private final Map<Item, ItemOrder> myShoppingCart;
    
    /**
     * Constructs an empty shopping cart.
     */
    public ShoppingCart() {
        myShoppingCart = new HashMap<Item, ItemOrder>();
        myMembership = false; //initialize default initial status
    }


    /**
     * Adds the item order into a map. 
     * Update the quantity, otherwise add new item order to the shopping cart.
     * @param theOrder the item order
     */
    public void add(final ItemOrder theOrder) {
        Objects.requireNonNull(theOrder);
        //check if the item is already added then update the quantity, 
        //otherwise adds new item order to the map
        if (myShoppingCart.containsKey(theOrder.getItem())) {            
            myShoppingCart.replace(theOrder.getItem(), theOrder);
        } else {
            myShoppingCart.put(theOrder.getItem(), theOrder);   
        }
    }


    /**
     * Sets status for a customer.
     * @param theMembership the membership status
     */
    public void setMembership(final boolean theMembership) {
        
        myMembership = !myMembership;
    }

    /**
     * Calculate the total. Apply membership price if available.
     * @return the total
     */
    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        final Iterator<Item> itemsShoppingCart = myShoppingCart.keySet().iterator();
        while (itemsShoppingCart.hasNext()) {
            final Item itemOrderedName = itemsShoppingCart.next();
            final ItemOrder itemOrdered = myShoppingCart.get(itemOrderedName);
            if (myMembership) {
                if (myShoppingCart.get(itemOrderedName).getItem().isBulk()) {
                    total = total.add(bulkCalculateForMember(itemOrdered));
                } else {
                    total = total.add(itemOrdered.getItem().getPrice().
                              multiply(new BigDecimal(itemOrdered.getQuantity())));
                }                
            } else { //not a member
                total = total.add(itemOrdered.getItem().getPrice().
                                  multiply(new BigDecimal(itemOrdered.getQuantity())));
            }
            
        }
        return total;
    }
    
    /**
     * Clear the shopping cart.
     */
    public void clear() {
        myShoppingCart.clear();
    }
    
    /**
     * Calculate the total for an bulk item order.
     * @param theItemOrdered the item ordered
     * @return the total for an item
     */
    private BigDecimal bulkCalculateForMember(final ItemOrder theItemOrdered) {
        BigDecimal result = BigDecimal.ZERO;
        final int itemRemainder = theItemOrdered.getQuantity() 
                        % theItemOrdered.getItem().getBulkQuantity();
        final int bulkTimes = theItemOrdered.getQuantity() 
                        / theItemOrdered.getItem().getBulkQuantity();
        result = result.add(theItemOrdered.getItem().getBulkPrice().
                            multiply(new BigDecimal(bulkTimes)));
        result = result.add(theItemOrdered.getItem().getPrice().
                            multiply(new BigDecimal(itemRemainder)));
        return result;
    }
    
    /**
     * Return size of the map.
     */
    @Override
    public String toString() {
        
        return "Size of your cart is: " + myShoppingCart.size();
    }

}
