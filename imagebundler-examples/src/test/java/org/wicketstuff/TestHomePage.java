package org.wicketstuff;

import junit.framework.TestCase;
import org.apache.wicket.util.tester.WicketTester;
import org.wicketstuff.imagebundler.example.HomePage;
import org.wicketstuff.imagebundler.example.WicketApplication;


/**
 * Simple test using the WicketTester
 */
public class TestHomePage extends TestCase
{
	private WicketTester tester;

	@Override
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	public void testRenderMyPage()
	{
		// start and render the test page
		tester.startPage(HomePage.class);

		// assert rendered page class
		tester.assertRenderedPage(HomePage.class);

	}
}
