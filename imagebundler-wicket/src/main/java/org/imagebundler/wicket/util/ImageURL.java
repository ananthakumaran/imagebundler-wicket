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

/**
 * utility class to represent imageURL
 * 
 * @author Ananth
 * 
 */
public class ImageURL
{
	/** package name */
	private String packageName;
	/** method name */
	private String methodName;
	/** image name */
	private String imageName;
	/** path of the image */
	private String path;

	/**
	 * 
	 * @param packageName
	 *            package name
	 * @param methodName
	 *            image name with extension
	 */
	public ImageURL(String packageName, String imageName, String path, String methodName)
	{
		this.packageName = packageName;
		this.imageName = imageName;
		this.path = path;
		this.methodName = methodName;
	}

	public String getPath()
	{
		return path;
	}

	public String getPackageName()
	{
		return packageName;
	}

	public String getMethodName()
	{
		return methodName;
	}

	public String getImageName()
	{
		return imageName;
	}

	public void setImageName(String imageName)
	{
		this.imageName = imageName;
	}

}
