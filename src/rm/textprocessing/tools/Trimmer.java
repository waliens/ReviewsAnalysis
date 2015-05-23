package rm.textprocessing.tools;

/**
 * A tool for trimming a string (i.e. removing the starting and ending space characters)
 * @author Romain Mormont
 */
public class Trimmer extends TextProcessingTool 
{
	@Override
	public String process(String input) 
	{
		String out = input.replaceAll("^\\s+", "");
		return out.replaceAll("\\s+$", "");
	}

}
