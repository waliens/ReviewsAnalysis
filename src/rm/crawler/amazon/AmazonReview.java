package rm.crawler.amazon;

import rm.util.TSVRowSerializable;

/**
 * A class representing an amazon review
 * @author Romain Mormont
 * TSV format : 'stars'\t'text'\t'title'\t'polarity'
 */
public class AmazonReview implements TSVRowSerializable
{
	private int stars;
	private String text;
	private String title;
	private float polarity;
	
	/**
	 * Build an amazon review object
	 * @param stars The number of stars
	 * @param text The review text
	 * @param title The review title
	 * @throws IllegalArgumentException Throw if the number of stars is invalid
	 */
	public AmazonReview(int stars, String text, String title) throws IllegalArgumentException
	{
		if(!valid_stars(stars))
			throw new IllegalArgumentException("Invalid number of stars : " + stars);
			
		this.stars = stars;
		this.text = text;
		this.title = title;
		this.polarity = 0.0f;
	}
	
	/**
	 * Build an amazon review object
	 * @param stars The number of stars
	 * @param text The review text
	 * @param title The review title
	 * @param polarity The review polarity
	 * @throws IllegalArgumentException Throw if the number of stars is invalid
	 */
	public AmazonReview(int stars, String text, String title, float polarity) throws IllegalArgumentException
	{
		this(stars, text, title);
		
		if(polarity < -1 || polarity > 1)
			throw new IllegalArgumentException("Polarity must be in [-1,1]");
			
		this.polarity = polarity;
	}
	
	/**
	 * Checks whether the number of stars is valid (in ]0, 5]
	 * @param stars The number of stars
	 * @return True if the number of stars is valid, false otherwise
	 */
	public boolean valid_stars(int stars)
	{
		return stars > 0 && stars <= 5;
	}
	
	/**
	 * Return the review text
	 * @return The review text
	 */
	public String get_text()
	{
		return text;
	}
	
	/**
	 * Return the review title
	 * @return The review title
	 */
	public String get_title()
	{
		return title;
	}
	
	/**
	 * Return the review polarity
	 * @return The review polarity
	 */
	public float get_polarity()
	{
		return polarity;
	}
	
	/**
	 * Return the number of stars
	 * @return The number of stars
	 */
	public int get_stars()
	{
		return stars;
	}
	
	/**
	 * Set the review text
	 * @param text The new review text
	 */
	public void set_text(String text)
	{
		this.text = text;
	}
	
	/**
	 * Set the review title
	 * @param title The new review title
	 */
	public void set_title(String title)
	{
		this.title = title;
	}
	
	/**
	 * Set the review polarity
	 * @param polarity The new review polarity
	 */
	public void set_polarity(float polarity)
	{
		this.polarity = polarity;
	}

	@Override
	public String format_tsv() 
	{
		return stars + "\t" + text + "\t" + title + "\t" + polarity;
	}
}
