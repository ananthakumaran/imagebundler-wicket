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
