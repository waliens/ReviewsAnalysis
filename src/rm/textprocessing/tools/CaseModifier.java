package rm.textprocessing.tools;

import rm.textprocessing.util.Case;

/**
 * A processing tool for modifying the case of a string
 * @author Romain Mormont
 */
public class CaseModifier extends TextProcessingTool 
{
	private Case to; /** Case to which the tool must be convert strings */
	
	public CaseModifier(Case to)
	{
		this.to = to;
	}

	@Override
	public String process(String input) 
	{
		switch(to)
		{
		case LOWERCASE:	return input.toLowerCase();
		case UPPERCASE: return input.toUpperCase();
		default: return "";
		}
	}
}
