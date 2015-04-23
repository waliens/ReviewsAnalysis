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
	 * Return the text processing tool corresponding to the given tool
	 * @param tool The tool
	 * @return The corresponding text processing tool
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
