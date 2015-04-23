package rm.textprocessing.tools;

/**
 * A class for removing numbers from a text document
 * @author Romain Mormont
 *
 */
public class NumberRemover extends PatternRemover 
{
	/**
	 * Construct the number remover
	 */
	public NumberRemover()
	{
		super("(- ?)?\\d+([\\.,]\\d+)?");
	}
}
