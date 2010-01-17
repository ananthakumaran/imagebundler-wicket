package org.wicketstuff.sprite.processor;

import static javax.lang.model.SourceVersion.RELEASE_6;

import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;

import org.wicketstuff.sprite.ImageBundle;
import org.wicketstuff.sprite.util.BundleClass;


/**
 * generates source files
 * 
 * @author Ananth
 * 
 */
@SupportedAnnotationTypes( { "org.wicketstuff.sprite.ImageBundle" })
@SupportedSourceVersion(RELEASE_6)
public class Processor extends AbstractProcessor
{
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
	{
		getMessager().printMessage(Kind.NOTE, "processing started");
		try
		{
			for (Element element : roundEnv.getElementsAnnotatedWith(ImageBundle.class))
			{
				// handle interface only
				if (element.getKind() == ElementKind.INTERFACE)
				{
					// TODO clean up

					BundleClass bundleClass = new BundleClass(Class.forName(element.asType()
							.toString()));
					Writer writer = getFiler().createSourceFile(bundleClass.getBinaryName())
							.openWriter();
					writer.write(bundleClass.toCode());
					writer.close();
					getMessager().printMessage(Kind.NOTE, bundleClass.getClassName() + " created");
				}
				else
				{
					warnElementIsUnhadled(element);
				}

			}
		}
		catch (Exception e)
		{

		}
		getMessager().printMessage(Kind.NOTE, "processing finished");
		return true;
	}

	private void warnElementIsUnhadled(Element element)
	{
		getMessager().printMessage(Kind.WARNING, "Unhandled element " + element);
	}

	private Filer getFiler()
	{
		return processingEnv.getFiler();
	}

	private Messager getMessager()
	{
		return processingEnv.getMessager();
	}

	private Elements getElementsUtils()
	{
		return processingEnv.getElementUtils();
	}
}
