package org.wicketstuff.imagebundler.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.lang.model.element.Element;

import org.wicketstuff.imagebundler.Resource;
import org.wicketstuff.imagebundler.ImageBundleBuilder.ImageRect;

/**
 * used to create a bundle method
 * 
 * @author Ananth
 */
public class BundleMethod
{

	private final Logger logger = Logger.getLogger(getClass().getName());
	/** simple method name */
	private final String methodName;
	/** image url */
	private final String imageURL;
	/** The eclosing class of this method */
	private final BundleClass clazz;

	/**
	 * constructor
	 */
	public BundleMethod(Element methodElement, BundleClass clazz)
	{
		this.methodName = methodElement.getSimpleName().toString();
		this.clazz = clazz;
		this.imageURL = buildImageURL(methodElement);
		addToBundle();
	}

	/**
	 * appends all the code the to rich string
	 * 
	 * @param tabCount
	 *            tabCount
	 * @return String representation of the method
	 */
	public RichString toCode(int tabCount)
	{
		RichString str = new RichString(tabCount);
		str.line();
		// override annotation
		str.append("@Override").line();
		// method signature
		str.append("public Image ").append(methodName).append("(String id,String fileName)").open();

		// declare the image
		str.append("Image image = new Image(id, \"spacer.gif\")").semicolon();
		// set the style element
		str.append("image.add(new SimpleAttributeModifier(\"style\", \"").append(
				getStyle(getImageRect())).append("\"))").semicolon();
		str.append("return image").semicolon().close();
		return str;
	}

	/**
	 * creates the image url for the given method.Returns null if it cannot
	 * build the image url
	 * 
	 * @return
	 */
	public String buildImageURL(Element element)
	{
		Resource resource = element.getAnnotation(Resource.class);
		String imageName;
		if (resource == null)
		{
			// This method is not annotated with @Resource.So fall back and
			// check for any image with methodname

			// TODO add support for other extension also
			imageName = methodName + ".png";
		}
		else
		{
			imageName = resource.value();
		}

		return clazz.getPackageName().replace('.', '/') + "/" + imageName;
	}

	/**
	 * getter for imageUrl
	 * 
	 * @return imageUrl
	 */
	public String getImageURL()
	{
		return imageURL;
	}

	/**
	 * adds the image to the imageBundle
	 */
	public void addToBundle()
	{
		try
		{
			clazz.getImageBundleBuilder().assimilate(getImageURL());
		}
		catch (Exception e)
		{
			logger.log(Level.SEVERE, "Could not add " + getImageURL() + " to the imageBundle", e);
		}
	}

	/**
	 * get the imageRect which contains the details of the image NOTE don't call
	 * this method before creation of the imagebundle
	 * 
	 * @return imageRect
	 */
	public ImageRect getImageRect()
	{
		return clazz.getImageBundleBuilder().getMapping(getImageURL());
	}

	/**
	 * create the CSS style for the image
	 * 
	 * @param imageRect
	 * @return
	 */
	public String getStyle(ImageRect imageRect)
	{
		// TODO may change on the future
		String fileName = String.format("resources/%s/%s.png", clazz.getBinaryName(), clazz
				.getClassName());
		return String
				.format(
						"background-image: url(%s); background-position:-%dpx -%dpx; width:%dpx; height:%dpx;",
						fileName, imageRect.getLeft(), imageRect.getTop(), imageRect.getWidth(),
						imageRect.getHeight());
	}

}
