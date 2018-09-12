package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author Sohof Dastmard
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		if (wordList.size() > 0) // Just return if generator already has been trained.
			return;
		
		String [] tokens = (sourceText.trim()).split("\\s+");
		
		this.starter = tokens[0];
		String prevWord = starter;
				
		for (int i=1; i < tokens.length; ++i) {
			
			String w = tokens[i]; // current word we are looking at.
			ListNode node = wordNodeExists(prevWord);
			if (node != null) {  // i.e prevWord did exist in the wordlist
				node.addNextWord(w);		
			}
			
			else {
				ListNode newNode = new ListNode(prevWord);
				newNode.addNextWord(w);
				wordList.add(newNode);
				
			}
		prevWord = w;
			
		}
		// If last word does not exist in the word list then add starter to 
		// be a next word for the last word in the source text. And add the node to word list
		if (wordNodeExists(prevWord) == null) {
			ListNode newNode = new ListNode(prevWord);
			newNode.addNextWord(starter);
			wordList.add(newNode);
		}

	}

	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {

		String emptyString = "";  
		if (wordList.isEmpty()) // If generator is not trained yet, empty string should be returned
			return emptyString;
		
		if (numWords <= 0)
			return emptyString;
		
		String currWord = starter;
		StringBuilder output = new StringBuilder(currWord + " ");
		int nrOfWordsGenerated = 1;	

		while (nrOfWordsGenerated < numWords ) {

			ListNode currNode = wordNodeExists(currWord);
			String nextWord = currNode.getRandomNextWord(rnGenerator);
			output.append(nextWord + " ");
			currWord = nextWord;
			nrOfWordsGenerated++;		
		}
		return output.toString();
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
		
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		wordList.clear();
		starter = "";
		train(sourceText);
	}
	
	/**
	 * Given a word it check if the wordList has a ListNode with the given word.
	 * Returns the ListNode if there is such a node, returns null otherwise. 
	 * */
	private ListNode wordNodeExists(String word) {
			
		for (ListNode node : wordList) {
			
			if (node.getWord().equals(word))
				return node;
			
		}
		return null;
	}
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		//String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		String textString = "hi hi there hi";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(5));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. 
 * 
 */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		int randomPos = generator.nextInt(nextWords.size());

		String randomNextWord = nextWords.get(randomPos);;
		
	    return randomNextWord;
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


