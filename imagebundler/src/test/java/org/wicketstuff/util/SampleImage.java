package org.wicketstuff.util;

import org.apache.wicket.markup.html.image.Image;
import org.wicketstuff.imagebundler.ImageBundle;


@ImageBundle
public interface SampleImage
{
	public Image buttons(String a, String fileName);
}
