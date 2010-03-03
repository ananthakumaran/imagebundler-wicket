/**
 * Copyright (C) 2010 Anantha Kumaran <ananthakumaran@gmail.com>
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.imagebundler.wicket.examples;

import java.util.Locale;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;


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
	public HomePage()
	{

		EditorButton editorBundle = new EditorButtonBundle();
		this.add(editorBundle.italic("italic"));
		this.add(editorBundle.bold("bold"));
		this.add(editorBundle.italic("anotheritalic"));

		this.add(new Link<Void>("china")
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				Locale locale = Locale.CHINESE;
				Session session = RequestCycle.get().getSession();
				session.setLocale(locale);
				this.setResponsePage(new HomePage());
			}
		});

		this.add(new Link<Void>("english")
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				Locale locale = Locale.ENGLISH;
				Session session = RequestCycle.get().getSession();
				session.setLocale(locale);
				this.setResponsePage(new HomePage());
			}
		});
	}
}
