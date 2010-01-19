package org.wicketstuff.imagebundler.util;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.image.Image;
import org.wicketstuff.imagebundler.ImageBundleBuilder;

/**
 * used to create a Bundle class.
 * <p>
 * 
 * <pre>
 * package org.test;
 * 
 * import org.apache.wicket.markup.html.image.Image;
 * import org.wicketstuff.sprite.ImageBundle;
 * 
 * public interface SampleImage
 * {
 * 	public Image boldImage(String id , String fileName);
 * 	public Image codeImage(String id , String fileName);
 * }
 *  
 * For the above the generated class will be like this
 * 
 * package org.test;
 * 
 * import org.test.Sample;
 * import org.apache.wicket.markup.html.image.Image;
 * 
 * public class SampleImageBundle implements SampleImage 
 * {
 * 	public Image boldImage(String id , String fileName)
 * 	{
 * 	// code goes here
 * 	}
 * 	public Image codeImage(String id , String fileName)
 * 	{
 * 	// code goes here
 * 	}
 * }
 * 
 * <pre>
 * 
 * 
 * @author Ananth
 * 
 */
public class BundleClass
{

	private final Logger logger = Logger.getLogger(getClass().getName());
	/** utility to create the bundle image */
	private ImageBundleBuilder imageBundleBuilder = new ImageBundleBuilder();

	private final String packageName;
	private final String className;
	private final String binaryName;
	private final String interfaceName;
	private final Set<String> imports = new TreeSet<String>();
	private final List<BundleMethod> methods = new ArrayList<BundleMethod>();

	/**
	 * constructor
	 * 
	 * @param clazz
	 *            class of the interface
	 */
	public BundleClass(String fullClazzName)
	{
		this.className = getSimpleName(fullClazzName) + "Bundle";
		this.interfaceName = getSimpleName(fullClazzName);
		this.packageName = parsePackageName(fullClazzName);
		this.binaryName = packageName + "." + className;

		// add import for the wicket image class
		addImports(Image.class);
		addImports(SimpleAttributeModifier.class);
	}

	/**
	 * get the class name only
	 * 
	 * @param fullClazzName
	 *            binary name of the class eg java.lang.Object
	 * @return class Name
	 */
	public String getSimpleName(String fullClazzName)
	{
		return fullClazzName.substring(fullClazzName.lastIndexOf('.') + 1);
	}

	/**
	 * get the package name
	 * 
	 * @param fullClazzName
	 *            binary name of the class eg java.lang.Object
	 * @return package name
	 */
	public String parsePackageName(String fullClazzName)
	{
		return fullClazzName.substring(0, fullClazzName.lastIndexOf('.'));
	}

	/**
	 * getter for package Name
	 * 
	 * @return
	 */
	public String getPackageName()
	{
		return packageName;
	}

	/**
	 * adds the class to the set of imports
	 * 
	 * @param clazzes
	 *            classes
	 * @return {@link BundleClass}
	 */
	public BundleClass addImports(Class<?>... clazzes)
	{
		for (Class<?> clazz : clazzes)
		{
			imports.add(clazz.getName());
		}
		return this;
	}

	/**
	 * adds methods to the class
	 * 
	 * @param methods
	 *            methods
	 * @return {@link BundleClass}
	 */
	public BundleClass addMethods(List<? extends Element> methodElements)
	{
		for (Element method : methodElements)
		{
			if (method.getKind() == ElementKind.METHOD)
			{
				this.methods.add(new BundleMethod(method, this));
			}
		}
		return this;
	}

	/**
	 * gets the String form of the class
	 * 
	 * @return String representation of the class
	 */
	public String toCode()
	{

		RichString str = new RichString();
		// package declaration
		// TODO get rid of this
		str.statement("package", Arrays.asList(packageName)).line();
		// imports
		str.statement("import", imports).line();

		// TODO add comments

		// class declaration
		str.append("public class ").append(className).append(" implements ").append(interfaceName)
				.open();
		// method declaration
		for (BundleMethod method : methods)
		{
			str.append(method.toCode(str.getTabCount()));
		}
		// close the class
		str.close();

		return str.toString();
	}

	/**
	 * getter for class name
	 * 
	 * @return className
	 */
	public String getClassName()
	{
		return className;
	}

	/**
	 * getter for binary name
	 * 
	 * @return binaryName
	 */
	public String getBinaryName()
	{
		return binaryName;
	}

	public ImageBundleBuilder getImageBundleBuilder()
	{
		return imageBundleBuilder;
	}


	public void drawBundleImage(OutputStream outputStream)
	{
		try
		{
			imageBundleBuilder.writeBundledImage(outputStream);
		}
		catch (Exception e)
		{
			logger.log(Level.SEVERE, "could not draw bundle image", e);
		}
	}
}
