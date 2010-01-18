package org.wicketstuff.imagebundler.processor;
import static org.wicketstuff.imagebundler.processor.CurrentEnv.getFiler;
import static org.wicketstuff.imagebundler.processor.CurrentEnv.getMessager;

import java.io.IOException;
import java.io.Writer;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;

import org.wicketstuff.imagebundler.util.BundleClass;

public class BundleClassGenerator
{
	private Element element;

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
			Writer writer = getFiler().createSourceFile(bundleClass.getBinaryName(), element)
					.openWriter();
			writer.write(bundleClass.toCode());
			writer.close();
			getMessager().printMessage(Kind.NOTE, bundleClass.getClassName() + " created");
		}
		catch (IOException ioE)
		{
			getMessager().printMessage(Kind.ERROR, "could not create file");
		}

	}
}
