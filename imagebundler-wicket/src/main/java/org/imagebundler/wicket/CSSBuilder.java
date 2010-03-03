package org.imagebundler.wicket;

import static org.imagebundler.wicket.util.Utils.insertLocale;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.imagebundler.wicket.ImageBundleBuilder.ImageRect;
import org.imagebundler.wicket.util.ImageURL;

/**
 * 
 * builds the css rules
 * 
 * @author Ananth
 * 
 */
public class CSSBuilder
{
	/** list of imageurl for each locale */
	private Map<String, Map<ImageURL, ImageRect>> cssMap;
	/** list of rules */
	public List<String> rules = new ArrayList<String>();
	/** class name */
	private String className;

	/**
	 * 
	 * @param imageURL
	 */
	public CSSBuilder(Map<String, Map<ImageURL, ImageRect>> cssMap, String className)
	{
		this.cssMap = cssMap;
		this.className = className;
	}

	/**
	 * builds the css image
	 */
	public void build()
	{
		for (String locale : cssMap.keySet())
		{
			for (Entry<ImageURL, ImageRect> entry : cssMap.get(locale).entrySet())
			{
				buildCSS(locale, entry.getKey(), entry.getValue());
			}
		}
		writeCSS();
	}

	/**
	 * creates a css rule with the details and push it into the list of css
	 * rules
	 * 
	 * @param locale
	 * @param imageURL
	 * @param imageRect
	 */
	public void buildCSS(String locale, ImageURL imageURL, ImageRect imageRect)
	{
		String rule = String
				.format(
						".%s_%s { background-image :url(resources/%s.%s/%s.png) ; background-position:-%dpx -%dpx; width:%dpx; height:%dpx; } ",
						className, insertLocale(imageURL.getMethodName(), locale), imageURL
								.getPackageName(), className, insertLocale(className, locale),
						imageRect.getLeft(), imageRect.getTop(), imageRect.getWidth(), imageRect
								.getHeight());

		rules.add(rule);
	}

	/**
	 * removes all the previous css rules associated with this class and add the
	 * new css rules
	 */
	public void writeCSS()
	{
		CSSRules cssRules = CSSRules.get();
		cssRules.remove("." + className);
		cssRules.rules.addAll(rules);
	}
}
