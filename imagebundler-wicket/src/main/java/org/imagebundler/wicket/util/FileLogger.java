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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

/**
 * utility to log the info into a file
 * 
 * @author Ananth
 * 
 */
public class FileLogger
{
	private final RichString logs = new RichString();

	/**
	 * logs the messages with exception
	 * 
	 * @param level
	 *            log level
	 * @param message
	 *            message
	 * @param ex
	 *            exception
	 */
	public void log(Level level, String message, Exception ex)
	{

		// [level] message
		logs.append("[").append(level.getName()).append("]").tab().append(message).line();

		if (ex != null)
		{
			// log the stack trace
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			ex.printStackTrace(printWriter);
			logs.append(writer.toString()).line();
		}

		// end with a horizantal rule
		logs.repeat("-", 100).line();
	}

	/**
	 * logs the messages
	 * 
	 * @param level
	 *            log level
	 * @param message
	 *            information
	 */
	public void log(Level level, String message)
	{
		log(level, message, null);
	}

	@Override
	public String toString()
	{
		return logs.toString();
	}
}
