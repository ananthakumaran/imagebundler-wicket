package org.wicketstuff.imagebundler.example;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.image.Image;

public  class SampleImageBundleTest // implements SampleImage
{
	public Image buttons(String id, String fileName)
	{
		Image image = new Image(id, "spacer.gif");		
		image.add(new SimpleAttributeModifier("style", "background-image: url(resources/org.wicketstuff.imagebundler.example.SampleImageBundle/SampleImageBundle.png); background-position:-0px -125px; width:260px; height:60px;"));
		return image;
	}

	public Image course(String id, String fileName)
	{
		Image image = new Image(id, "spacer.gif");
		image.add(new SimpleAttributeModifier("style", "background-image: url(resources/org.wicketstuff.imagebundler.example.SampleImageBundle/SampleImageBundle.png); background-position:-0px -0px; width:125px; height:125px;"));
		return image;
	}

}
