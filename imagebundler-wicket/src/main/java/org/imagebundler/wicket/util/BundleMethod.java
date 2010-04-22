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

import static org.imagebundler.wicket.util.Utils.insertLocale;

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
import org.imagebundler.wicket.ImageBundleBuilder.ImageRect;
import org.imagebundler.wicket.processor.CurrentEnv;

/**
 * USED to create a bundle method
 * 
 * @author Ananth
 */
public class BundleMethod
{

	private final FileLogger logger = CurrentEnv.getLogger();
	/** list of image urls <locale , imageurl > */
	private final Map<String, ImageURL> imageURLs = new HashMap<String, ImageURL>();
	/** package name */
	private final String packageName;
	/** class name */
	private final String className;
	/** list of locale */
	private final String[] locales;
	/** method element */
	private final Element methodElement;
	/** return type of the method */
	private String returnType;

	/**
	 * constructor
	 * 
	 * @throws ImageNotFoundException
	 */
	public BundleMethod(Element methodElement, String packageName, String className,
			String[] locales) throws Exception
	{
		checkMethodSignature(methodElement);
		this.packageName = packageName;
		this.locales = locales;
		this.methodElement = methodElement;
		this.className = className;
	}

	/**
	 * checks the method signature and throws exception if it is not of type
	 * <code>public Image methodName(String arg1)</code> or
	 * <code>public ImageItem methodName()</code>
	 * 
	 * @param methodElement
	 *            method element
	 * @throws MethodSignatureException
	 */
	@SuppressWarnings("unchecked")
	void checkMethodSignature(Element methodElement) throws MethodSignatureException
	{
		// All the methods in the Interface is Abstract by default
		Set<Modifier> modifiers = methodElement.getModifiers();
		if (!(modifiers.size() == 2 && modifiers.contains(Modifier.ABSTRACT) && modifiers
				.contains(Modifier.PUBLIC)))
		{
			throw new MethodSignatureException();
		}

		ExecutableElement execMethodElement = (ExecutableElement)methodElement;


		String returnType = execMethodElement.getReturnType().toString();
		List<VariableElement> params = (List<VariableElement>)execMethodElement.getParameters();

		// check return Type
		if (!((returnType.equals("org.apache.wicket.markup.html.image.Image") && (params.size() == 1 && params
				.get(0).asType().toString().equals("java.lang.String"))) || (returnType
				.equals("org.imagebundler.wicket.ImageItem") && (params.size() == 0))))
		{
			throw new MethodSignatureException();
		}
		else
		{
			this.returnType = returnType;
		}


	}


	/**
	 * appends all the code the to rich string
	 * 
	 * @param tabCount
	 *            tabCount
	 * @return String representation of the method
	 */
	public RichString toCode(int tabCount, Map<String, ImageRect> imagePos) // <locale
	// ,
	// imagepo>
	{

		String returnType = this.returnType.equals("org.imagebundler.wicket.ImageItem")
				? "ImageItem"
				: "Image";

		String imageSrc = getProperty("image.clear");
		String defaultStyle = getStyle("default", imagePos.get("default"));

		RichString str = new RichString(tabCount);
		str.line();
		str.append("@Override").line();
		str.append("public ").append(returnType + " ").append(getMethodName()).append("(");

		if ("ImageItem".equals(returnType))
		{
			str.append(")");
		}
		else
		{
			str.append("String id)");
		}


		str.open();
		str.append("SimpleImageItem imageItem = new SimpleImageItem(\"").append(imageSrc).append(
				"\",\"").append(defaultStyle).append("\")").semicolon();

		for (String locale : imagePos.keySet())
		{
			if (!"default".equals(locale))
			{
				str.append("imageItem.addLocalizedStyle(\"").append(locale).append("\",\"").append(
						getStyle(locale, imagePos.get(locale))).append("\")").semicolon();
			}
		}

		if ("ImageItem".equals(returnType))
		{
			str.append("return imageItem").semicolon();
		}
		else
		{
			str.append("Image image = new Image(id)").semicolon();
			str.append("image.add(new ImageItemModifier(imageItem))").semicolon();
			str.append("return image").semicolon();
		}
		return str.close();
	}

	/**
	 * creates the image urls
	 * 
	 * @throws ImageNotFoundException
	 */
	public void buildImageURL() throws ImageNotFoundException
	{
		Resource resource = methodElement.getAnnotation(Resource.class);
		if (resource == null)
		{
			// tries to construct the default image url
			ImageURL defaultImageURL = matchExt(getMethodName());
			imageURLs.put("default", defaultImageURL);

			// construct the urls for all the locales
			for (String locale : locales)
			{
				imageURLs.put(locale, matchExt(Utils.insertLocale(getMethodName(), locale),
						defaultImageURL));
			}
		}
		else
		{
			try
			{
				if (exists(resource.value()))
				{
					// tries to construct the default image url
					ImageURL defaultImageURL = new ImageURL(packageName, resource.value(),
							getPath(resource.value()), getMethodName());
					imageURLs.put("default", defaultImageURL);
					// construct the urls for all the locales
					for (String locale : locales)
					{
						String value = Utils.insertLocale(resource.value(), locale);
						if (exists(value))
						{
							imageURLs.put(locale, new ImageURL(packageName, value, getPath(value),
									getMethodName()));
						}
						else
						{
							imageURLs.put(locale, defaultImageURL);
						}
					}
				}
				else
				{
					// image not found
					throw new ImageNotFoundException("cann't find the image " + resource.value());
				}
			}
			catch (Exception ex)
			{
				logger.log(Level.SEVERE, "", ex);
				throw new ImageNotFoundException("cann't find the image " + resource.value());
			}
		}
	}

	/**
	 * matches a list of file extensions for the given name. if no match found
	 * throws {@link ImageNotFoundException}
	 * 
	 * @param name
	 * @return
	 * @throws ImageNotFoundException
	 */
	ImageURL matchExt(String name) throws ImageNotFoundException
	{
		return matchExt(name, null);
	}

	/**
	 * mathces a list of file extensions for the given name. if no match found
	 * the defaut Image URL will be returned
	 * 
	 * @param name
	 * @param defaultImageURL
	 * @return
	 * @throws ImageNotFoundException
	 */
	private ImageURL matchExt(String name, ImageURL defaultImageURL) throws ImageNotFoundException
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
				if (exists(name + extension))
				{
					// image found
					return new ImageURL(packageName, name + extension, getPath(name + extension),
							getMethodName());
				}
			}
			catch (Exception ex)
			{
				logger.log(Level.SEVERE, "", ex);
			}
		}

		// image not found
		if (defaultImageURL != null)
		{
			return defaultImageURL;
		}
		else
		{
			throw new ImageNotFoundException("cann't find the image for the method " + name);
		}
	}

	/**
	 * check for the existense of the given file in the current package
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	boolean exists(String fileName) throws IOException
	{
		File file = new File(getPath(fileName));
		return file.exists();
	}

	/**
	 * gets the full path of the file relative to the current package
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	String getPath(String fileName) throws IOException
	{
		FileObject FileObj = CurrentEnv.getFiler().getResource(StandardLocation.CLASS_OUTPUT,
				packageName, fileName);
		return FileObj.toUri().toString().replace("file:", "");
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
		return imageURLs.get(locale);
	}

	/**
	 * get the default image url
	 * 
	 * @return
	 */
	public ImageURL getImageURL()
	{
		return imageURLs.get("default");
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

	/**
	 * getter for method Name
	 * 
	 * @return
	 */
	public String getMethodName()
	{
		return methodElement.getSimpleName().toString();
	}


	public String getStyle(String locale, ImageRect imageRect)
	{
		ImageURL imageURL = imageURLs.get(locale);
		String rule = String
				.format(
						" background-image :url(resources/%s.%s/%s.png) ; background-position:-%dpx -%dpx; width:%dpx; height:%dpx;  ",
						imageURL.getPackageName(), className, insertLocale(className, locale),
						imageRect.getLeft(), imageRect.getTop(), imageRect.getWidth(), imageRect
								.getHeight());
		return rule;
	}
}
