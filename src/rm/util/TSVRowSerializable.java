package rm.util;

/**
 * Implemented by any object that could be serialized as a row of a TSV file
 * @author Romain Mormont
 */
public interface TSVRowSerializable 
{
	/**
	 * Format the object as one-line string in the tsv format
	 * @return The tsv string
	 */
	public String format_tsv();
}
