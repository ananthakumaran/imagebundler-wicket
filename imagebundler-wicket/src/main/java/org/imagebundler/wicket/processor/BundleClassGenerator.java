package org.imagebundler.wicket.processor;

import static org.imagebundler.wicket.processor.CurrentEnv.getFiler;
import static org.imagebundler.wicket.processor.CurrentEnv.getMessager;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.logging.Level;

import javax.lang.model.element.Element;
import javax.tools.StandardLocation;
import javax.tools.Diagnostic.Kind;

import org.imagebundler.wicket.util.BundleClass;
import org.imagebundler.wicket.util.FileLogger;

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
	private final FileLogger logger = CurrentEnv.getLogger();

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

	public void generate() throws Exception
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

			// don't try to create class if the you could not create the
			// imageBundle
			throw new Exception("could not create bundle image");
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
