/*
 * TCSS 305
 * Assignment 2 - Shopping Cart
 */
package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.math.BigDecimal;
import model.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Test for Item class.
 * 
 * @author Hop Pham
 * @version Jan 15 2018
 */
public class TestItem {
    
    /** Bulk quantity of an item to use when no other value is specified. */
    private static final int BULK_QUANTITY = 0;
    
    /** Name of an item used when comparing the item's name. */
    private static final String  ITEM_NAME = "Laptop";
    
    /** Price of an item used when comparing the price of an item. */
    private static final BigDecimal  ITEM_PRICE = BigDecimal.TEN;
    
    /** Bulk price of an item to used when no other price is specified. */
    private static final BigDecimal BULK_PRICE = BigDecimal.ZERO;
    
    /** The item that I will use in the tests. */
    private Item myItem;

    /**
     * A method to initialize the test fixture before each test.
     */
    @Before
    public void setUp() {
        myItem = new Item(ITEM_NAME, ITEM_PRICE);
    }

    /**
     * After test a method.
     */
    @After
    public void tearDown() {
        
    }

    /**
     * Test method for {@link model.Item#hashCode()}.
     */
    @Test
    public void testHashCode() {
        final Item itemNew = new Item(ITEM_NAME, ITEM_PRICE);
        assertEquals(myItem.hashCode(), itemNew.hashCode());
    }

    /**
     * Test method for {@link model.Item#Item(java.lang.String, java.math.BigDecimal)}.
     */
    @Test
    public void testItemStringBigDecimal() {       
        assertEquals(myItem.getPrice(), ITEM_PRICE);
        assertEquals(myItem.toString().substring(0, ITEM_NAME.length()), ITEM_NAME);
    }

    /**
     * Test empty name for constructor 
     * {@link model.Item#Item(java.lang.String, java.math.BigDecimal)}.
     */
    @Test (expected = IllegalArgumentException.class) 
    public void testItemStringEmpty() {
        final String itemName = "";
        myItem = new Item(itemName, ITEM_PRICE);
    }
    
    /**
     * Test null name for constructor 
     * {@link model.Item#Item(java.lang.String, java.math.BigDecimal)}.
     */
    @Test (expected = NullPointerException.class) 
    public void testItemStringNull() {
        final String itemName = null;
        myItem = new Item(itemName, ITEM_PRICE);
    }
    
    /**
     * Test illegal price for constructor 
     * {@link model.Item#Item(java.lang.String, java.math.BigDecimal)}.
     */
    @Test (expected = IllegalArgumentException.class) 
    public void testItemBigDecimalIllegal() {
        final BigDecimal itemPrice = new BigDecimal("-10.00");
        myItem = new Item(ITEM_NAME, itemPrice);
    }
    
    /**
     * Test negative bulk quantity for constructor 
     * {@link model.Item#Item(java.lang.String, java.math.BigDecimal)}.
     */
    @Test (expected = IllegalArgumentException.class) 
    public void testItemBigDecimalIllegalQuantity() {
        final int bulkQuantity = -1;
        final BigDecimal bulkPrice = new BigDecimal("-20.0");
        myItem = new Item(ITEM_NAME, ITEM_PRICE, bulkQuantity, bulkPrice);
    }
    
    /**
     * Test negative bulk price for constructor 
     * {@link model.Item#Item(java.lang.String, java.math.BigDecimal)}.
     */
    @Test (expected = IllegalArgumentException.class) 
    public void testItemBigDecimalBulkPrice() {
        final BigDecimal bulkPrice = new BigDecimal("-10.0");
        myItem = new Item(ITEM_NAME, ITEM_PRICE, BULK_QUANTITY, bulkPrice);
    }
    
    /**
     * Test null price for constructor 
     * {@link model.Item#Item(java.lang.String, java.math.BigDecimal)}.
     */
    @Test (expected = NullPointerException.class) 
    public void testItemBigDecimalNull() {
        final BigDecimal itemPrice = null;
        myItem = new Item(ITEM_NAME, itemPrice);
    }
    
    /**
     * Test illegal bulk and bulk quantity price for constructor 
     * {@link model.Item#Item(java.lang.String, java.math.BigDecimal)}.
     */
    @Test (expected = IllegalArgumentException.class) 
    public void testItemBigDecimalBulk() {
        final int itemBulkQuantity = 10;
        myItem = new Item(ITEM_NAME, ITEM_PRICE, itemBulkQuantity, BULK_PRICE);
    }
    
    /**
     * Test method for {@link model.Item#Item(
     * java.lang.String, java.math.BigDecimal, int, java.math.BigDecimal)}.
     */
    @Test
    public void testItemStringBigDecimalIntBigDecimal() {
        myItem = new Item(ITEM_NAME, ITEM_PRICE, BULK_QUANTITY, BULK_PRICE);
        assertEquals(ITEM_PRICE, myItem.getPrice());
        assertEquals(BULK_QUANTITY, myItem.getBulkQuantity());
        assertEquals(BULK_PRICE, myItem.getBulkPrice());
    }

    /**
     * Test method for {@link model.Item#getPrice()}.
     */
    @Test
    public void testGetPrice() {
        assertEquals(myItem.getPrice(), ITEM_PRICE);
    }

    /**
     * Test method for {@link model.Item#getBulkQuantity()}.
     */
    @Test
    public void testGetBulkQuantity() {
        assertEquals(myItem.getBulkPrice(), BigDecimal.ZERO);
    }

    /**
     * Test method for {@link model.Item#getBulkPrice()}.
     */
    @Test
    public void testGetBulkPrice() {
        myItem = new Item(ITEM_NAME, ITEM_PRICE, BULK_QUANTITY, BULK_PRICE);
        assertEquals(myItem.getBulkPrice(), BULK_PRICE);
    }

    /**
     * Test method for {@link model.Item#isBulk()}.
     */
    @Test
    public void testIsBulk() {
        final int bulkQuantity = 10;
        myItem = new Item(ITEM_NAME, ITEM_PRICE, bulkQuantity, BigDecimal.TEN);
        assertEquals(myItem.isBulk(), true);
    }

    /**
     * Test bulk for method {@link model.Item#toString()}.
     */
    @Test
    public void testToStringBulk() {
        final int bulkQuantity = 10;
        myItem = new Item(ITEM_NAME, ITEM_PRICE, bulkQuantity, BigDecimal.TEN);        
        final String resultString = ITEM_NAME + ", " + ITEM_PRICE + " (" 
                                + 10 + " for $" + BigDecimal.TEN + ")";
        assertEquals(myItem.toString(), resultString);
    }

    /**
     * Test self and true equally for method {@link model.Item#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObjectSelf() {
        final Item itemTest = new Item(ITEM_NAME, ITEM_PRICE, 
                                       BULK_QUANTITY, BULK_PRICE);
         
        assertEquals(true, myItem.equals(myItem));
        assertEquals(true, myItem.equals(itemTest));
    }
    
    /**
     * Test method for {@link model.Item#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObject() { 
        final int bulkQuantity = 100;
        final Item itemTest = new Item(
                     ITEM_NAME, BigDecimal.ONE, bulkQuantity, BigDecimal.ONE);
        assertEquals(false, myItem.equals(
                     new Item("Wrong", ITEM_PRICE, bulkQuantity, BigDecimal.ONE)));
        assertEquals(false, myItem.equals(
                     new Item(ITEM_NAME, BigDecimal.ONE)));        
        assertEquals(false, myItem.equals(
                     new Item(ITEM_NAME, ITEM_PRICE, bulkQuantity, BigDecimal.ONE)));
        assertEquals(false, itemTest.equals(
                     new Item(ITEM_NAME, BigDecimal.ONE, bulkQuantity, BigDecimal.TEN)));
        assertNotEquals("equals() fails to return false when passed a null parameter",
                        myItem, null);
        assertNotEquals("equals() fails to return false when passed the wrong parameter type",
                        myItem, new Color(bulkQuantity, bulkQuantity, bulkQuantity));
                             
    }
}
