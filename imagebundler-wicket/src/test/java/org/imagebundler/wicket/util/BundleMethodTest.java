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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

import junit.framework.Assert;

import org.imagebundler.wicket.MethodSignatureException;
import org.junit.Test;


/**
 * 
 * @author Ananth
 * 
 */
public class BundleMethodTest
{

	@Test
	public void testCheckMethodSignature()
	{
		// no modifiers
		try
		{
			BundleMethod bundleMethod = new BundleMethod(new MockElement("sampleMethod"),
					new BundleClass("org.stub"));
			Assert.fail("should throw MethodSignatureException");
		}
		catch (MethodSignatureException ex)
		{
			// expected
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			Assert.fail("should throw MethodSignatureException");
		}

		// public abstract modifier
		try
		{
			MockElement mockMethodElement = new MockElement("mockMethod");
			Set<Modifier> modifiers = new HashSet<Modifier>();
			modifiers.add(Modifier.PUBLIC);
			modifiers.add(Modifier.ABSTRACT);
			mockMethodElement.setModifier(modifiers);
			mockMethodElement.setReturnType("org.apache.wicket.markup.html.image.Image");
			List<VariableElement> parlist = new ArrayList<VariableElement>();
			parlist.add(new MockVariableElement("java.lang.String"));
			mockMethodElement.setParameters(parlist);
			BundleMethod bundleMethod = new BundleMethod(mockMethodElement, new BundleClass(
					"org.stub"));
		}
		catch (MethodSignatureException ex)
		{
			ex.printStackTrace();
			Assert.fail("should  not throw  MethodSignatureException");
		}
		catch (Exception ex)
		{
			// expected
		}

		// more than two modifier
		try
		{
			MockElement mockMethodElement = new MockElement("mockMethod");
			Set<Modifier> modifiers = new HashSet<Modifier>();
			modifiers.add(Modifier.PUBLIC);
			modifiers.add(Modifier.STATIC);
			mockMethodElement.setModifier(modifiers);
			mockMethodElement.setReturnType("java.lang.String");
			List<VariableElement> parlist = new ArrayList<VariableElement>();
			parlist.add(new MockVariableElement("java.lang.String"));
			mockMethodElement.setParameters(parlist);
			BundleMethod bundleMethod = new BundleMethod(mockMethodElement, new BundleClass(
					"org.stub"));
			Assert.fail("should throw MethodSignatureException");
		}
		catch (MethodSignatureException ex)
		{
			// expected
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			Assert.fail("should throw MethodSignatureException");
		}

	}
}
