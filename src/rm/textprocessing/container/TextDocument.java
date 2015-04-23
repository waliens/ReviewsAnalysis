package rm.textprocessing.container;

import rm.util.TSVRowSerializable;

/**
 * Class for representing a text document
 * @author Romain Mormont
 */
public class TextDocument implements TSVRowSerializable
{
	String doc_content;
	
	/**
	 * The text document
	 * @param content The content of the string
	 */
	public TextDocument(String content)
	{
		doc_content = content;
	}
	
	public String get_content()
	{
		return doc_content;
	}
	
	public void set_content(String new_content)
	{
		doc_content = new_content;
	}

	@Override
	public String format_tsv() 
	{
		return doc_content;
	}
}
