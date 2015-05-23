package rm.textprocessing.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A tool for replacing every part of a String matching the given pattern by some other string
 */
public class PatternReplacer extends TextProcessingTool {

	private Pattern pattern;
	private String replace;
	
	/**
	 * Construct a pattern replacer object
	 * @param pattern The pattern to match
	 * @param replace The replacing string
	 */
	public PatternReplacer(String pattern, String replace)
	{
		this.pattern = Pattern.compile(pattern);
		this.replace = replace;
	}
	
	/**
	 * Set a new pattern 
	 * @param new_pattern The new pattern
	 */
	public void set_pattern(String new_pattern)
	{
		this.pattern = Pattern.compile(new_pattern);
	}
	
	/**
	 * Set the replace string
	 * @param replace The replacing string
	 */
	public void set_replace(String replace)
	{
		this.replace = replace;
	}
	
	@Override
	public String process(String input) 
	{
		Matcher matcher = pattern.matcher(input);
		return matcher.replaceAll(replace);
	}
}
