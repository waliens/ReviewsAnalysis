package rm.crawler.amazon.exceptions;

public class StarsNotFoundException extends Exception 
{
	private static final long serialVersionUID = -1755101923775258283L;
	
	public StarsNotFoundException() { super(); }
	public StarsNotFoundException(String s) { super(s); }
}
