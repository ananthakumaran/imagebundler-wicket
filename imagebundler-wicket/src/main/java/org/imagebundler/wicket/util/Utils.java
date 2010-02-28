package org.imagebundler.wicket.util;

/**
 * Utils
 * 
 * @author Ananth
 * 
 */
public class Utils
{
	/**
	 * inserts the locale in the given value
	 * 
	 * @param value
	 * @param locale
	 * @return
	 */
	public static String insertLocale(String value, String locale)
	{
		int lastIndex = value.lastIndexOf('.');
		if (lastIndex == -1)
		{
			return value + "_" + locale;
		}
		else
		{
			return value.substring(0, lastIndex) + "_" + locale + value.substring(lastIndex);
		}
	}
}
