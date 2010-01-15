package com.wicketstuff.sprite.example;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;

/**
 * Homepage
 */
public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public HomePage(final PageParameters parameters) {


        SampleImageBundle bundle = new SampleImageBundle();
        
        // send the orginal file name
        add(bundle.boldImage("bold" , "buttons.png"));
        add(bundle.codeImage("code" , "buttons.png"));
        // TODO Add your page's components here
    }
}
