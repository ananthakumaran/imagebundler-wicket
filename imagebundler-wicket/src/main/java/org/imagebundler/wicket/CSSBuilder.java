package org.imagebundler.wicket;

import static org.imagebundler.wicket.util.Utils.insertLocale;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.imagebundler.wicket.ImageBundleBuilder.ImageRect;
import org.imagebundler.wicket.processor.CurrentEnv;
import org.imagebundler.wicket.util.FileLogger;
import org.imagebundler.wicket.util.ImageURL;
import org.imagebundler.wicket.util.RichString;

/**
 * 
 * @author Ananth
 * 
 */
public class CSSBuilder
{
	private final FileLogger logger = CurrentEnv.getLogger();
	/** list of image urls */
	private Map<String, Map<ImageURL, ImageRect>> cssMap;

	public List<String> rules = new ArrayList<String>();
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
			for (Map<ImageURL, ImageRect> value : cssMap.values())
			{
				for (Entry<ImageURL, ImageRect> entry : value.entrySet())
				{
					buildCSS(locale, entry.getKey(), entry.getValue());
				}
			}
		}
		writeCSS();
	}

	public void buildCSS(String locale, ImageURL imageURL, ImageRect imageRect)
	{
		imageURL.getClassName();
		insertLocale(imageURL.getMethodName(), locale);
		insertLocale(imageURL.getImageName(), locale);
		imageRect.getLeft();

		String rule = String
				.format(
						".%s_%s { background-image :url(resources/%s.%s/%s.png) ; background-position:-%dpx -%dpx; width:%dpx; height:%dpx; } ",
						className, insertLocale(imageURL.getMethodName(), locale), imageURL
								.getPackageName(), className, insertLocale(className, locale),
						imageRect.getLeft(), imageRect.getTop(), imageRect.getWidth(), imageRect
								.getHeight());

		rules.add(rule);
	}

	public void writeCSS()
	{
		CSSRules cssRules = CSSRules.get();
		cssRules.remove("." + className);
		cssRules.rules.addAll(rules);
	}
}
