package org.wicketstuff.sprite.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.wicket.markup.html.image.Image;

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
	public BundleClass(Class<?> clazz)
	{
		this.className = clazz.getSimpleName() + "Bundle";
		this.interfaceName = clazz.getSimpleName();
		this.packageName = clazz.getPackage().getName();
		this.binaryName = packageName + "." + className;

		// add import for the wicket image class
		addImports(Image.class);

		// add methods
		addMethods(clazz.getMethods());
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
	public BundleClass addMethods(Method... methods)
	{
		for (Method method : methods)
		{
			this.methods.add(new BundleMethod(method));
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
}
