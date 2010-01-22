package org.wicketstuff.imagebundler.util;

/**
 * utility class to represent imageURL
 * 
 * @author Ananth
 * 
 */
public class ImageURL
{
	public String packageName;
	public String imageName;

	/**
	 * 
	 * @param packageName
	 *            package name
	 * @param imageName
	 *            image name with extension
	 */
	public ImageURL(String packageName, String imageName)
	{
		this.packageName = packageName;
		this.imageName = imageName;
	}

}
