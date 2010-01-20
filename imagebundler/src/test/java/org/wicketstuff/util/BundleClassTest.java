package org.wicketstuff.util;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.lang.model.element.Element;

import org.apache.wicket.util.file.File;
import org.junit.Ignore;
import org.junit.Test;
import org.wicketstuff.imagebundler.util.BundleClass;
import org.wicketstuff.imagebundler.util.MockElement;


@Ignore
public class BundleClassTest
{

	@Test
	public void testBundleClass()
	{
//		System.out.println("asdfsadfasf");
//
//		BundleClass bundleClass = new BundleClass("org.wicketstuff.util.SampleImage");
//
//		// Try to put it inside the source
//		File f = new File("generatedImage.png");
//		FileOutputStream fileOutputStream = null;
//		try
//		{
//			f.createNewFile();
//		}
//		catch (IOException e1)
//		{
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try
//		{
//			fileOutputStream = new FileOutputStream(f);
//
//		}
//		catch (FileNotFoundException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//		assertNotNull(bundleClass);
//		Element buttonMethod = new MockElement("buttons");
//		Element coureMethod = new MockElement("course");
//
//		bundleClass.addMethods(Arrays.asList(buttonMethod, coureMethod));
//		bundleClass.drawBundleImage(fileOutputStream);
//		try
//		{
//			fileOutputStream.close();
//		}
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.print(bundleClass.getBinaryName());
//		System.out.println(bundleClass.toCode());
	}


}
