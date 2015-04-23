package rm.textprocessing.analyzer.polarizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import rm.util.Pair;

/**
 * Polarity analyzer using the SentiWordNet lexicon 
 * @author Romain Mormont
 * The polarity type is Pair<Float,Float> : first element is the positive polarity degree, second element 
 * is the negative polarity degree
 */
public class SentiWordNetAnalyzer extends PolarityAnalyzer<Pair<Float, Float>>
{	
	private static final long serialVersionUID = 6441423643533903563L;
	
	public final String lexpath = "lexicon/polarity/SentiWordNet_3.0.0.txt";
	
	@Override
	protected void load_sentiment_lexicon() 
	{
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try
		{
			fileReader = new FileReader(lexpath);
			bufferedReader = new BufferedReader(fileReader);
	        
	        String line = null;
	        
	        while ((line = bufferedReader.readLine()) != null) 
	        {
	        	if(line.startsWith("#")) // skip comments
	        		continue;

	        	String[] splitted = line.split("\t"); // split the components
	        
	        	if(splitted.length != 6) // 6 elements should lie in the array
	        		continue;
	        	
	        	// get the polarity
	        	float neg_pol = Float.parseFloat(splitted[3]),
	        		  pos_pol = Float.parseFloat(splitted[2]);
	        	
	        	// get the words (only keep the first sense)
	        	String[] words = splitted[4].split(" ");
	        	
	        	for(String sensed_word : words)
	        	{
	        		String[] word_info = sensed_word.split("#");
	        		
		        	if(Integer.parseInt(word_info[1]) > 1) // skip second (or greater) sense of a word
		        		continue;
		        	
		        	// last precaution, check whether the word wasn't inserted before
		        	if(this.containsKey(word_info[0]))
		        		continue;
		        	
		        	put(word_info[0], new Pair<Float,Float>(pos_pol, neg_pol));
	        	}
	        }
	    }
	    catch(IOException e)
	    {
	    	System.err.println("IOError while loading the senti word net lexicon : " + e.getMessage());
	    } 
		catch (Exception e) 
		{
			System.err.println("Duplicate insertion in the polarity map for the sentiword lexicon : " + e.getMessage());
		}
	    finally
	    {
	    	try
	    	{
		        if(bufferedReader != null) bufferedReader.close();
		        if(fileReader != null) 	   fileReader.close();
	    	} catch(Exception e) { }
	    }
	}

	@Override
	protected float compute_polarity(Pair<Float, Float> polarity_info) 
	{
		return Math.abs(polarity_info.right) > polarity_info.left ? -polarity_info.right : polarity_info.left;
	}	
	

}
