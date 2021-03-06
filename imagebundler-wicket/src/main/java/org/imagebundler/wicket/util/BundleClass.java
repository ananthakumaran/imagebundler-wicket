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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import org.imagebundler.wicket.ImageNotFoundException;
import org.imagebundler.wicket.ImageBundleBuilder.ImageRect;

/**
 * used to create a Bundle class.
 * 
 * @author Ananth
 * 
 */
public class BundleClass
{

	/** full package name */
	private final String packageName;
	/** simple class name */
	private final String className;
	/** full class name including the package eg: java.lang.String */
	private final String binaryName;
	/** The implemented interface name */
	private final String interfaceName;
	/** imports list */
	private final Set<String> imports = new TreeSet<String>();
	/** method list */
	public final List<BundleMethod> methods = new ArrayList<BundleMethod>();
	private final String[] locale;
	/**
	 * contains the path of the BundleImage of this class.This path is relative
	 * to the webapp dir
	 */
	private String imageBundlePath;

	/**
	 * constructor
	 * 
	 * @param clazz
	 *            class of the interface
	 */
	public BundleClass(String fullClazzName, String[] locale)
	{
		// TODO cleanup
		this.className = getSimpleName(fullClazzName) + "Bundle";
		this.interfaceName = getSimpleName(fullClazzName);
		this.packageName = parsePackageName(fullClazzName);
		this.binaryName = packageName + "." + className;
		this.locale = locale;
		// add import for the wicket image class
		addImports("org.apache.wicket.markup.html.image.Image");
		addImports("org.imagebundler.wicket.ImageItem");
		addImports("org.imagebundler.wicket.SimpleImageItem");
		addImports("org.imagebundler.wicket.ImageItemModifier");
		buildImageBundlePath();
	}

	/**
	 * get the class name only
	 * 
	 * @param fullClazzName
	 *            binary name of the class eg java.lang.Object
	 * @return class Name
	 */
	public String getSimpleName(String fullClazzName)
	{
		int lastIndex = fullClazzName.lastIndexOf('.');
		if (lastIndex != -1)
		{
			return fullClazzName.substring(lastIndex + 1);
		}
		// default package
		return fullClazzName;
	}

	/**
	 * get the package name
	 * 
	 * @param fullClazzName
	 *            binary name of the class eg java.lang.Object
	 * @return package name
	 */
	public String parsePackageName(String fullClazzName)
	{
		int lastIndex = fullClazzName.lastIndexOf('.');
		if (lastIndex != -1)
		{
			return fullClazzName.substring(0, lastIndex);
		}
		// default package
		return "";
	}

	/**
	 * getter for package Name
	 * 
	 * @return
	 */
	public String getPackageName()
	{
		return packageName;
	}

	/**
	 * adds the import
	 * 
	 * @param className
	 *            full class name
	 */
	public void addImports(String className)
	{
		imports.add(className);
	}

	/**
	 * adds methods to the class. throws {@link ImageNotFoundException} if the
	 * image cann't be found
	 * 
	 * @param methods
	 *            methods
	 * @return {@link BundleClass}
	 * @throws ImageNotFoundException
	 */
	public BundleClass addMethods(List<? extends Element> methodElements) throws Exception
	{
		for (Element method : methodElements)
		{
			if (method.getKind() == ElementKind.METHOD)
			{
				BundleMethod bundleMethod = new BundleMethod(method, packageName, className, locale);
				bundleMethod.buildImageURL();
				this.methods.add(bundleMethod);
			}
		}
		return this;
	}

	/**
	 * gets the String form of the class
	 * 
	 * @return String representation of the class
	 */
	public String toCode(Map<String, Map<String, ImageRect>> imagePos)
	{

		RichString str = new RichString();
		// package declaration
		// TODO get rid of this
		str.statement("package", Arrays.asList(packageName)).line();
		// imports
		str.statement("import", imports).line();

		// TODO add comments

		// class declaration
		str.append("public class ").append(className).append(" implements ").append(interfaceName)
				.open();
		// method declaration
		for (BundleMethod method : methods)
		{
			str.append(method.toCode(str.getTabCount(), imagePos.get(method.getMethodName())));
		}
		// close the class
		str.close();

		return str.toString();
	}

	/**
	 * getter for class name
	 * 
	 * @return className
	 */
	public String getClassName()
	{
		return className;
	}

	/**
	 * getter for binary name
	 * 
	 * @return binaryName
	 */
	public String getBinaryName()
	{
		return binaryName;
	}

	/**
	 * getter
	 * 
	 * @return imageBundlePath that can be used as url
	 */
	public String getImageBundlePath()
	{
		return imageBundlePath;
	}

	/**
	 * builds the path for the imagebundle that we are going to create
	 */
	private void buildImageBundlePath()
	{
		imageBundlePath = String.format("resources/%s/%s.png", binaryName, className);
	}
}
