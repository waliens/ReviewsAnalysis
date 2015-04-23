package rm.crawler.amazon;

import java.io.IOException;
import java.text.ParseException;

import rm.textprocessing.container.corpus.AbstractCorpus;
import rm.util.IOUtil;

public class AmazonCorpus extends AbstractCorpus<AmazonReview> 
{
	private static final long serialVersionUID = -7247277302909764274L;

	/**
	 * Build an empty amazon corpus
	 */
	public AmazonCorpus() { }
	
	/**
	 * Read a corpus from a file 
	 * @param file The file containing the corpus data
	 * Each line of the file must be formatted as : id\tcontent\n
	 */
	public AmazonCorpus(String file) throws IOException, ParseException
	{ 
		load_tsv(file); 
	}

	@Override
	public void load_tsv(String file) throws ParseException, IOException 
	{
		// remove the objects stored in the map
		this.clear();
		
		String[] entries = IOUtil.read_file_lines(file);
		
		try 
		{
			for(String entry : entries)
			{
				String[] splitted = entry.split("\t");
				
				if(splitted.length != 4) 
					throw new ParseException("All the rows of the file should contain 4 elements", 0);
				
				AmazonReview review = new AmazonReview(Integer.parseInt(splitted[0]), 
													   splitted[1], splitted[2], 
													   Float.parseFloat(splitted[3]));
				add(review);
			}
		} 
		catch (NumberFormatException e) {
			throw new ParseException(e.getMessage(), 0);
		}
	}
}
