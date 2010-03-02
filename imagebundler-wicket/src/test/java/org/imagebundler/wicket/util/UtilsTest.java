package org.imagebundler.wicket.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilsTest
{
	@Test
	public void testInsertLocale()
	{
		assertEquals("value_lo", Utils.insertLocale("value", "lo"));
		assertEquals("value_lo.ext", Utils.insertLocale("value.ext", "lo"));
		assertEquals("value", Utils.insertLocale("value", "default"));
		assertEquals("value.ext", Utils.insertLocale("value.ext", "default"));
	}
}
