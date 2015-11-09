package rm.textprocessing.container.corpus;

import java.io.IOException;
import java.util.ArrayList;

import rm.util.IOUtil;
import rm.util.TSVRowSerializable;
import rm.util.TSVSerializable;

/**
 * Abstract corpus type. A corpus is a set of document identified by a unique string.
 * The documents stored in the corpus should be TSV serializable (i.e. implementing TSVRowSerializable)
 * @author Romain Mormont
 * @param <D> The document type.
 */
abstract public class AbstractCorpus<D extends TSVRowSerializable> 
	extends ArrayList<D>
	implements TSVSerializable
{
	private static final long serialVersionUID = 4728367663071157209L;
	
	@Override
	public void save_tsv(String file) throws IOException 
	{
		StringBuilder sb = new StringBuilder();
		
		for(D entry : this)
			sb.append(entry.format_tsv() + "\n");
		
		IOUtil.print_str(file, sb.toString());
	}
}
