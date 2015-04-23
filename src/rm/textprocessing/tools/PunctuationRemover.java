package rm.textprocessing.tools;

/**
 * A class for replacing punctuation char by whitespaces from a TextDocument
 * @author Romain Mormont
 *
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
