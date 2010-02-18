---
layout: default
title: ImageBundler
header : ImageBundler For Apache Wicket
meta_keywords: imagebundler , wicket image bundling , Wicket , wicket image sprite 
meta_description: imageBundler for apache wicket
---

Automatic Compile-Time Image Bundling For Apache Wicket
=======================================================
Inspiration
-----------
[http://code.google.com/p/google-web-toolkit/wiki/ImageBundleDesign](http://code.google.com/p/google-web-toolkit/wiki/ImageBundleDesign)
Goals
-----
*	Allow developers to easily create bundles of static images that can be downloaded in a single HTTP request.
*	Solve the "bouncy UI startup" problem in an automated way that does not require developers to specify image sizes explicitly in code.
*	Avoid even simple HTTP freshness checks, leaving HTTP connections available for important work.
Non-Goals
---------
*	Find the optimal way to combine individual images into a single image in multiple dimensions. Simply lining up images left-to-right to create an image strip is sufficient.
*	Support use cases that need a dynamic set of images, such as those in which end user input controls the set of images. For example, this mechanism is not intended as a way to implement a photo gallery.
Defining an Image Bundle
------------------------
{% highlight java %}
@ImageBundle
interface WordProcessorImage {

  /**
   * Would match either file 'newFileIcon.gif' or 'newFileIcon.png'
   * in the same package as this type.
   * Note that other file extensions may also be recognized.
   */
  public Image newFileIcon(String id);

  /**
   * Would bundle the file 'open-file-icon.gif' residing in the same package as this type.
   */
  @Resource("open-file-icon.gif")
  public Image openFileIcon(String id);

  /**
   * Would bundle the file 'savefile.gif' residing in the folder icons relative to this package.
   */
  @Resource("icons/savefile.gif")
  public Image saveFileIcon(String id);
}
{% endhighlight %}
Using an Image Bundle
---------------------
{% highlight java %}

  /**
   *  If you configured eclipse properly WordProcessorImageBundle Class will be automatically created after you save
   *  the  WordProcessorImage interface
   */
  WordProcessorImage bundle = new WordProcessorImageBundle();
  add(bundle.openFileIcon("openfile"));
  add(bundle.saveFileIcon("savefile"));

{% endhighlight %}
How it Works
------------
JDK 6 Annotation processor is used to generate source files.For the above example the generated class will
be like this
{% highlight java %}
public class WordProcessorImageBundle implements WordProcessorImage
{
	
	@Override
	public Image newFileIcon(String id)
	{
		Image image = new Image(id);
		image.add(new SimpleAttributeModifier("src", "images/clear.gif"));
		image.add(new SimpleAttributeModifier("style", "background-image: "+
		+"url(resources/org.imagebundler.wicket.examples.WordProcessorImageBundle/WordProcessorImageBundle.png);"
		+"background-position:-Xpx -Xpx; width:Xpx; height:Xpx;"));
		return image;
	}
	
	@Override
	public Image openFileIcon(String id)
	{
		Image image = new Image(id);
		image.add(new SimpleAttributeModifier("src", "images/clear.gif"));
		image.add(new SimpleAttributeModifier("style", "background-image: "+
		+"url(resources/org.imagebundler.wicket.examples.WordProcessorImageBundle/WordProcessorImageBundle.png);"
		+"background-position:-Xpx -Xpx; width:Xpx; height:Xpx;"));
		return image;
	}
	@Override
	public Image saveFileIcon(String id);
		Image image = new Image(id);
		image.add(new SimpleAttributeModifier("src", "images/clear.gif"));
		image.add(new SimpleAttributeModifier("style", "background-image: "+
		+"url(resources/org.imagebundler.wicket.examples.WordProcessorImageBundle/WordProcessorImageBundle.png);"
		+"background-position:-Xpx -Xpx; width:Xpx; height:Xpx;"));
		return image;
	}
}

{% endhighlight %}
##Configuration##
###Interface###
*	Annotate your interface with @ImageBundle
*	All the methods in the interface should have the following signature 
{% highlight java %}
public Image methodName(String id)
{% endhighlight %}
*	By default the image that have the same name as method name residing in the same package as this type will be bundled. for example for a method
sample, anyone of the following file sample.gif , sample.png  , sample.jpg in the same package will be bundled
###imagebundler.properties###

*	For the image bundler to work a 1px transparent gif image namely clear.gif should be placed in  images/clear.gif (relative to the webapp). This can be changed by proper configuration
*	By default all the generated imagebundle will be placed in the same package.
*	This file is not needed if you follow the defaults
{% highlight java %}
# [default the folder that contains the imagebundler.properties will be considered as the basedir]
basedir=full/pathto/base/directory
# [default src/main/webapp]
webapp=path/relativeto/basedir
# This needs a 1px transparent gif to work.
# [default images/clear.gif]
image.clear=path/relativeto/webapp
# [default same package] note that the generated image bundles will be placed at basedir+webapp+image.output folder
image.output=path/for/generated/images/relativeto/webapp
{% endhighlight %}
