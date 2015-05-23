package rm.crawler.amazon.exceptions;

/**
 * Thrown if some review is not found in the HTML document fetched from Amazone
 */
public class ReviewTextNotFoundException extends Exception 
{
	private static final long serialVersionUID = -1755101923775258283L;
	
	public ReviewTextNotFoundException() { super(); }
	public ReviewTextNotFoundException(String s) { super(s); }
}