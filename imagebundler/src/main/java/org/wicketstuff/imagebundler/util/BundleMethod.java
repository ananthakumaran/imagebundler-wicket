package org.wicketstuff.imagebundler.util;

import javax.lang.model.element.Element;

/**
 * used to create a bundle method
 * 
 * @author Ananth
 */
public class BundleMethod
{

	// simple method name
	public String methodName;

	/**
	 * constructor
	 */
	public BundleMethod(Element methodElement)
	{
		this.methodName = methodElement.getSimpleName().toString();
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

		// TODO: orginal code should come here
		// for now add return null;
		str.append("return null").semicolon().close();
		return str;
	}
}
