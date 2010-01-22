package org.wicketstuff.imagebundler.util;

import javax.lang.model.element.Element;

import org.wicketstuff.imagebundler.ImageNotFoundException;
import org.wicketstuff.imagebundler.Resource;
import org.wicketstuff.imagebundler.ImageBundleBuilder.ImageRect;

/**
 * used to create a bundle method
 * 
 * @author Ananth
 */
public class BundleMethod
{

	/** simple method name */
	private final String methodName;
	/** image url */
	private final ImageURL imageURL;
	/** The eclosing class of this method */
	private final BundleClass clazz;

	/**
	 * constructor
	 * 
	 * @throws ImageNotFoundException
	 */
	public BundleMethod(Element methodElement, BundleClass clazz) throws Exception
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
		// TODO check the signature of this method in the interface
		// method signature
		str.append("public Image ").append(methodName).append("(String id)").open();

		// declare the image
		str.append("Image image = new Image(id, \"spacer.gif\")").semicolon();
		// set the style element
		str.append("image.add(new SimpleAttributeModifier(\"style\", \"").append(
				getStyle(getImageRect())).append("\"))").semicolon();
		str.append("return image").semicolon().close();
		return str;
	}

	/**
	 * creates the image url for the given method
	 * 
	 * @return imageURL
	 */
	public ImageURL buildImageURL(Element element)
	{
		Resource resource = element.getAnnotation(Resource.class);
		ImageURL imageURL = new ImageURL(clazz.getPackageName(), methodName);
		if (resource != null)
		{
			imageURL.setResource(resource.value());
		}
		return imageURL;
	}

	/**
	 * getter for imageUrl
	 * 
	 * @return imageUrl
	 */
	public ImageURL getImageURL()
	{
		return imageURL;
	}

	/**
	 * adds the image to the imageBundle
	 */
	public void addToBundle() throws Exception
	{
		clazz.getImageBundleBuilder().assimilate(getImageURL());
	}

	/**
	 * get the imageRect which contains the details of the image NOTE don't call
	 * this method before creation of the imagebundle
	 * 
	 * @return imageRect
	 */
	public ImageRect getImageRect()
	{
		return clazz.getImageBundleBuilder().getMapping(getImageURL().getMethodName());
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
