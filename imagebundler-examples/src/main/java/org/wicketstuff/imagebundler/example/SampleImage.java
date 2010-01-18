package org.wicketstuff.imagebundler.example;

import org.apache.wicket.markup.html.image.Image;
import org.wicketstuff.imagebundler.ImageBundle;


@ImageBundle
public interface SampleImage
{
	public Image boldImage(String a, String fileName);
	public Image codeImage(String id , String fileName);
}
