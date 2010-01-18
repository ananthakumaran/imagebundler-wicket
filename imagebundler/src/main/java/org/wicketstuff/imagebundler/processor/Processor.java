package org.wicketstuff.imagebundler.processor;

import static javax.lang.model.SourceVersion.RELEASE_6;
import static org.wicketstuff.imagebundler.processor.CurrentEnv.getMessager;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import javax.tools.Diagnostic.Kind;

import org.wicketstuff.imagebundler.ImageBundle;


/**
 * generates source files
 * 
 * @author Ananth
 * 
 */
@SupportedAnnotationTypes( { "org.wicketstuff.imagebundler.ImageBundle" })
@SupportedSourceVersion(RELEASE_6)
public class Processor extends AbstractProcessor
{

	@Override
	public void init(ProcessingEnvironment processingEnv)
	{
		super.init(processingEnv);
		CurrentEnv.set(this.processingEnv);
	}


	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
	{
		CurrentEnv.getMessager().printMessage(Kind.NOTE, "ImageBundle processing started");
		try
		{
			for (Element element : roundEnv.getElementsAnnotatedWith(ImageBundle.class))
			{
				getMessager().printMessage(Kind.NOTE, element.getSimpleName());
				// handle interface only
				if (element.getKind() == ElementKind.INTERFACE)
				{
					new BundleClassGenerator(element).generate();
				}
				else
				{
					warnElementIsUnhadled(element);
				}
			}
		}
		catch (Exception e)
		{
			logExceptionToTextFile(e);
		}
		getMessager().printMessage(Kind.NOTE, "processing finished");
		return true;
	}

	private void warnElementIsUnhadled(Element element)
	{
		getMessager().printMessage(Kind.WARNING, "Unhandled element " + element);
	}

	/** Logs <code>e</code> to <code>SOURCE_OUTPUT/ImageBundler-errors.txt</code> */
	private void logExceptionToTextFile(Exception e)
	{
		try
		{
			FileObject fo = this.processingEnv.getFiler().createResource(
					StandardLocation.SOURCE_OUTPUT, "", "ImageBundler-exception.txt");
			OutputStream out = fo.openOutputStream();
			e.printStackTrace(new PrintStream(out));
			// Specifically for Eclipse's AbortCompilation exception which has a
			// useless printStackTrace output
			try
			{
				Field f = e.getClass().getField("problem");
				Object problem = f.get(e);
				out.write(problem.toString().getBytes());
			}
			catch (NoSuchFieldException nsfe)
			{
			}
			out.close();
		}
		catch (Exception e2)
		{
			this.processingEnv.getMessager().printMessage(Kind.ERROR,
					"Error writing out error message " + e2.getMessage());
		}
	}

}
