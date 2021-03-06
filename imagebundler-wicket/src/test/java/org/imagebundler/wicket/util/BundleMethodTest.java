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


import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;

import javax.lang.model.element.Element;

import org.imagebundler.wicket.ImageNotFoundException;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * @author Ananth
 * 
 */
public class BundleMethodTest
{
	BundleMethodStub bundleMethod;

	@Before
	public void setUp() throws Exception
	{
		String[] locales = new String[] { "en", "en_US" };
		Element mockElement = createMock(Element.class);
		bundleMethod = new BundleMethodStub(mockElement, "org.test", "testclass", locales);
	}

	@Test
	public void testMatchExt() throws ImageNotFoundException
	{
		bundleMethod.addFile("test.png");
		ImageURL imageURL = bundleMethod.matchExt("test");
		assertEquals("test.png", imageURL.getImageName());
	}

	@Test(expected = ImageNotFoundException.class)
	public void testMatchExtForWrongFileName() throws ImageNotFoundException
	{
		ImageURL imageURL = bundleMethod.matchExt("test");
		assertEquals("test.png", imageURL.getImageName());
	}

}
