/**
 * Copyright (C) 2010 Anantha Kumaran <ananthakumaran@gmail.com>
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.imagebundler.wicket.processor;

import static javax.lang.model.SourceVersion.RELEASE_6;
import static org.imagebundler.wicket.processor.CurrentEnv.getLogger;
import static org.imagebundler.wicket.processor.CurrentEnv.getMessager;

import java.io.OutputStream;
import java.util.Set;
import java.util.logging.Level;

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

import org.imagebundler.wicket.CSSRules;
import org.imagebundler.wicket.ImageBundle;
import org.imagebundler.wicket.util.FileLogger;

/**
 * generates the bundle class and bundle image
 * 
 * @author Ananth
 * 
 */
@SupportedAnnotationTypes( { "org.imagebundler.wicket.ImageBundle" })
@SupportedSourceVersion(RELEASE_6)
public class Processor extends AbstractProcessor
{
	private final FileLogger logger = CurrentEnv.getLogger();

	@Override
	public void init(ProcessingEnvironment processingEnv)
	{
		super.init(processingEnv);
		CurrentEnv.set(this.processingEnv);
	}


	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
	{
		boolean writeLogs = false;
		try
		{
			for (Element element : roundEnv.getElementsAnnotatedWith(ImageBundle.class))
			{
				writeLogs = true;
				// handle interface only
				if (element.getKind() == ElementKind.INTERFACE)
				{
					try
					{
						new BundleClassGenerator(element).generate();
					}
					catch (Exception ex)
					{
						getLogger().log(Level.SEVERE, "could not generate class", ex);
					}
				}
				else
				{
					warnElementIsUnhadled(element);
				}
			}
			CSSRules.get().save();
		}
		catch (Exception e)
		{
			getLogger().log(Level.SEVERE, "could not process the annotation", e);
			writeLogs = true;
		}
		finally
		{
			if (writeLogs)
			{
				logLogsToTextFile();
			}
		}
		return true;
	}

	/**
	 * warn the user if he puts the annotation in Types other than Interface
	 * 
	 * @param element
	 *            element
	 */
	private void warnElementIsUnhadled(Element element)
	{
		getMessager().printMessage(Kind.WARNING, "Unhandled element " + element);
		logger.log(Level.WARNING, "@ImageBundle annotation should be applied to Interface only");
	}

	/**
	 * write the logs to the text file for debugging purposes
	 */
	private void logLogsToTextFile()
	{
		try
		{
			if (logger.toString() != "")
			{
				FileObject fo = this.processingEnv.getFiler().createResource(
						StandardLocation.SOURCE_OUTPUT, "", "ImageBundler-logs.txt");
				OutputStream out = fo.openOutputStream();
				out.write(logger.toString().getBytes());
				out.close();
			}
		}
		catch (Exception e2)
		{
			this.processingEnv.getMessager().printMessage(Kind.ERROR,
					"Error writing out logs " + e2.getMessage());
		}
	}
}
