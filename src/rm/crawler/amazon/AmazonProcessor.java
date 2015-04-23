package rm.crawler.amazon;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import rm.crawler.amazon.exceptions.*;
import rm.textprocessing.analyzer.polarizer.LiubAnalyzer;
import rm.textprocessing.analyzer.polarizer.PolarityAnalyzer;
import rm.textprocessing.tools.Pipeline;
import rm.textprocessing.tools.Tool;
import rm.textprocessing.tools.ToolFactory;

/**
 * Processor class for handling amazon reviews pages and amazon reviews
 * @author Romain Mormont
 */
public class AmazonProcessor
{
	private Pipeline pipeline;
	private PolarityAnalyzer analyzer;
	private AmazonProcessingStrategy strategy;
	private AmazonCorpus corpus;
	
	public AmazonProcessor(AmazonProcessingStrategy strategy, AmazonCorpus corpus)
	{
		Tool[] toolchain = { Tool.REM_PUNCTUATION, Tool.REM_NUMBER,  
							 Tool.MOD_CASE, Tool.REM_STOPS_WORD, 
							 Tool.REM_WHITESPACE, Tool.TRIM };
		
		this.pipeline = ToolFactory.mk_pipeline(toolchain);
		this.analyzer = new LiubAnalyzer();
		this.strategy = strategy;
		this.corpus   = corpus;
	}

	/**
	 * Process the document according the strategy
	 * @param doc The jsoup document containing the reviews page
	 * @throws ReviewTitleNotFoundException 
	 * @throws ReviewTextNotFoundException 
	 * @throws StarsNotFoundException 
	 */
	public void process(Document doc) throws StarsNotFoundException, ReviewTextNotFoundException, ReviewTitleNotFoundException 
	{
		// select the review content section
		Elements reviews = doc.select(".review"); 

		for(Element html_review : reviews)
		{
			AmazonReview review = new AmazonReview(get_stars(html_review), 
													get_text(html_review), 
													get_title(html_review));

			if(compute_polarity())
			{
				String p_text = pipeline.process(review.get_text()),
						p_title = pipeline.process(review.get_title());
				
				review.set_polarity(analyzer.get_text_polarity(p_title + " " + p_text));
				
				if(preprocess())
				{
					review.set_text(p_text);
					review.set_title(p_title);
				}
			}
			
			corpus.add(review);
		}
	}
	
	/**
	 * Return the number of stars associated with the given review html object
	 * @param review The html element containing the review
	 * @return The number of stars
	 * @throws StarsNotFoundException If the number of stars wasn't found
	 */
	private int get_stars(Element review) throws StarsNotFoundException
	{
		Elements star = review.select(".review-rating span");
		
		if(star.isEmpty())
			throw new StarsNotFoundException("No argument exception.");
		
		return Integer.parseInt(star.first().text());
	}
	
	/**
	 * Return the review text associated with the given review html object
	 * @param review The html element containing the review
	 * @return The review text
	 * @throws ReviewTextNotFoundException If the review text wasn't found
	 */
	private String get_text(Element review) throws ReviewTextNotFoundException
	{
		Elements text = review.select(".review-text");
		
		if(text.isEmpty())
			throw new ReviewTextNotFoundException("No argument exception.");
		
		return text.first().text();
	}
	
	/**
	 * Return the review title associated with the given review html object
	 * @param review The html element containing the review
	 * @return The review title
	 * @throws ReviewTitleNotFoundException If the review text wasn't found
	 */
	private String get_title(Element review) throws ReviewTitleNotFoundException
	{
		Elements title = review.select(".review-title");
		
		if(title.isEmpty())
			throw new ReviewTitleNotFoundException("No argument exception.");
		
		return title.first().text();
	}
	
	/**
	 * Checks whether the polarity must be computed
	 * @return True if the polarity must be computed, false otherwise
	 */
	private boolean compute_polarity()
	{
		return strategy == AmazonProcessingStrategy.PREPROCESS || 
					strategy == AmazonProcessingStrategy.POLARITY;
	}
	
	/**
	 * Checks whether the text fields must be preprocessed
	 * @return True if the text fields must be proceprocessed, false otherwise
	 */
	private boolean preprocess()
	{
		return strategy == AmazonProcessingStrategy.PREPROCESS;
	}
}
