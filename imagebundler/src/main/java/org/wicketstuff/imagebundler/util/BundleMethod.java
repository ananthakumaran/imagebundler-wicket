package org.wicketstuff.imagebundler.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.lang.model.element.Element;

import org.wicketstuff.imagebundler.ImageBundleBuilder.ImageRect;

/**
 * used to create a bundle method
 * 
 * @author Ananth
 */
public class BundleMethod
{

	private final Logger logger = Logger.getLogger(getClass().getName());
	// simple method name
	private String methodName;
	private String imageURL;
	private BundleClass clazz;

	/**
	 * constructor
	 */
	public BundleMethod(Element methodElement, BundleClass clazz)
	{
		this.methodName = methodElement.getSimpleName().toString();
		this.clazz = clazz;

		// TODO get the filename using annotation
		this.imageURL = clazz.getPackageName().replace('.', '/') + "/" + methodName + ".png";

		addToBundle();
	}

	/**
	 * appends all the code the to rich string
	 * 
	 * @param tabCount
	 *            tabCount
	 * @return String representation of the method
	 */
	public RichString toCode(int tabCount)
	{
		RichString str = new RichString(tabCount);
		str.line();
		// override annotation
		str.append("@Override").line();
		// method signature
		str.append("public Image ").append(methodName).append("(String id,String fileName)").open();

		//
		str.append("//").append(getImageRect().getLeft() + "" + getImageRect().getName()).line();
		str.append("//").append("" + getImageRect().getTop() + getImageRect().getLeft()).line();
		// TODO: orginal code should come here
		// for now add return null;
		str.append("return null").semicolon().close();
		return str;
	}


	/**
	 * getter for imageUrl
	 * 
	 * @return imageUrl
	 */
	public String getImageURL()
	{
		return imageURL;
	}

	/**
	 * adds the image to the imageBundle
	 */
	public void addToBundle()
	{
		try
		{
			clazz.getImageBundleBuilder().assimilate(getImageURL());
		}
		catch (Exception e)
		{
			logger.log(Level.SEVERE, "Could not add " + getImageURL() + " to the imageBundle", e);
		}
	}

	/**
	 * get the imageRect which contains the details of the image NOTE don't call
	 * this method before creation of the imagebundle
	 * 
	 * @return imageRect
	 */
	public ImageRect getImageRect()
	{
		return clazz.getImageBundleBuilder().getMapping(getImageURL());
	}

}
