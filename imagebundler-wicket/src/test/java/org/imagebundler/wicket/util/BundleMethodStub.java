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
import java.util.List;
import java.util.logging.Logger;

import javax.lang.model.element.Element;

/**
 * 
 * @author Ananth
 * 
 */
public class BundleMethodStub extends BundleMethod
{

	public static final Logger logger = Logger.getLogger(BundleMethodStub.class.getName());
	private final List<String> fileList = new ArrayList<String>();
	public String path = "testPath";
	public String methodName = "methodName";

	public BundleMethodStub(Element methodElement, String packageName, String className,
			String[] locales) throws Exception
	{
		super(methodElement, packageName, className, locales);
	}

	@Override
	void checkMethodSignature(Element methodElement)
			throws org.imagebundler.wicket.MethodSignatureException
	{

	};

	@Override
	public boolean exists(String file)
	{
		return fileList.contains(file);
	}

	@Override
	String getPath(String fileName) throws java.io.IOException
	{
		return path;
	};

	public void addFile(String fileName)
	{
		fileList.add(fileName);
	}

	@Override
	public String getMethodName()
	{
		return methodName;
	}
}
