Usage
-----
for info about usage see 
[http://ananthakumaran.github.com/imagebundler-wicket](http://ananthakumaran.github.com/imagebundler-wicket)

TODO
----

Locales support can be provided by taking a array of locales in the annotation

	@ImageBundler({"en","en_US"});
	
allow the user to provide different images for different locales by appending
the locale at the end.

	open_en.png;
	
	

Instead of adding inline styles generate a seperate css file and
write the style for all locales.
 

        @Override
    	public Image openFileIcon(String id)
    	{
            String localeClass = "openFileIcon"  + Request.get().getSession().getLocale(); 
    		Image image = new Image(id);
    		image.add(new SimpleAttributeModifier("src","images/clear.gif"));
    		image.add(new SimpleAttributeModifier("class",  localClass + " openFileIcon "));
    		return image;
    	}

genereted css should be like this.
    .openFileIcon {
     background-image :url(resources/org.imagebundler.wicket.examples.WordProcessorImageBundle/WordProcessorImageBundle.png) ;
     background-position:-Xpx -Xpx; width:Xpx; height:Xpx;
    }
    
    .openFileIcon_en {
     background-image :url(resources/org.imagebundler.wicket.examples.WordProcessorImageBundle/WordProcessorImageBundle_en.png) ;
     background-position:-Xpx -Xpx; width:Xpx; height:Xpx;
    }

using this method we can fallback to default locale if we get a different locale other than the expected. 