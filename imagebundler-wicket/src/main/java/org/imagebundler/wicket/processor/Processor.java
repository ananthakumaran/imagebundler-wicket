/**
 * Copyright (C) 2009 Anantha Kumaran <ananthakumaran@gmail.com>
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

import org.imagebundler.wicket.ImageBundle;
import org.imagebundler.wicket.util.FileLogger;

/**
 * generates source files
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
		CurrentEnv.getMessager().printMessage(Kind.NOTE, "ImageBundle processing started");
		boolean writeLogs = false;
		try
		{
			for (Element element : roundEnv.getElementsAnnotatedWith(ImageBundle.class))
			{
				writeLogs = true;
				getMessager().printMessage(Kind.NOTE, element.getSimpleName());
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
		}
		catch (Exception e)
		{
			getLogger().log(Level.SEVERE, "", e);
			writeLogs = true;
		}
		finally
		{
			if (writeLogs)
			{
				logLogsToTextFile();
			}
		}
		getMessager().printMessage(Kind.NOTE, "processing finished");
		return true;
	}

	private void warnElementIsUnhadled(Element element)
	{
		getMessager().printMessage(Kind.WARNING, "Unhandled element " + element);
	}

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
