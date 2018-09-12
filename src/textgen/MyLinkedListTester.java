/**
 * 
 */
package textgen;

/**junit tests for MyLinkedList class 
*
* @author Sohof Dastmard
*/

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);		
	}

	
	/** Test if the get method is working correctly.	*/
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list. */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		try { 
			list1.remove(2); // index 2 is over max valid bound
			fail("Check out of bounds");
		}
		catch(IndexOutOfBoundsException e) {			
		}
		
		try {
		 
			emptyList.remove(0); // no element at index 0
			fail("Check out of bounds");
		}
		catch(IndexOutOfBoundsException e) {			
		}
		
		try { 
			shortList.remove(-1); // index -1 is invalid
			fail("Check out of bounds");
		}
		catch(IndexOutOfBoundsException e) {			
		}
		
		int size = LONG_LIST_LENGTH;
		for (int i = 0; i<LONG_LIST_LENGTH; i++){
			int n = longerList.remove(0); // always remove the first element till list is empty
			size--;
			assertEquals("Remove: check a is correct ", i, n);
			assertEquals("Remove: check size is correct ", size, longerList.size());		
		}
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
		try {
			emptyList.add(null);
			fail("added null to end of list");
		}
		catch(NullPointerException e) {
		}
		try {
			shortList.add(null);
			fail("added null to end of list");
		}
		catch(NullPointerException e) {
		}
		try {
			longerList.add(null);
			fail("added null to end of list");
		}
		catch(NullPointerException e) {
		}
	
		assertEquals("Check Adding 5 to end of empty list:", emptyList.add(5), true);
		assertEquals("Check if 5 was added to end of empty list:", emptyList.get(0),  (Integer)5);

		assertEquals("Check Adding C to end of shorterList ", shortList.add("C"), true);
		assertEquals("Check if C was added to end of short list:", shortList.get(2),  "C");
		
		assertEquals("Check Adding 11 to end of longerList ", longerList.add(11),true);
		assertEquals("Check if C was added to end of short list:", longerList.get(10),  (Integer)11);
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// Test size of empty list
		assertEquals("Checking empty list has size zero", emptyList.size(), 0);
		
		// Test size of short list
		assertEquals("Checking short list has size 2", shortList.size(), 2);
		
		// Test size of longer list
		assertEquals("Checking longer list has size 10", longerList.size(), 10);
		
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
		final int NEG_INDEX = -1;
		
		// We should be able to a new element at index LONG_LIST_LENGTH since that is next 
		// available index to use. But we should be able to add at LONG_LIST_LENGTH + 1
		final int OUT_OF_BOUND_INDEX = LONG_LIST_LENGTH +1; 
		
		try {
			emptyList.add(NEG_INDEX, new Integer(1));
			fail("added element at NEG_INDEX");
		}
		catch(IndexOutOfBoundsException e) {
		}
		try {
			shortList.add(NEG_INDEX, new String("C"));
			fail("added element at NEG_INDEX");
		}
		catch(IndexOutOfBoundsException e) {
		}
		try {
			longerList.add(OUT_OF_BOUND_INDEX, new Integer(11));
			fail("added element at OUT_OF_BOUND_INDEX ");
		}
		catch(IndexOutOfBoundsException e) {
		}
		
		//Testing adding element to empty list at index zero
		emptyList.add(0,5);
		assertEquals("Check Adding 5 to empty list at index 0:", emptyList.get(0), (Integer)5);

		//Testing adding elements to short list at index zero and index 1
		shortList.add(1, "C");
		assertEquals("Check Adding C to index 1 of shorterList ", shortList.get(1), "C");
		
		shortList.add(0, "a");
		assertEquals("Check Adding a to index 0 of shorterList ", shortList.get(0), "a");
		
		//Testing adding elements to long list at index zero and index 5

		longerList.add(0,100);
		longerList.add(5,500 );

		assertEquals("Check Adding 100 to index 0 in longerList ", longerList.get(0), (Integer)100);
		assertEquals("Check Adding 500 to index 5 in longerList ", longerList.get(5), (Integer)500);
		
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	try {
		emptyList.set(0, 1);
		fail("Index out of bounds");
	}
	catch(IndexOutOfBoundsException e) {
			
	}

	shortList.set(0,"F");
	assertEquals("Check setting elment 0 to F", shortList.get(0), "F");
	try {
		shortList.set(1, null);
		fail("Managed to set an index with null pointer");

	}
	catch(NullPointerException e) {	
	}
	try {
		shortList.set(2, "G");
		fail("Index out of bounds");
	}
	catch(IndexOutOfBoundsException e) {
			
	}
	
	longerList.set(3, 27);
	assertEquals("Check setting elment at index 3 to 27", longerList.get(3), (Integer)27);

	try {
		longerList.set(5, null);
		fail("Managed to set an index with null pointer");
	}
	catch(NullPointerException e) {
		
	}
	
	try {
		longerList.set(10, 10);
		fail("Index out bounds");
	}
	catch(IndexOutOfBoundsException e) {
		
	}
		
 }
}
