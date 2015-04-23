package rm.textprocessing.analyzer.polarizer;

import java.util.HashMap;

/**
 * Base class for any polarity analyzer 
 * @author Romain Mormont
 * @param <P> Type representing the polarity information stored into the object
 * 
 * The polarity itself is a number in [-1,1] : 
 * - 0 : neutral
 * - < 0 : negative
 * - > 0 : positive
 *
 * An unknown word is considered neutral
 * The neutrality of a term is determine by the static member THRESHOLD_NEUTRAL.
 */
abstract public class PolarityAnalyzer<P> extends HashMap<String, P>
{
	private static final long serialVersionUID = 5521509468518089521L;

	/** Extreme scores */
	public static float NEUTRAL_SCORE = 0.0f;
	public static float NEGATIVE_SCORE = -1.0f;
	public static float POSITIVE_SCORE = 1.0f;
	
	/** Thresholds */
	public static float THRESHOLD_NEUTRAL = 0.01f;
	
	public PolarityAnalyzer()
	{
		load_sentiment_lexicon();
	}
	
	/**
	 * Loads the sentiment lexicon into the map
	 */
	abstract protected void load_sentiment_lexicon();
	
	/**
	 * Compute the polaritygiven some polarity information
	 * @param polarity_info The polarity information
	 * @return The polarity in [-1,1]
	 */
	abstract protected float compute_polarity(P polarity_info);
	
	/**
	 * Compute the polarity of the given word
	 * @param word The word for which the polarity must be computed
	 * @return The polarity score in [-1,1]
	 */
	public float get_polarity(String word)
	{
		if(!containsKey(word))
			return NEUTRAL_SCORE;
		
		return compute_polarity(get(word));
	}
	
	/**
	 * Compute the polarity class of the given word
	 * @param word The word 
	 * @return The polarity class C in { NEGATIVE, POSITIVE, NEUTRAL }
	 */
	public Polarity get_polarity_class(String word)
	{
		return get_polarity_class(get_polarity(word));
	}
	
	/**
	 * Compute the polarity class for the given polarity
	 * @param polarity A polarity value in [-1,1]
	 * @return The corresponding polarity class
	 */
	static public Polarity get_polarity_class(float polarity)
	{
		if(Math.abs(polarity) < THRESHOLD_NEUTRAL)
			return Polarity.NEUTRAL;
		
		return polarity > 0 ? Polarity.POSITIVE : Polarity.NEGATIVE;
	}
	
	/**
	 * Computet the average polarity of a text
	 * @param text The text (a set of words separated by whitespaces)
	 * @return The average polarity of the text (in [-1, 1])
	 * @note The words that are not in the lexicon are ignored
	 */
	public float get_text_polarity(String text)
	{
		String[] text_words = text.split("\\s+");
		float average_polarity = 0;
		int non_empty_words = 0;
		
		for(String word : text_words)
		{
			if(word.isEmpty() || !containsKey(word)) // skip empty words
				continue; 
			
			average_polarity += get_polarity(word);
			non_empty_words++;
		}
		
		if(non_empty_words == 0) 
			return 0.0f;
		
		return average_polarity / (float) non_empty_words;
	}
	
	/**
	 * Compute the polarity class (based on the average polarity) of a text
	 * @param text The text (a set of words separated by whitespaces)
	 * @return The polarity class of the text
	 * @note The words that are not in the lexicon are ignored
	 */
	public Polarity get_text_polarity_class(String text)
	{
		return get_polarity_class(get_text_polarity(text));
	}
}
