package rm.crawler.amazon;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import rm.textprocessing.analyzer.polarizer.PolarityAnalyzer;
import rm.textprocessing.analyzer.polarizer.Polarity;

/**
 * A crawler for running through the set of amazon review pages of a given product
 * @author Romain Mormont
 */
public class AmazonCrawler 
{
	private String start_page_url;
	private AmazonCorpus corpus;

	private AmazonProcessor processor;

	/**
	 * Build an amazon crawler starting on the given product's review page referenced by the given url.
	 * @param start_url
	 * @param product_name
	 */
	public AmazonCrawler(String start_url)
	{
		this.start_page_url = start_url;
		this.corpus = new AmazonCorpus();
		this.processor = new AmazonProcessor(AmazonProcessingStrategy.PREPROCESS, corpus);
	}
	
	/**
	 * Launch the crawler from the start url
	 * During the crawling, an amazon review corpus is built
	 */
	public void launch()
	{
		String url = start_page_url;
		
		try
		{
			int page_counter = 1, attempts = 0;
			while(!url.isEmpty())
			{
				try
				{
					System.out.println("Get page " + page_counter + " : " + url);
					Document doc = get_document(url);
					System.out.println("Process...");
					processor.process(doc);
					url = get_next_page_url(doc);
				}
				catch(IOException e)
				{
					// on time out retry at least 3 times
					if(!e.getMessage().equals("Read timed out") || attempts > 3)
						throw e;
					attempts++;
					continue;
				}
				
				attempts = 0;
				page_counter++;
			}
		}
		catch(Exception e)
		{
			System.err.println("Error while crawling the review pages : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private Document get_document(String url) throws IOException
	{
		return Jsoup.connect(url) 
				.userAgent("Mozilla/5.0")
				.header("from", "waliens@hotmail.com")
				.timeout(5000)
				.referrer("http://www.google.com").get();
	}
	
	/**
	 * Return the url of the next review page
	 * @param doc The html document containing the page 
	 * @return The string url of the next page, an empty string if there is none
	 */
	private String get_next_page_url(Document doc)
	{
		Elements items = doc.select(".a-last a");
		
		if(items.size() == 0) 
			return "";
		
		String url = items.first().attr("href");
		
		if(!url.startsWith("http"))
			url = "http://www.amazon.com" + url;
		
		return url;
	}
	
	/**
	 * Return the corpus associated with the 
	 * @return
	 */
	public AmazonCorpus get_corpus()
	{
		return corpus;
	}
	
	/**
	 * Main function lauching the amazon review crawler
	 * Parameters :
	 *  1) the name of the product
	 *  2) the url of the product reviews' first page
	 *  3) the file in which the corpus must be saved
	 */
	public static void main(String[] args)
	{
		if(args.length != 3)
		{
			System.err.println("USAGE : ./program <name> <url> <corpus_file>");
			return;
		}
		
		String name = args[0], url = args[1];
		
		AmazonCrawler crawler = new AmazonCrawler(url);
		crawler.launch();
		
		// get and save corpus
		AmazonCorpus corpus = crawler.get_corpus();

		try { corpus.save_tsv(args[2]); }
		catch (IOException e) 
		{
			System.err.println("Cannot save file : " + e.getMessage());
		}
		
		// print summary
		float polarity = 0;
		float stars = 0;
		int pos_count = 0, neu_count = 0, neg_count = 0;
		
		for(AmazonReview review : corpus)
		{	
			polarity += review.get_polarity();
			stars += review.get_stars();
			
			switch(PolarityAnalyzer.get_polarity_class(review.get_polarity()))
			{
			case NEUTRAL: neu_count++; break;
			case POSITIVE: pos_count++; break;
			case NEGATIVE: neg_count++; break;
			}
		}
		
		System.out.println("Product '" + name + "' :");
		System.out.println("\t- polarity : " + (polarity / corpus.size()));
		System.out.println("\t- stars    : " + (stars / corpus.size()));
		System.out.println("\t- Positive : " + pos_count + " (" + (100 * pos_count / (float) corpus.size()) + " %)");
		System.out.println("\t- Neutral  : " + neu_count + " (" + (100 * neu_count / (float) corpus.size()) + " %)");
		System.out.println("\t- Negative : " + neg_count + " (" + (100 * neg_count / (float) corpus.size()) + " %)");
		
		return;
	}
}
