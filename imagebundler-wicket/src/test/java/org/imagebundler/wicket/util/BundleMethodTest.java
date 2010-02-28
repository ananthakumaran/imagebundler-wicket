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
		bundleMethod = new BundleMethodStub(mockElement, "org.test", locales);
	}

	@Test
	public void testMatchExt() throws ImageNotFoundException
	{
		bundleMethod.addFile("test.png");
		ImageURL imageURL = bundleMethod.matchExt("test");
		assertEquals("test.png", imageURL.getImageName());
	}

	@Test(expected=ImageNotFoundException.class)
	public void testMatchExtForWrongFileName() throws ImageNotFoundException
	{
		ImageURL imageURL = bundleMethod.matchExt("test");
		assertEquals("test.png", imageURL.getImageName());
	}
	
	
}
