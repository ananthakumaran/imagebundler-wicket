---
layout: default
title: ImageBundler
header : Automatic Compile Time Image Bundling for Wicket
meta_keywords: imagebundler , wicket image bundling , Wicket , wicket image sprite 
meta_description: imageBundler for apache wicket
---

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

	// bundle class
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
  public ImageItem saveFileIcon();
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
  ImageItem imageItem = bundle.saveFileIcon();
  Image img = new Image(id);
  img.add(new SimpleAttributeModifier("src", imageItem.getSrc()));
  img.add(new SimpleAttributeModifier("style", imageItem.getStyle()));
  add(img);

{% endhighlight %}

Localization
------------
ImageBundler provides support for using different images for different locales.

{% highlight java %}
  // localization eg
  @ImageBundle(locale = { "ta_IN" })
  public interface SampleImage
  {
	public ImageItem a();
	public ImageItem b();
	@Resource("c.png")
	public Image sample(String id);
  }
{% endhighlight java %}

For each method there should be a image file without any locale(default). so for
the above eg. there should be three images namely `a.*,b.*,c.png`.

The image file for the Tamil locale should be named as `a_ta_IN.*,b_ta_IN.*,c_ta_IN.png`.
If you leave any of the image file for Tamil locale then the default image will be used instead.

ImageItem
---------
The style and src of the image is available through the ImageItem interface. To get 
the `ImageItem` change the method signature as follows

{% highlight java %}
public ImageItem methodName();
{% endhighlight %}

How it Works
------------
JDK 6 Annotation processor is used to generate source files.A  sample generated file.

{% highlight java %}

public class SampleImageBundle implements SampleImage
{
	@Override
	public ImageItem a()
	{
		return new AbstractImageItem("images/clear.gif")
		{
			@Override
			public String getStyle()
			{
				String locale = RequestCycle.get().getSession().getLocale().toString();
				String style = " background-image :url(resources/org.imagebundler.wicket.examples.SampleImageBundle/SampleImageBundle.png) ; background-position:-48px -0px; width:24px; height:24px;  ";
				if(locale.equals("ta_IN"))
				{
					style = " background-image :url(resources/org.imagebundler.wicket.examples.SampleImageBundle/SampleImageBundle_ta_IN.png) ; background-position:-50px -0px; width:25px; height:25px;  ";
				}
				return style;
			}
		}
	}
	
	@Override
	public ImageItem b()
	{
		return new AbstractImageItem("images/clear.gif")
		{
			@Override
			public String getStyle()
			{
				String locale = RequestCycle.get().getSession().getLocale().toString();
				String style = " background-image :url(resources/org.imagebundler.wicket.examples.SampleImageBundle/SampleImageBundle.png) ; background-position:-24px -0px; width:24px; height:24px;  ";
				if(locale.equals("ta_IN"))
				{
					style = " background-image :url(resources/org.imagebundler.wicket.examples.SampleImageBundle/SampleImageBundle_ta_IN.png) ; background-position:-25px -0px; width:25px; height:25px;  ";
				}
				return style;
			}
		}
	}
	
	@Override
	public Image sample(String id)
	{
		Image image = new Image(id);
		image.add(new SimpleAttributeModifier("src", "images/clear.gif"));
		String locale = RequestCycle.get().getSession().getLocale().toString();
		String style = " background-image :url(resources/org.imagebundler.wicket.examples.SampleImageBundle/SampleImageBundle.png) ; background-position:-0px -0px; width:24px; height:24px;  ";
				if(locale.equals("ta_IN"))
				{
					style = " background-image :url(resources/org.imagebundler.wicket.examples.SampleImageBundle/SampleImageBundle_ta_IN.png) ; background-position:-0px -0px; width:25px; height:25px;  ";
				}
				image.add(new SimpleAttributeModifier("style", style));
		return image;
	}
}

{% endhighlight %}
##Configuration##

###Interface###
*	Annotate your interface with @ImageBundle and pass the array of the locales if needed
*	All the methods in the interface should have any of the following signature 
{% highlight java %}
public Image methodName(String id)
public ImageItem methodName()
{% endhighlight %}

###imagebundler.properties###

*	For the image bundler to work a 1px transparent gif image namely clear.gif should be placed in  images/clear.gif (relative to the webapp). This can be changed by proper configuration
*	This file is not needed if you follow the defaults
{% highlight ini %}
# This needs a 1px transparent gif to work.
# [default images/clear.gif]
image.clear=path/relativeto/webapp
{% endhighlight %}

Example
-------
see here for a [sample](http://github.com/ananthakumaran/imagebundler-wicket/tree/master/imagebundler-wicket-examples/)
project.
