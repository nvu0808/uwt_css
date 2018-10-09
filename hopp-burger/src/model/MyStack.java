/**
 * 
 */
package model;

//import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author tamnguyen
 *
 */
public class MyStack<Type>{

	private int size;
	private Node<Type> head;
	
	
	public MyStack(){
		
		head =  new Node<Type>();
		size = 0;		
	} // end of constructor;
	
	public boolean isEmpty() {
		
		return size == 0;	
	}
	
	public int size() {
		
		return size;
	}
	
	/*
	 * add item to the front of the previous
	 * node and connect the new node to the previous.
	 */
	public void push(Type theItem) {
		
		if (head == null) {
			
			head = new Node<Type>(theItem, null);
		}else {
		
			Node<Type> previous = head;
			head = new Node<Type>(theItem, previous);
			size++;	
			
		}
	}
	
	
	/**
	 * check for if the node is empty
	 * if not copy item in the node
	 * to the return variable, and delete
	 * the node. and resize the stack.
	 * 
	 * @return item in the node.
	 */
	public Type pop(){
		
		if(isEmpty()) {
			throw new NoSuchElementException("node is empty.");
		}
		
		Type item = head.item;
		head = head.next;
		size--;	
		return item;	
	}
	
	/**
	 * return the item in the
	 * first node.
	 * @return first item.
	 */
	public Type peek() {
		
		if(isEmpty()) {
			throw new NoSuchElementException("node is empty");
		}
		return head.item;	
	}
	
	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder(200);
		Node<Type> current = head;
		str.append('[');
		while (current.next.next != null) {	
			str.append(current.item);
			str.append(",");
			current = current.next;
		}
		str.append(current);
		str.append(']');
		return str.toString();
	}
	
	/*
	 * Inner class to create stack.
	 */
	@SuppressWarnings("hiding")
	private class Node<Type> {
		
		private Type item;
		private Node<Type> next;
		
		
		private Node() {
			item = null;
			next = null;
			
		}
		private Node(Type theItem, Node<Type> theNext) {
			
			this.item = theItem;
			this.next = theNext;
			
		} // end of stack constructor.
		
		@Override
		public String toString() {
			
			return item.toString();
			
		} // end of toString.
		
	} // end of Stack class

	

}
