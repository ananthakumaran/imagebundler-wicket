package org.wicketstuff.imagebundler.example;

import org.apache.wicket.markup.html.image.Image;
import org.wicketstuff.imagebundler.ImageBundle;
import org.wicketstuff.imagebundler.Resource;


@ImageBundle
public interface SampleImage
{
	
	@Resource("buttons.png")
	public Image butto(String id);

	public Image course(String id);
}
