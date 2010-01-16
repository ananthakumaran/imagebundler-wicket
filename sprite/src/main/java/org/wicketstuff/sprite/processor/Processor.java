package org.wicketstuff.sprite.processor;

import static javax.lang.model.SourceVersion.RELEASE_6;

import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;


@SuppressWarnings("restriction")
@SupportedAnnotationTypes({"org.wicketstuff.sprite.ImageBundle"})
@SupportedSourceVersion(RELEASE_6)
public class Processor extends AbstractProcessor
{
	Logger logger = Logger.getLogger(getClass().getName());

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
	{
		if (!roundEnv.processingOver())
	    processingEnv.getMessager().printMessage(Kind.MANDATORY_WARNING, "hello World");
	return false; // Don't claim any annotations
	}

}
