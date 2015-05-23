package rm.textprocessing.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class for removing parts of a String based on a regex pattern
 * @author Romain Mormont
 */
public class PatternRemover extends TextProcessingTool
{
	private Pattern pattern;
	
	/**
	 * Construct a pattern replacer object
	 * @param pattern The pattern to match
	 */
	public PatternRemover(String pattern)
	{
		this.pattern = Pattern.compile(pattern);
	}
	
	/**
	 * Set a new pattern 
	 * @param new_pattern The new pattern
	 */
	public void set_pattern(String new_pattern)
	{
		this.pattern = Pattern.compile(new_pattern);
	}
	
	@Override
	public String process(String input) 
	{
		Matcher matcher = pattern.matcher(input);
		return matcher.replaceAll("");
	}
}
