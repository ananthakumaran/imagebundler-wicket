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

package org.imagebundler.wicket;

/**
 * The method signature should be of the form
 * <code>Image methodName(String arg)</code>
 * 
 * @author Ananth
 */
public class MethodSignatureException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 * 
	 * @param message
	 *            message
	 */
	public MethodSignatureException()
	{
		super("The signature of the method should be public Image methodName(String arg)");
	}
}
