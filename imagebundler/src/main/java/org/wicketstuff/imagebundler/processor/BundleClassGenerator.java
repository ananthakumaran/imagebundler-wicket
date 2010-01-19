package org.wicketstuff.imagebundler.processor;

import static org.wicketstuff.imagebundler.processor.CurrentEnv.getFiler;
import static org.wicketstuff.imagebundler.processor.CurrentEnv.getMessager;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.lang.model.element.Element;
import javax.tools.StandardLocation;
import javax.tools.Diagnostic.Kind;

import org.wicketstuff.imagebundler.util.BundleClass;

/**
 * generates the ImageBundle class
 * 
 * @author Ananth
 * 
 */
public class BundleClassGenerator
{
	private Element element;
	private static final String BUNDLE_TYPE = "png";
	private final Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * constructor
	 * 
	 * @param element
	 *            element with <code>@ImageBundle</code> annotation
	 */
	public BundleClassGenerator(Element element)
	{
		this.element = element;
	}

	public void generate()
	{
		BundleClass bundleClass = new BundleClass(element.asType().toString());
		bundleClass.addMethods(element.getEnclosedElements());
		try
		{
			OutputStream outStream = getFiler().createResource(StandardLocation.SOURCE_OUTPUT,
					bundleClass.getPackageName(), bundleClass.getClassName() + "." + BUNDLE_TYPE,
					element).openOutputStream();
			bundleClass.drawBundleImage(outStream);
			outStream.close();
		}
		catch (IOException e)
		{
			logger.log(Level.SEVERE, "could not create bundle image", e);
		}
		try
		{
			Writer writer = getFiler().createSourceFile(bundleClass.getBinaryName(), element)
					.openWriter();
			writer.write(bundleClass.toCode());
			writer.close();
			getMessager().printMessage(Kind.NOTE, bundleClass.getClassName() + " class created");
		}
		catch (IOException ioE)
		{
			getMessager().printMessage(Kind.ERROR, "could not create file");
		}

	}
}
