package rm.crawler.amazon.exceptions;

public class ReviewTextNotFoundException extends Exception 
{
	private static final long serialVersionUID = -1755101923775258283L;
	
	public ReviewTextNotFoundException() { super(); }
	public ReviewTextNotFoundException(String s) { super(s); }
}