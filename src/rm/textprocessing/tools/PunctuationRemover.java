package rm.textprocessing.tools;

/**
 * A class for replacing from a String all punctuation characters by whitespaces 
 * @author Romain Mormont
 */
public class PunctuationRemover extends PatternReplacer
{
	/**
	 * Construct a punctuation remover
	 */
	public PunctuationRemover()
	{
		super("\\p{Punct}+", " ");
	}
}
