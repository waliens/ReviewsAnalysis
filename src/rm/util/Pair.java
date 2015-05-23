package rm.util;

/**
 * Class holding a pair of elements that can have different types
 */
public class Pair<L, R> 
{
	public L left;
	public R right;
	
	public Pair(L left, R right)
	{
		this.left = left;
		this.right = right;
	}
	
	public L first()
	{
		return left;
	}
	
	public R second()
	{
		return right;
	}
}
