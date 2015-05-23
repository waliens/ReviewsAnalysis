package rm.textprocessing.analyzer.polarizer;

import rm.util.IOUtil;

/**
 * An polarity analyzer using the Lui Bing polarity lexicons
 * @author Romain Mormont
 * Source : http://www.cs.uic.edu/~liub/
 */
public class LiubAnalyzer extends PolarityAnalyzer<Float> 
{
	private static final long serialVersionUID = -5577626286552704471L;

	@Override
	protected void load_sentiment_lexicon() 
	{
		String[] lines = IOUtil.read_file_lines("lexicon/polarity/liub_lexicon.txt");
		
		for(String line : lines)
		{
			if(line.startsWith(";") || line.isEmpty())
				continue;
			
			String[] splitted = line.split("\t");
			
			if(splitted.length != 2)
				continue;
			
			put(splitted[1], Float.parseFloat(splitted[0]));
		}
	}

	@Override
	protected float compute_polarity(Float polarity_info) 
	{
		return polarity_info;
	}
}
