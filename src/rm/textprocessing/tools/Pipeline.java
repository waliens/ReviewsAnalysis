package rm.textprocessing.tools;

import java.util.Vector;

/**
 * A processing tool that applies to a document or a corpus a set of other processing tools
 * @author Romain Mormont
 */
public class Pipeline extends TextProcessingTool 
{
	Vector<TextProcessingTool> process_chain;
	
	public Pipeline()
	{
		process_chain = new Vector<TextProcessingTool>();
	}
	
	@Override
	public String process(String input) 
	{
		for(TextProcessingTool tpl : process_chain)
			input = tpl.process(input);
		return input;
	}
	
	/**
	 * Add a new tool to the pipeline
	 * @param tool The new tool to add
	 * @throws Exception Thrown if one tries to add the pipeline to itself
	 */
	public void add_tool(TextProcessingTool tool) throws Exception
	{
		if((tool instanceof Pipeline) && ((Pipeline) tool) == this)
			throw new Exception("Cannot add the pipeline as a tool its toolchain");
		
		process_chain.add(tool);
	}
	
	/**
	 * Remove all the tools from the pipeline
	 */
	public void clear()
	{
		process_chain.clear();
	}
}
