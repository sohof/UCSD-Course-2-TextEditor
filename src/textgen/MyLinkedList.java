package textgen;

import java.util.AbstractList;

/** A class that implements a doubly linked list
 *
 * @param <E> The type of the elements stored in the list
 * @author Sohof Dastmard
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		head = new LLNode<E>();
		tail = new LLNode<E>();
		size =0;
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 * @throws NullPointerException if trying to add null element
	 */
	public boolean add(E element ) 
	{
		if (size == 0) {  // the list is empty first available index is 0
			add(0,element);
		}
		else // if list is not empty first available index is at size 
			add(size,element);		

		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException("index out of bounds");
		
		LLNode<E> currNode = head.next; 
		
		for (int i=0; i<index; i++) {
			currNode = currNode.next;
		}		
		return currNode.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		if(element == null)
			throw new NullPointerException("Trying to add null element");
		
		// if list is not empty the first available index is at index = size 
		if(index < 0 || index > size())
			throw new IndexOutOfBoundsException("Index out of bounds");
		
		LLNode<E> newNode = new LLNode<>(element);

		LLNode<E> beforeNewNode = head;
		LLNode<E> afterNewNode = null;
		
		for (int i=0; i<index; i++) {
			beforeNewNode = beforeNewNode.next;
		}	
		afterNewNode = beforeNewNode.next;
		beforeNewNode.next = newNode;
		newNode.prev = beforeNewNode;
		newNode.next = afterNewNode;
		afterNewNode.prev = newNode; 
		size++; // Increase list size		
	
	}


	/** Return the size of the list */
	public int size() 
	{
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index out of bounds");
		// get will also throw IndexOutOfBoundsException If index is outside the bounds of the list
		E deletedNodeData = get(index);

		LLNode<E> nodeToRemove = head.next;
		
		int i = 0;
		while (index != i++)
		{
			nodeToRemove = nodeToRemove.next;
			System.out.println("index = " + i);
		}	
		nodeToRemove.prev.next = nodeToRemove.next;
		nodeToRemove.next.prev = nodeToRemove.prev;
		size--;
		return deletedNodeData;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		if (element == null)
			throw new NullPointerException("Trying to set element with a null reference");
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index out of bounds");
		
		LLNode<E> nodeToSet = head.next;
		
		int i = 0;
		while (index != i++)
		{
			nodeToSet = nodeToSet.next;
		}	
		E oldValue = nodeToSet.data; // save old value
		
		nodeToSet.data = element; // set new value
		
		return oldValue;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode() 
	{
		this(null);
	}
	
	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
