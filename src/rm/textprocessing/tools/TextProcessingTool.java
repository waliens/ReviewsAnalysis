package rm.textprocessing.tools;

import rm.textprocessing.container.corpus.TextCorpus;
import rm.textprocessing.container.TextDocument;

/**
 * A class implemented by all the text processing tool
 * A text processing tool is an object that can perform a processing operation on a String object (through the method 'process')
 * @author Romain Mormont
 */
public abstract class TextProcessingTool 
{
	/**
	 * Processes the text documents of a corpus
	 * @param textCorpus The corpus to process
	 */
	public void process(TextCorpus textCorpus)
	{
		for(TextDocument doc : textCorpus)
			process(doc);		
	}
	
	/**
	 * Process a text document
	 * @param doc The document to process
	 */
	public void process(TextDocument doc)
	{
		doc.set_content(process(doc.get_content()));
	}
	
	/**
	 * Process the input string and return the resulting string
	 * @param input The input string
	 * @return The processed string
	 */
	public abstract String process(String input);
}
