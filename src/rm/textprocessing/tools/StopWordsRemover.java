package rm.textprocessing.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rm.textprocessing.util.Language;

public class StopWordsRemover extends PatternRemover 
{
	private String[] stop_words;
	
	/**
	 * Construct a StopWordsRemover from a file
	 * @param file The file from which the 
	 * @throws IOException If an IO erro occurs while reading the file
	 */
	public StopWordsRemover(String file) throws IOException
	{
		super("");
		set_sw_from_file(file);
		set_pattern(get_sw_pattern());
	}
	
	/**
	 * Construct a StopWordsRemover from a list of words
	 * @param stop_words The stop words list
	 */
	public StopWordsRemover(String[] stop_words)
	{
		super("");
		this.stop_words = stop_words;	
		set_pattern(get_sw_pattern());
	}
	
	/**
	 * Construct a StopWordsRemover from the default list for the given language
	 * @param lang The language
	 * @throws IOException Thrown if the file couldn't be read
	 */
	public StopWordsRemover(Language lang) throws IOException
	{
		this(StopWordsRemover.get_sw_file_lang(lang));
	}
	
	/**
	 * Return the name of the file containing the stop words list for the given language
	 * @param lang The language
	 * @return The filename 
	 */
	private static String get_sw_file_lang(Language lang)
	{
		switch(lang)
		{
		case FRENCH:
			return "lexicon/stopwords/fr.txt";
		default: // default is English
			return "lexicon/stopwords/en.txt";
		}
	}
	
	/**
	 * Make a regex pattern from the stop words list
	 * @return The regex pattern
	 */
	private String get_sw_pattern()
	{
		if(stop_words.length == 0)
			return "";
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\\b(?:" + stop_words[0]);
		
		for(int i = 1; i < stop_words.length; ++i)
			sb.append("|" + stop_words[i]);
		
		sb.append(")\\b");
		
		return sb.toString();
	}
	
	/**
	 * Set the stop_word array from a file
	 * @param file
	 */
	private void set_sw_from_file(String file) throws IOException
	{
		FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        
        String line = null;
        
        while ((line = bufferedReader.readLine()) != null) 
            lines.add(line);
        
        bufferedReader.close();
        fileReader.close();
        
        stop_words = lines.toArray(new String[lines.size()]);
	}
}
