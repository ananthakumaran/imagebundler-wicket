/**
 * Copyright (C) 2009 Anantha Kumaran <ananthakumaran@gmail.com>
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

package org.imagebundler.wicket.util;

import java.util.Collection;

/**
 * string wrapper. provides some utility function to create source code and also
 * provide method chaining
 * 
 * @author Ananth
 */
public final class RichString
{
	// orignal string container
	private final StringBuffer string = new StringBuffer("");
	// used for indent purpose
	private int tabCount = 0;

	/**
	 * constructor
	 */
	public RichString()
	{
	}

	/**
	 * constructor
	 * 
	 * @param tabCount
	 *            tabCount
	 */
	public RichString(int tabCount)
	{
		this.tabCount = tabCount;
	}

	/**
	 * appends the string
	 * 
	 * @param string
	 *            string
	 * @return {@link RichString}
	 */
	public RichString append(String string)
	{
		this.string.append(string);
		return this;
	}

	/**
	 * appends the string
	 * 
	 * @param string
	 *            string
	 * @return {@link RichString}
	 */
	public RichString append(RichString string)
	{
		return append(string.toString());
	}

	/**
	 * adds a statement.By default add a semicolon and a newLine at the end of
	 * each statement
	 * 
	 * @param keyword
	 *            keyword
	 * @param str
	 *            str
	 * @return {@link RichString}
	 */
	public RichString statement(String keyword, Collection<String> str)
	{
		return statement(keyword, true, str);
	}

	/**
	 * adds a statement.
	 * 
	 * @param keyword
	 *            keyword
	 * @param semicolon
	 *            semicolon
	 * @param str
	 *            str
	 * @return {@link RichString}
	 */
	public RichString statement(String keyword, boolean semicolon, Collection<String> str)
	{
		for (String s : str)
		{
			append(keyword).space().append(s).semicolon();
		}
		return this;
	}

	/**
	 * adds a new line
	 * 
	 * @return {@link RichString}
	 */
	public RichString line()
	{
		return append("\n").repeat("\t", tabCount);
	}

	/**
	 * appends a new line and a open braces and a new line
	 * 
	 * @return {@link RichString}
	 */
	public RichString open()
	{
		line();
		tabCount++;
		return append("{").line();
	}

	/**
	 * appends a close braces and a new line
	 * <P>
	 * NOTE this won't append a line before it bcoz in most case it would be
	 * preceeded by semicolon which appends the line
	 * 
	 * @return {@link RichString}
	 */
	public RichString close()
	{
		// remove the last tab added by the line method
		this.removeTail("\t");
		tabCount--;
		return append("}").line();
	}

	/**
	 * adds a semicolon and a new line
	 * 
	 * @return {@link RichString}
	 */
	public RichString semicolon()
	{
		return append(";").line();
	}

	/**
	 * appends a space
	 * 
	 * @return {@link RichString}
	 */
	public RichString space()
	{
		return append(" ");
	}

	/**
	 * appends a tab
	 * 
	 * @return {@link RichString}
	 */
	public RichString tab()
	{
		return append("\t");
	}


	/**
	 * repeatedly append the str for the specified no of time
	 * 
	 * @param str
	 *            str
	 * @param times
	 *            no of times
	 * @return {@link RichString}
	 */
	public RichString repeat(String str, int times)
	{
		for (int i = 0; i < times; i++)
		{
			append(str);
		}
		return this;
	}

	@Override
	public String toString()
	{
		return string.toString();
	}

	/**
	 * getter for tabCount
	 * 
	 * @return tabCount
	 */
	public int getTabCount()
	{
		return tabCount;
	}

	/**
	 * delete the
	 * <code>str<code> from the string if it is the tail of the string
	 * 
	 * @param str
	 * @return {@link RichString}
	 */
	public RichString removeTail(String str)
	{
		int lastIndex = string.lastIndexOf(str);
		if (lastIndex != -1 && lastIndex + str.length() == string.length())
		{
			string.delete(string.lastIndexOf(str), string.length());
		}
		return this;
	}
}
