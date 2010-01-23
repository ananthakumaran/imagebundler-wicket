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

import java.lang.annotation.ElementType;
import java.util.Set;
import java.util.logging.Level;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

import org.imagebundler.wicket.ImageNotFoundException;
import org.imagebundler.wicket.MethodSignatureException;
import org.imagebundler.wicket.Resource;
import org.imagebundler.wicket.ImageBundleBuilder.ImageRect;
import org.imagebundler.wicket.processor.CurrentEnv;

import sun.security.action.GetLongAction;

/**
 * used to create a bundle method
 * 
 * @author Ananth
 */
public class BundleMethod
{

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
	 * <p>
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
		// check the return type and arguments
		if (!methodElement.asType().toString().equals(
				"(java.lang.String)org.apache.wicket.markup.html.image.Image"))

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
		// TODO check the signature of this method in the interface
		// method signature
		str.append("public Image ").append(methodName).append("(String id)").open();

		// declare the image
		str.append("Image image = new Image(id, \"spacer.gif\")").semicolon();
		// set the style element
		str.append("image.add(new SimpleAttributeModifier(\"style\", \"").append(
				getStyle(getImageRect())).append("\"))").semicolon();
		str.append("return image").semicolon().close();
		return str;
	}

	/**
	 * creates the image url for the given method
	 * 
	 * @return imageURL
	 */
	private ImageURL buildImageURL(Element element)
	{
		Resource resource = element.getAnnotation(Resource.class);
		ImageURL imageURL = new ImageURL(clazz.getPackageName(), methodName);
		if (resource != null)
		{
			imageURL.setResource(resource.value());
		}
		return imageURL;
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
		// TODO may change on the future
		String fileName = String.format("resources/%s/%s.png", clazz.getBinaryName(), clazz
				.getClassName());
		return String
				.format(
						"background-image: url(%s); background-position:-%dpx -%dpx; width:%dpx; height:%dpx;",
						fileName, imageRect.getLeft(), imageRect.getTop(), imageRect.getWidth(),
						imageRect.getHeight());
	}

}
