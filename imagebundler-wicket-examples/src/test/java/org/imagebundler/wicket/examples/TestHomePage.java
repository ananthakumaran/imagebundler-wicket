package org.imagebundler.wicket.examples;

import junit.framework.TestCase;

import org.apache.wicket.util.tester.WicketTester;


/**
 * Simple test using the WicketTester
 */
public class TestHomePage extends TestCase
{
	private WicketTester tester;

	@Override
	public void setUp()
	{
		this.tester = new WicketTester(new WicketApplication());
	}

	public void testRenderMyPage()
	{
		// start and render the test page
		// tester.startPage(HomePage.class);

		// assert rendered page class
		// tester.assertRenderedPage(HomePage.class);

	}
}
