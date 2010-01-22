package org.wicketstuff.imagebundler.util;

import java.util.logging.Level;

import javax.lang.model.element.Element;
import javax.tools.StandardLocation;

import org.wicketstuff.imagebundler.ImageNotFoundException;
import org.wicketstuff.imagebundler.Resource;
import org.wicketstuff.imagebundler.ImageBundleBuilder.ImageRect;
import org.wicketstuff.imagebundler.processor.CurrentEnv;

/**
 * used to create a bundle method
 * 
 * @author Ananth
 */
public class BundleMethod
{

	private final FileLogger logger = CurrentEnv.getLogger();
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
	public BundleMethod(Element methodElement, BundleClass clazz) throws ImageNotFoundException
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
	 * @throws ImageNotFoundException
	 */
	public ImageURL buildImageURL(Element element) throws ImageNotFoundException
	{
		Resource resource = element.getAnnotation(Resource.class);
		if (resource == null)
		{
			// This method is not annotated with @Resource.So fall back and
			// check for any image with methodname


			// TODO check for all the jpeg extension
			String[] extensions = { ".png", ".gif", ".jpg", ".jpeg", ".jpe" };
			for (String extension : extensions)
			{
				try
				{
					CurrentEnv.getFiler().getResource(StandardLocation.CLASS_OUTPUT,
							clazz.getPackageName(), methodName + extension).openInputStream()
							.close();
					// image found
					return new ImageURL(clazz.getPackageName(), methodName + extension);
				}
				catch (Exception ex)
				{
					// fail silently
				}
			}
			// image not found
			// TODO provide some detail message
			throw new ImageNotFoundException("cann't find the image for the method " + methodName);
		}
		else
		{
			try
			{
				CurrentEnv.getFiler().getResource(StandardLocation.CLASS_OUTPUT,
						clazz.getPackageName(), resource.value()).openInputStream().close();
				// image found
				return new ImageURL(clazz.getPackageName(), resource.value());
			}
			catch (Exception ex)
			{
				logger.log(Level.SEVERE, "cann't find the image " + resource.value(), ex);
				// image not found
				// TODO provide some detail message
				throw new ImageNotFoundException("cann't find the image " + resource.value());
			}

		}
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
		return clazz.getImageBundleBuilder().getMapping(getImageURL().imageName);
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
