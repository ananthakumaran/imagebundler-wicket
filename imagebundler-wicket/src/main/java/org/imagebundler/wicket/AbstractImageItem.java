package org.imagebundler.wicket;

/**
 * Partial implementation of ImageItem
 * 
 * @author Ananth
 * 
 */
public abstract class AbstractImageItem implements ImageItem
{
	private final String imageSrc;


	/**
	 * 
	 * @param imageSrc
	 */
	public AbstractImageItem(String imageSrc)
	{
		this.imageSrc = imageSrc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.imagebundler.wicket.ImageItem#getSrc()
	 */
	public String getSrc()
	{
		return imageSrc;
	}
}
