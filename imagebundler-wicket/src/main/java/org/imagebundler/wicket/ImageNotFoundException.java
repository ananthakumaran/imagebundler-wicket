package org.imagebundler.wicket;

/**
 * image cann't be loaded with the given information
 * 
 * @author Ananth
 */
public class ImageNotFoundException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 * 
	 * @param message
	 *            message
	 */
	public ImageNotFoundException(String message)
	{
		super(message);
	}

}
