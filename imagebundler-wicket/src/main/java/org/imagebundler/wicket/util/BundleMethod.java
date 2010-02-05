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
import java.util.List;
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
import org.imagebundler.wicket.ImageBundleBuilder.ImageRect;
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
	/** image url */
	private final ImageURL imageURL;
	/** The eclosing class of this method */
	private final BundleClass clazz;

	/**
	 * constructor
	 * 
	 * @throws ImageNotFoundException
	 */
	public BundleMethod(Element methodElement, BundleClass clazz) throws Exception
	{
		checkMethodSignature(methodElement);
		this.methodName = methodElement.getSimpleName().toString();
		this.clazz = clazz;
		this.imageURL = buildImageURL(methodElement);
		addToBundle();
	}

	/**
	 * checks the method signature and throws exception if it is not of type
	 * <code>public Image methodName(String arg1)</code>
	 * 
	 * @param methodElement
	 *            method element
	 * @throws MethodSignatureException
	 */
	private void checkMethodSignature(Element methodElement) throws MethodSignatureException
	{
		// All the methods in the Interface is Abstract by default
		Set<Modifier> modifiers = methodElement.getModifiers();
		if (!(modifiers.size() == 2 && modifiers.contains(Modifier.ABSTRACT) && modifiers
				.contains(Modifier.PUBLIC)))
		{
			throw new MethodSignatureException(
					"The signature of the method should be public Image methodName(String arg)");
		}

		ExecutableElement execMethodElement = (ExecutableElement)methodElement;

		// check return Type
		if (!execMethodElement.getReturnType().toString().equals(
				"org.apache.wicket.markup.html.image.Image"))
		{
			throw new MethodSignatureException(
					"The signature of the method should be public Image methodName(String arg)");
		}


		List<VariableElement> params = (List<VariableElement>)execMethodElement.getParameters();
		// check parameters
		if (!(params.size() == 1 && params.get(0).asType().toString().equals("java.lang.String")))
		{
			throw new MethodSignatureException(
					"The signature of the method should be public Image methodName(String arg)");
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
		// method signature
		str.append("public Image ").append(methodName).append("(String id)").open();

		// declare the image
		str.append("Image image = new Image(id)").semicolon();

		// set the src
		str.append("image.add(new SimpleAttributeModifier(\"src\", \"").append(
				getProperty("image.clear")).append("\"))").semicolon();
		str.append("String url = RequestCycle.get().urlFor(new ResourceReference(this.getClass(), \"EditorButtonBundle.png\", RequestCycle.get().getSession().getLocale(), null)) + \"\"").semicolon();
		// set the style element
		str.append("image.add(new SimpleAttributeModifier(\"style\", \"").append(
				getStyle(getImageRect())).append("\"))").semicolon();
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
	private ImageURL buildImageURL(Element element) throws ImageNotFoundException
	{
		Resource resource = element.getAnnotation(Resource.class);
		if (resource == null)
		{
			// This method is not annotated with @Resource.So fall back and
			// check for any image with methodname
			// TODO check for all the jpeg extension
			String[] extensions = { ".png", ".gif", ".jpg", ".jpeg", ".jpe" };
			for (String extension : extensions)
			{

				try
				{
					// we are guessing the file extension and attempting to
					// open it. If there is not such file an exception will
					// be thrown.
					FileObject imageFileObj = CurrentEnv.getFiler().getResource(
							StandardLocation.CLASS_OUTPUT, clazz.getPackageName(),
							methodName + extension);
					final String path = imageFileObj.toUri().toString().replace("file:", "");
					File imageFile = new File(path);
					if (imageFile.exists())
					{
						// image found
						return new ImageURL(clazz.getPackageName(), methodName + extension, path,
								methodName);
					}
				}
				catch (Exception ex)
				{
					logger.log(Level.SEVERE, "", ex);
				}
			}
			// image not found
			// TODO provide some detail message
			throw new ImageNotFoundException("cann't find the image for the method " + methodName);
		}
		else
		{
			try
			{
				FileObject imageFileObj = CurrentEnv.getFiler().getResource(
						StandardLocation.CLASS_OUTPUT, clazz.getPackageName(), resource.value());
				final String path = imageFileObj.toUri().toString().replace("file:", "");
				File imageFile = new File(path);
				if (imageFile.exists())
				{
					// image found
					return new ImageURL(clazz.getPackageName(), resource.value(), path, methodName);
				}
				// image not found
				// TODO provide some detail message
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
	 * getter for imageUrl
	 * 
	 * @return imageUrl
	 */
	private ImageURL getImageURL()
	{
		return imageURL;
	}

	/**
	 * adds the image to the imageBundle
	 */
	private void addToBundle() throws Exception
	{
		clazz.getImageBundleBuilder().assimilate(getImageURL());
	}

	/**
	 * get the imageRect which contains the details of the image NOTE don't call
	 * this method before creation of the imagebundle
	 * 
	 * @return imageRect
	 */
	private ImageRect getImageRect()
	{
		return clazz.getImageBundleBuilder().getMapping(getImageURL().getMethodName());
	}

	/**
	 * create the CSS style for the image
	 * 
	 * @param imageRect
	 * @return
	 */
	private String getStyle(ImageRect imageRect)
	{
		return String
				.format(
						"background-image: url(\"+url+\"); background-position:-%dpx -%dpx; width:%dpx; height:%dpx;",
						imageRect.getLeft(), imageRect.getTop(), imageRect.getWidth(), imageRect
								.getHeight());
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
