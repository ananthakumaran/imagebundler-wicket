package org.imagebundler.wicket.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.lang.model.element.Element;

/**
 * 
 * @author Ananth
 * 
 */
public class BundleMethodStub extends BundleMethod
{

	public static final Logger logger = Logger.getLogger(BundleMethodStub.class.getName());
	private List<String> fileList = new ArrayList<String>();
	public String path = "testPath";
	public String methodName = "methodName";

	public BundleMethodStub(Element methodElement, String packageName, String[] locales)
			throws Exception
	{
		super(methodElement, packageName, locales);
	}

	@Override
	void checkMethodSignature(Element methodElement)
			throws org.imagebundler.wicket.MethodSignatureException
	{

	};

	@Override
	public boolean exists(String file)
	{
		return fileList.contains(file);
	}

	@Override
	String getPath(String fileName) throws java.io.IOException
	{
		return path;
	};

	public void addFile(String fileName)
	{
		fileList.add(fileName);
	}

	@Override
	String getMethodName()
	{
		return methodName;
	}
}
