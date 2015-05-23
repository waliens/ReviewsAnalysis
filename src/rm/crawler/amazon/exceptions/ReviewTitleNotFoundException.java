package rm.crawler.amazon.exceptions;

/**
 * Thrown if the review title is not found
 */
public class ReviewTitleNotFoundException extends Exception 
{
	private static final long serialVersionUID = -1755101923775258283L;
	
	public ReviewTitleNotFoundException() { super(); }
	public ReviewTitleNotFoundException(String s) { super(s); }
}
