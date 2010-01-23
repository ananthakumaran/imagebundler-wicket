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
