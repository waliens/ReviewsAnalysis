package rm.textprocessing.tools;

/**
 * A class for removing whitespace characters from a TextDocument 
 * @author Romain Mormont
 */
public class WhitespaceRemover extends PatternReplacer
{
	public WhitespaceRemover()
	{
		super("\\s+", " ");
	}
}
