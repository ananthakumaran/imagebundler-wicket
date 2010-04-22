package org.imagebundler.wicket;

import java.util.Locale;

import org.apache.wicket.IClusterable;

/**
 * provides a way to access the style and source of a image
 * 
 * @author Ananth
 * 
 */
public interface ImageItem extends IClusterable
{
	/**
	 * gets the src of the image Note: this actually contains the src of the
	 * transparent gif. Default value <code>images/clear.gif</code>
	 * 
	 * @return src of the image (transparent image)
	 */
	String getSrc();

	/**
	 * gets the style of the image
	 * <p>
	 * eg
	 * <p>
	 * <code>
	 * 	background-image:url(resources/org.imagebundler.wicket.examples.EditorButtonBundle/EditorButtonBundle.png);
	 *  background-position:-148px -0px; width:16px; height:16px;  
	 * 	</code>
	 * 
	 * @return style of the image
	 */
	String getStyle(Locale locale);
}
