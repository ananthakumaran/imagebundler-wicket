package org.imagebundler.wicket;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;

public class ImageItemModifier extends AbstractBehavior
{

	private static final long serialVersionUID = 1L;

	private final ImageItem imageItem;
	private final AttributeModifier styleModifier;

	public ImageItemModifier(ImageItem imageItem)
	{
		super();
		if (imageItem == null)
		{
			throw new IllegalArgumentException("Argument [imageItem] cannot be null");
		}
		this.imageItem = imageItem;
		this.styleModifier = new AttributeModifier("style", true, new ImageStyleModel(imageItem));
	}

	public final ImageItem getImageItem()
	{
		return imageItem;
	}

	@Override
	public void onComponentTag(final Component component, final ComponentTag tag)
	{
		if (isEnabled(component))
		{
			tag.getAttributes().put("src", imageItem.getSrc());
			styleModifier.onComponentTag(component, tag);
		}
	}

	@Override
	public void detach(Component component)
	{
		styleModifier.detach(component);
	}

}