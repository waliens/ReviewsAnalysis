package rm.util;

import java.io.IOException;
import java.text.ParseException;

/**
 * Implemented by any object that could be serialized in the TSV format
 * @author Romain Mormont
 * @note Any class implementing this interface should provide 
 */
public interface TSVSerializable 
{
	/**
	 * Save the object in the TSV format in the given file
	 * @param file The file in which the object must be serialized in the TSV format
	 * @throws IOException If an IO error occurs
	 */
	public void save_tsv(String file) throws IOException;
	
	/**
	 * Load the object from a TSV formatted file
	 * @param file The file containing the object data in the TSV format
	 * @note The previous content of the object is erased
	 * @throws ParseException If the parsing fails
	 * @throws IOException If an IO error occurs
	 */
	public void load_tsv(String file) throws ParseException, IOException;
}
