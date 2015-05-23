package rm.crawler.amazon;

/**
 * The different processing startegy  * - FETCH    : fetch the reviews and add them to the corpus object associated with the processor class
 * - POLARITY : fetch the reviews, set their polarity and add them to the corpus object associated with the processor class
 * - PROCESS  : fetch and preprocess the reviews (+ find the polarity) and add the reviews in the corpus object associated with the processor class
 * @author Romain Mormont
 */
public enum AmazonProcessingStrategy 
{
	FETCH, POLARITY, PREPROCESS
}
