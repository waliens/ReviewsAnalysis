package rm.textprocessing.tools;

/**
 * A tool for replacing every serie of space characters by a single whitespace
 * @author Romain Mormont
 */
public class WhitespaceRemover extends PatternReplacer
{
	public WhitespaceRemover()
	{
		super("\\s+", " ");
	}
}
