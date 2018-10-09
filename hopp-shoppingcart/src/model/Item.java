/*
 * TCSS 305
 * Assignment 2 - Shopping Cart
 */

package model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * An Item object stores information about an individual item.
 * 
 * @author Hop Pham
 * @version Jan 15 2018
 */
public final class Item {    
 
    // class constants
    
    /**
     * A default quantity to use when no other value is specified.
     */
    public static final int MIN_BULK_QUANTITY = 0;

    /**
     * A default price to use when no other value is specified.
     */
    public static final BigDecimal MIN_BULK_PRICE = BigDecimal.ZERO;
    
    // instance fields
    
    /** 
     * The name of an item. 
     */
    private String myItemName;
    
    /**
     * The price of an item.
     */
    private BigDecimal myItemPrice;
    
    /**
     * The quantity of an item.
     */
    private int myBulkQuantity;
    
    /**
     * The bulk price for the item.
     */
    private BigDecimal myBulkPrice;
    // constructors
    
    /**
     * Constructs a single item with the specified name, and price.
     * The bulk price and quantity are default Zero.
     * @param theName the name to assign to this item
     * @param thePrice the price to assign to this item
     */
    public Item(final String theName, final BigDecimal thePrice) {
        this(theName, thePrice, MIN_BULK_QUANTITY, MIN_BULK_PRICE);
    }

    /**
     * Constructs an item with the specified name, price, quantity, and price for the bulk.
     * <p>Precondition: The parameters must not violate the class invariant.
     * That is, theName must not be empty
     *      AND thePrice must be greater than Zero
     *      AND theBulkQuantity and theBulkPrice must not be null
     *      AND theBulkQuantity and theBylkPrice must both > 0 otherwise 0 </p>
     * @param theName The name of the item
     * @param thePrice The price of a single item
     * @param theBulkQuantity The quantity of the item
     * @param theBulkPrice The bulk price of the item
     */
    public Item(final String theName, final BigDecimal thePrice, final int theBulkQuantity,
                final BigDecimal theBulkPrice) {
        
        myItemName = Objects.requireNonNull(theName);
        //make sure the item's name doesn't have only space
        if (myItemName.trim().length() < 1) {
            throw new IllegalArgumentException("The item's name must not empty!");
        }        
        //make sure the single price of an item > 0
        if (thePrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The price must be greater than zero!");
        }        
        //first check both quantity and bulk price are not negative
        if (theBulkQuantity < 0 || theBulkPrice.compareTo(MIN_BULK_PRICE) < 0) {
            throw new IllegalArgumentException("Both the quantity and the bulk price"
                            + " must not be a negative number!");
        }
        //Only accept both zero or positive quantity and bulk price at a time.
        if (theBulkQuantity > 0 ^ theBulkPrice.compareTo(MIN_BULK_PRICE) > 0) {
            throw new IllegalArgumentException("The quantity and the bulk price"
                            + " must be positive numbers, or both are zero!");
        }
        // Assign value to each specified when the input values are valid.        
        myItemPrice = thePrice;
        myBulkQuantity = theBulkQuantity;
        myBulkPrice = theBulkPrice;
        
    }

    /**
     * Returns the single item price for this item.
     * @return the price of this item
     */
    public BigDecimal getPrice() {
        
        return myItemPrice;
    }
    
    /**
     * Returns the quantity of this item.
     * @return the bulk quantity of this item
     */
    public int getBulkQuantity() {
        
        return myBulkQuantity;
    }
    
    /**
     * Returns the bulk price for this item.
     * @return the bulk price of this item
     */
    public BigDecimal getBulkPrice() {
        
        return myBulkPrice;
    }

    /**
     * Returns True if the Item has bulk pricing; false otherwise.
     * @return true false value
     */
    public boolean isBulk() {
        boolean isBulkPricing = false;
        if (myBulkPrice.compareTo(BigDecimal.ZERO) > 0) {
            isBulkPricing = true;
        }
        return isBulkPricing;
    }

    /**
     * {@inheritDoc}
     * Returns a String representation of this Item: name, 
     * followed by a comma and a space, followed by price. 
     * Append an extra space and a parenthesized description of the bulk pricing 
     * that has the bulk quantity, the word “for” and the bulk price if it has bulk price. 
     */
    @Override 
    public String toString() {
        final StringBuilder builder = new StringBuilder(128); // default initial size = 16
        builder.append(myItemName);
        builder.append(", ");
        builder.append(myItemPrice);
        if (isBulk()) {
            builder.append(" (");
            builder.append(myBulkQuantity);
            builder.append(" for $");
            builder.append(myBulkPrice);
            builder.append(')');
        }      
        
        return builder.toString();
    }


    /**
     * {@inheritDoc}
     * Returns true if the specified object is equivalent to this Item, and false otherwise. 
     * Two items are equivalent if they have exactly equivalent 
     * names, prices, bulk quantities and bulk prices. 
     */
    @Override
    public boolean equals(final Object theOther) {
        boolean isEquals = false;
        if (this == theOther) {
            isEquals = true;
        } else if (theOther != null && this.getClass() == theOther.getClass()) {
            final Item otherItem = (Item) theOther; //already check same class above
            isEquals = Objects.equals(myItemName, otherItem.myItemName)
                            && myItemPrice.compareTo(otherItem.myItemPrice) == 0
                            && myBulkQuantity == otherItem.myBulkQuantity
                            && myBulkPrice.compareTo(otherItem.myBulkPrice) == 0;
                            
        }
        return isEquals;
    }

    /**
     * {@inheritDoc}
     * Returns an integer hash code for this item. 
     */
    @Override
    public int hashCode() {
        
        return Objects.hash(myItemName, myItemPrice, myBulkQuantity, myBulkPrice);
    }

}
