package org.wicketstuff.sprite.util;

import java.lang.reflect.Method;

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
	public BundleMethod(Method method)
	{
		this.methodName = method.getName();
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
