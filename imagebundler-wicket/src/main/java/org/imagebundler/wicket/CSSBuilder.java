package org.imagebundler.wicket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.imagebundler.wicket.ImageBundleBuilder.ImageRect;
import org.imagebundler.wicket.processor.CurrentEnv;
import org.imagebundler.wicket.util.FileLogger;
import org.imagebundler.wicket.util.ImageURL;
import org.imagebundler.wicket.util.RichString;
import static org.imagebundler.wicket.util.Utils.*;

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
						".%s_%s { background-image :url(resources/%s/%s} ; background-position:-%dpx -%dpx; width:%dpx; height:%dpx; } \n",
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
		// Remove previous css associated with this class
		for (String rule : rules)
		{
			logger.log(Level.INFO, rule);
		}

	}
}
