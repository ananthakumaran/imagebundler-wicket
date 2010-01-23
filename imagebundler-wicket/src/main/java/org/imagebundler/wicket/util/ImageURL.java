package org.imagebundler.wicket.util;

/**
 * utility class to represent imageURL
 * 
 * @author Ananth
 * 
 */
public class ImageURL
{
	/** package name */
	private String packageName;
	/** method name */
	private String methodName;
	/** <code>@Resource</code> value */
	private String resource;
	/** image name */
	private String imageName;

	/**
	 * 
	 * @param packageName
	 *            package name
	 * @param methodName
	 *            image name with extension
	 */
	public ImageURL(String packageName, String imageName)
	{
		this.packageName = packageName;
		this.methodName = imageName;
	}

	public String getPackageName()
	{
		return packageName;
	}

	public String getMethodName()
	{
		return methodName;
	}

	public String getResource()
	{
		return resource;
	}

	public void setResource(String resource)
	{
		this.resource = resource;
	}

	public String getImageName()
	{
		return imageName;
	}

	public void setImageName(String imageName)
	{
		this.imageName = imageName;
	}

}
