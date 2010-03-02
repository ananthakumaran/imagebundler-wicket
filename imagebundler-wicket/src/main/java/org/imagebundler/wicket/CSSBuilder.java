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

	RichString css = new RichString();
	List<String> rules = new ArrayList<String>();

	/**
	 * 
	 * @param imageURL
	 */
	public CSSBuilder(Map<String, Map<ImageURL, ImageRect>> cssMap)
	{
		this.cssMap = cssMap;
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
						".%s_%s { background-image :url(resources/%s/%s} ; background-position:-%dpx -%dpx; width:%dpx; height:%dpx; } ",
						imageURL.getClassName(), insertLocale(imageURL.getMethodName(), locale),
						imageURL.getPackageName(), insertLocale(imageURL.getImageName(), locale),
						imageRect.getLeft(), imageRect.getTop(), imageRect.getWidth(), imageRect
								.getHeight());

		if (locale.equals("default"))
		{
			rules.add(0, rule);
		}
		else
		{
			rules.add(rule);
		}
	}

	public void writeCSS()
	{
		CSSRules.get().rules.addAll(rules);
	}
}
