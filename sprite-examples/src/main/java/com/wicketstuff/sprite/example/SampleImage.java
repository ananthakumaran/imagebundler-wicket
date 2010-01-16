package com.wicketstuff.sprite.example;

import org.apache.wicket.markup.html.image.Image;
import org.wicketstuff.sprite.ImageBundle;


@ImageBundle
public interface SampleImage
{

	public Image boldImage(String id , String fileName);
	public Image codeImage(String id , String fileName);
	
}
