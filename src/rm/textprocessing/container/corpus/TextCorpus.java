package rm.textprocessing.container.corpus;

import java.io.IOException;
import java.text.ParseException;

import rm.textprocessing.container.TextDocument;
import rm.util.IOUtil;

/**
 * Represent a corpus of documents
 * @author Romain Mormont
 */
public class TextCorpus extends AbstractCorpus<TextDocument>
{
	private static final long serialVersionUID = 2726288141933436400L;
	
	public TextCorpus() { }
	
	/**
	 * Read a corpus from a file 
	 * @param file The file containing the corpus data
	 * Each line of the file must be formatted as : id\tcontent\n
	 */
	public TextCorpus(String file) throws IOException, ParseException
	{ 
		load_tsv(file); 
	}

	@Override
	public void load_tsv(String file) throws ParseException, IOException 
	{
		// remove the objects stored in the map
		this.clear();
		
		String[] entries = IOUtil.read_file_lines(file);
		
		for(String entry : entries)
		{
			String[] splitted = entry.split("\t");
			
			if(splitted.length != 2) 
				throw new ParseException("All the rows of the file should contain 2 elements", 0);
			
			add(new TextDocument(splitted[1]));
		}
	}
}
