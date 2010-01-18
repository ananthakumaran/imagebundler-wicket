package org.wicketstuff.sprite.example;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.image.Image;

public abstract class SampleImageBundleTest implements SampleImage
{

	public Image boldImage(String id, String fileName)
	{
		Image image = new Image(id, "spacer.gif");
		
		// this should contain the new created image name
		fileName = String.format("resources/%s/%s", this.getClass().getName(),fileName);
		// image button
		String style = String.format("background-image: url(%s); background-position:-%dpx -%dpx; width:%dpx; height:%dpx;", fileName,0,0,20,20);
		image.add(new SimpleAttributeModifier("style",style));
		return image;
	}

	public Image codeImage(String id, String fileName)
	{
		Image image = new Image(id, "spacer.gif");
		
		// this should contain the new created image name
		fileName = String.format("resources/%s/%s", this.getClass().getName(),fileName);
		// image button
		String style = String.format("background-image: url(%s); background-position:-%dpx -%dpx; width:%dpx; height:%dpx;", fileName,80,120,20,20);
		image.add(new SimpleAttributeModifier("style",style));
		return image;
	}

}
