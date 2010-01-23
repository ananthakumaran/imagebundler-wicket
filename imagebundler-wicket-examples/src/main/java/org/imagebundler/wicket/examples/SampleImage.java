package org.imagebundler.wicket.examples;

import org.apache.wicket.markup.html.image.Image;
import org.imagebundler.wicket.ImageBundle;
import org.imagebundler.wicket.Resource;

@ImageBundle
public interface SampleImage
{

	@Resource("buttons.png")
	public Image buttons(String id);

	public Image course(String id);

}
