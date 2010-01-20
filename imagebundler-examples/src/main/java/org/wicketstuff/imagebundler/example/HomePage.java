package org.wicketstuff.imagebundler.example;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;


/**
 * Homepage
 */
public class HomePage extends WebPage
{

	private static final long serialVersionUID = 1L;


	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public HomePage(final PageParameters parameters)
	{


		 SampleImage bundle = new SampleImageBundle();

		// send the orginal file name
		 add(bundle.buttons("bold" , "bns.png"));
		 add(bundle.course("code" , "buttons.png"));
		// TODO Add your page's components here
	}
}
