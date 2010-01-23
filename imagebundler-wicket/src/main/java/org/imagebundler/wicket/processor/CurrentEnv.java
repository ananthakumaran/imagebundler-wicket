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

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.imagebundler.wicket.util.FileLogger;

/**
 * Provides static helper methods to get at the current
 * {@link ProcessingEnvironment}.
 * 
 * The {@link ProcessingEnvironment} used to get passed around as a method
 * parameter, but a whole lot of places need it, so putting it in one static
 * location cut down on the parameter cruft.
 * 
 */
public class CurrentEnv
{

	/** current environment */
	private static ProcessingEnvironment current;
	/** logger */
	private static final FileLogger logger = new FileLogger();

	public static void set(ProcessingEnvironment env)
	{
		current = env;
	}

	public static ProcessingEnvironment get()
	{
		return current;
	}


	public static Filer getFiler()
	{
		return current.getFiler();
	}

	public static Messager getMessager()
	{
		return current.getMessager();
	}

	public static Elements getElementUtils()
	{
		return current.getElementUtils();
	}

	public static Types getTypeUtils()
	{
		return current.getTypeUtils();
	}

	public static FileLogger getLogger()
	{
		return logger;
	}

}
