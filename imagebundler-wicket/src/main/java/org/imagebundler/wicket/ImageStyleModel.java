package org.imagebundler.wicket;

import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.model.ComponentModel;

public class ImageStyleModel extends ComponentModel<String>
{

	private static final long serialVersionUID = 1L;

	private final ImageItem imageItem;

	public ImageStyleModel(ImageItem imageItem)
	{
		super();
		if (imageItem == null)
		{
			throw new IllegalArgumentException("Argument [imageItem] cannot be null");
		}
		this.imageItem = imageItem;
	}

	public final ImageItem getImageItem()
	{
		return imageItem;
	}

	@Override
	protected final String getObject(Component component)
	{
		Locale locale = component.getLocale();
		return imageItem.getStyle(locale);
	}

}
