/*
 * TCSS 305
 * Assignment 2 - Shopping Cart
 */

package model;

import java.util.Objects;

/**
 * An ItemOrderobject stores information about a purchase order for an item: 
 * namely, a reference to the item itself and the quantity desired.
 * 
 * @author Hop Pham
 * @version Jan 15 2018
 */
public final class ItemOrder {

    /**
     * The quantity of the item order.
     */
    private final int myItemQuantity;
    
    /**
     * The item order.
     */
    private final Item myItem;
    
    /**
     * Constructs an item order with specified item object, and item quantity. 
     * @param theItem the item
     * @param theQuantity the quantity
     */
    public ItemOrder(final Item theItem, final int theQuantity) {
        
        if (theQuantity < 0) {
            throw new IllegalArgumentException("The quantity must not be a negative number!");
        }  
        
        myItem = Objects.requireNonNull(theItem);
        myItemQuantity = theQuantity;
    }

    /**
     * Returns the item order.
     * @return the item
     */
    public Item getItem() {
        
        return myItem;
    }
    
    /**
     * Returns the quantity of an item order.
     * @return the quantity
     */
    public int getQuantity() {
        
        return myItemQuantity;
    }


    /**
     * Return the name of an Item.
     */
    @Override
    public String toString() {

        return myItem.toString().substring(0, myItem.toString().indexOf(','));
    }

}
