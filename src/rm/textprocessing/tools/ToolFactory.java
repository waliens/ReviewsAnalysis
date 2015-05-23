package rm.textprocessing.tools;

import rm.textprocessing.util.Case;
import rm.textprocessing.util.Language;

/**
 * A tool for building processing tools 
 * @author Romain Mormont
 */
public class ToolFactory 
{
	public static Language lang; 
	public static Case case_;
	
	static 
	{ 
		lang = Language.ENGLISH;
		case_ = Case.LOWERCASE;
	}
	
	/**
	 * Build a pipeline from an array of Tool enumeration literal
	 * @param toolchain The array of Tool enumeration literal listing the tools that must added to the pipeline (in the same order)
	 * @return The constructed pipeline
	 */
	public static Pipeline mk_pipeline(Tool[] toolchain)
	{
		try
		{
			if(toolchain.length == 0)
				return null;
			
			Pipeline pipeline = new Pipeline();
			
			for(int i = 0; i < toolchain.length; ++i)
				pipeline.add_tool(tool(toolchain[i]));	
			
			return pipeline;
		} catch(Exception e) { return null; }
	}
	
	/**
	 * Return the text processing tool object corresponding to the given tool enumeration literal
	 * @param tool The tool enumeration literal
	 * @return The corresponding text processing tool object
	 */
	public static TextProcessingTool tool(Tool tool)
	{
		try
		{
			switch(tool)
			{
			case REM_NUMBER: 
				return new NumberRemover();
			case REM_PUNCTUATION:
				return new PunctuationRemover();
			case REM_STOPS_WORD:
				return new StopWordsRemover(lang);
			case REM_WHITESPACE:
				return new WhitespaceRemover();
			case MOD_CASE:
				return new CaseModifier(case_);
			case TRIM:
				return new Trimmer();
			default:
				return null;
			}
		} catch(Exception e) { return null; }
	}
}
