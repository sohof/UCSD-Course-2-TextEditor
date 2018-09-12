package spelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author Sohof Dastmard
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size; 

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	/** Insert a word into the trie. This method adds a word by creating and linking the 
	 * necessary trie nodes into the trie,
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
		boolean result = addWord(word.toLowerCase(), root, 0);
		if (result == true)
			size++;
		
	    return result;
	}
	
	private boolean addWord(String word, TrieNode node, int index)
	{
		boolean result = false;	
		
		if ( index >= word.length() )  {// we reached end of word. param node is the node containing last char.	
	
			if (node.endsWord() == true)  // last node was already a valid existing word so return false
				return false;
			else {
				node.setEndsWord(true); 	// we reached the end of word which did exist in trie but was 
				return true;				// not flagged as a valid word. So mark it is as valid word		
			}
			
		}
		TrieNode next = node.insert(word.charAt(index));
		
		if ( next == null ) { // The char at pos index existed as a node. Continue with next char of word

			result = addWord(word, node.getChild(word.charAt(index)), ++index);
		}
			
		else {// The char at index 0 did not exist so we create it.
			
			if (next.getText().equalsIgnoreCase(word)) {
				next.setEndsWord(true);
				return true;
			}
			else
				result = addWord(word, next, ++index);
		}
	
		return result;
	}
	
	/** 
	 * Return the number of words in the dictionary. This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    return size;
	}
		
	@Override
	public boolean isWord(String s) 
	{	
		return isWord(s.toLowerCase(), root, 0);
	}
	
	private boolean isWord(String word, TrieNode node, int index) {
		
		boolean result = false;
		
		if ( index >= word.length() ) 
			return node.endsWord();
			
		TrieNode next = node.getChild(word.charAt(index));
		if ( next!= null) {
			result = isWord(word, next, ++index);
		}
			 
		return result;
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length, containing the 
     * numCompletions shortest legal completions of the prefix string. All legal completions must
     *  be valid words in the dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. The list of completions must contain all of the shortest 
     * completions, but when there are ties, it may break them in any order. 
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
      /**
   	  This method implements the following algorithm:
   	  1. Find the stem in the trie.  If the stem does not appear in the trie, return an
   	     empty list
   	  2. Once the stem is found, perform a breadth first search to generate completions
   	     using the following algorithm:
   	     Create a queue (LinkedList) and add the node that completes the stem to the back
   	        of the list.
   	     Create a list of completions to return (initially empty)
   	     While the queue is not empty and you don't have enough completions:
   	        remove the first Node from the queue
   	        If it is a word, add it to the completions list
   	        Add all of its child nodes to the back of the queue
   	  Return the list of completions
   	 */
    	 	TrieNode next = root;
    	 	
    	 	for (char c : prefix.toCharArray() ) {
    	 	
    	 		if (next != null)
    	 			next = next.getChild(c);			// travel down the trie looking for stem							  	 			
    	 	}
    	 	if (next == null) 						//next == null means prefix did not exist as stem
    	 		return new LinkedList<String>(); 	// return empty list
    	 	
    	 	// Do a bfs search from stem node to find possible completions, based on distance from stem
    	 	Deque<TrieNode> queue = new LinkedList<>(); // deque supports removeFirst() and removeLast() 
    	 	queue.add(next);
    	 	
    	 	List<String> completions = new LinkedList<>();
    	 	while (!queue.isEmpty() && completions.size() < numCompletions) {

    	 		TrieNode node = queue.removeFirst();
    	 		
    	 		if (node.endsWord() == true) {
	 				completions.add(node.getText());
	 		}
    	 		
    	 		Set<Character> validNextChars = node.getValidNextCharacters();

    	 		for (char c : validNextChars) { 
    	 			
    	 			queue.add(node.getChild(c));
    	 			
    	 		} // end for each
    	 		
    	 	}//end while
	
    	 return completions;
    }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}	
	
}