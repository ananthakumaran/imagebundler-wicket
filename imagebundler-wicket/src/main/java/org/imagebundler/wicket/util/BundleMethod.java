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

package org.imagebundler.wicket.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.imagebundler.wicket.ImageNotFoundException;
import org.imagebundler.wicket.MethodSignatureException;
import org.imagebundler.wicket.Resource;
import org.imagebundler.wicket.processor.CurrentEnv;

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
	/** list of image urls */
	private final Map<String, ImageURL> imageURL = new HashMap<String, ImageURL>();
	/** package name */
	private final String packageName;
	/** list of locale */
	private String[] locales;
	/** method element */
	private final Element methodElement;

	/**
	 * constructor
	 * 
	 * @throws ImageNotFoundException
	 */
	public BundleMethod(Element methodElement, String packageName, String[] locales)
			throws Exception
	{
		checkMethodSignature(methodElement);
		this.methodName = methodElement.getSimpleName().toString();
		this.packageName = packageName;
		this.locales = locales;
		this.methodElement = methodElement;
	}

	/**
	 * checks the method signature and throws exception if it is not of type
	 * <code>public Image methodName(String arg1)</code>
	 * 
	 * @param methodElement
	 *            method element
	 * @throws MethodSignatureException
	 */
	@SuppressWarnings("unchecked")
	private void checkMethodSignature(Element methodElement) throws MethodSignatureException
	{
		// All the methods in the Interface is Abstract by default
		Set<Modifier> modifiers = methodElement.getModifiers();
		if (!(modifiers.size() == 2 && modifiers.contains(Modifier.ABSTRACT) && modifiers
				.contains(Modifier.PUBLIC)))
		{
			throw new MethodSignatureException();
		}

		ExecutableElement execMethodElement = (ExecutableElement)methodElement;

		// check return Type
		if (!execMethodElement.getReturnType().toString().equals(
				"org.apache.wicket.markup.html.image.Image"))
		{
			throw new MethodSignatureException();
		}


		List<VariableElement> params = (List<VariableElement>)execMethodElement.getParameters();
		// check parameters
		if (!(params.size() == 1 && params.get(0).asType().toString().equals("java.lang.String")))
		{
			throw new MethodSignatureException();
		}
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
		// locale
		str.append(" String locale = RequestCycle.get().getSession().getLocale().toString()")
				.semicolon();
		// method signature
		str.append("public Image ").append(methodName).append("(String id)").open();

		// declare the image
		str.append("Image image = new Image(id)").semicolon();

		// set the src
		str.append("image.add(new SimpleAttributeModifier(\"src\", \"").append(
				getProperty("image.clear")).append("\"))").semicolon();
		// TODO add the CSS styles here
		str.append("image.add(new SimpleAttributeModifier(\"class\",").append("locale")
				.append("))").semicolon();
		str.append("return image").semicolon().close();
		return str;
	}

	/**
	 * creates the image url for the given method
	 * 
	 * @param element
	 *            element
	 * @return {@link ImageURL}
	 * @throws ImageNotFoundException
	 */
	public ImageURL buildImageURL() throws ImageNotFoundException
	{
		Resource resource = methodElement.getAnnotation(Resource.class);
		if (resource == null)
		{
			// This method is not annotated with @Resource.So fall back and
			// check for any image with methodname
			String[] extensions = { ".png", ".gif", ".jpg", ".jpeg", ".jpe" };
			for (String extension : extensions)
			{

				try
				{
					// we are guessing the file extension and attempting to
					// open it. If there is not such file an exception will
					// be thrown.
					if (exists(methodName + extension))
					{
						// image found
						return new ImageURL(packageName, methodName + extension, getPath(methodName
								+ extension), methodName);
					}
				}
				catch (Exception ex)
				{
					logger.log(Level.SEVERE, "", ex);
				}
			}
			// image not found
			throw new ImageNotFoundException("cann't find the image for the method " + methodName);
		}
		else
		{
			try
			{
				if (exists(resource.value()))
				{
					// image found
					return new ImageURL(packageName, resource.value(), getPath(resource.value()),
							methodName);
				}
				// image not found
				throw new ImageNotFoundException("cann't find the image " + resource.value());
			}
			catch (Exception ex)
			{
				logger.log(Level.SEVERE, "", ex);
				throw new ImageNotFoundException("cann't find the image " + resource.value());
			}
		}
	}


	/**
	 * check for the existense of the given file in the current package
	 * 
	 * @param image
	 * @return
	 * @throws IOException
	 */
	private boolean exists(String image) throws IOException
	{
		File imageFile = new File(getPath(image));
		return imageFile.exists();
	}

	/**
	 * gets the full path of the file relative to the current package
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private String getPath(String fileName) throws IOException
	{
		FileObject imageFileObj = CurrentEnv.getFiler().getResource(StandardLocation.CLASS_OUTPUT,
				packageName, fileName);
		return imageFileObj.toUri().toString().replace("file:", "");
	}

	/**
	 * getter for the image url
	 * 
	 * @param locale
	 *            locale
	 * @return
	 */
	public ImageURL getImageURL(String locale)
	{
		return imageURL.get(locale);
	}

	/**
	 * get the default image url
	 * 
	 * @return
	 */
	public ImageURL getImageURL()
	{
		return imageURL.get("default");
	}

	/**
	 * get the propertye
	 * 
	 * @param key
	 *            key
	 * @return value
	 */
	private String getProperty(String key)
	{
		return CurrentEnv.getProperties().get(key);
	}

}
