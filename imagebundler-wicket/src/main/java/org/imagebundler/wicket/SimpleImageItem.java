package org.imagebundler.wicket;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class SimpleImageItem implements ImageItem
{

	private static final long serialVersionUID = 1L;

	private final String imageSrc;
	private final String defaultImageStyle;
	private Map<String, String> localizedImageStyles;

	public SimpleImageItem(String imageSrc, String defaultImageStyle)
	{
		this.imageSrc = imageSrc;
		this.defaultImageStyle = defaultImageStyle;
	}

	public void addLocalizedStyle(String localeStr, String imageStyle)
	{
		if (localizedImageStyles == null)
		{
			localizedImageStyles = new HashMap<String, String>();
		}
		localizedImageStyles.put(localeStr, imageStyle);
	}

	@Override
	public String getSrc()
	{
		return imageSrc;
	}

	@Override
	public String getStyle(Locale locale)
	{
		if ((locale == null) || (localizedImageStyles == null))
		{
			return defaultImageStyle;
		}
		String imageStyle = localizedImageStyles.get(locale.toString());
		// fallback to language style, eg.: when locale is "en_GB" but you
		// registered images only for language "en", without country specified
		if (imageStyle == null)
		{
			imageStyle = localizedImageStyles.get(locale.getLanguage());
			return imageStyle != null ? imageStyle : defaultImageStyle;
		}
		return imageStyle;
	}

}