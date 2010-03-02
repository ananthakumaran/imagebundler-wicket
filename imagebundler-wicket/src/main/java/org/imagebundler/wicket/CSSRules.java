package org.imagebundler.wicket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.imagebundler.wicket.processor.CurrentEnv;
import org.imagebundler.wicket.util.FileLogger;

public class CSSRules
{
	private final static FileLogger logger = CurrentEnv.getLogger();
	private static CSSRules INSTANCE = null;

	public List<String> rules = new ArrayList<String>();
	private static File cssFile;

	private CSSRules()
	{

	}

	public static CSSRules get()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new CSSRules();
			INSTANCE.loadRules();
		}
		return INSTANCE;
	}

	private void loadRules()
	{
		String cssFileUrl = CurrentEnv.getProperties().get("basedir");
		cssFileUrl += "/" + CurrentEnv.getProperties().get("webapp") + "/imagebundler.css";
		cssFile = new File(cssFileUrl);

		logger.log(Level.INFO, cssFileUrl);

		BufferedReader cssReader = null;
		try
		{
			if (cssFile.createNewFile())
			{
				logger.log(Level.INFO, "file created");
			}
			cssReader = new BufferedReader(new FileReader(cssFile));
			String line;
			while ((line = cssReader.readLine()) != null)
			{
				rules.add(line);
			}
		}

		catch (IOException e)
		{
			logger.log(Level.SEVERE, "", e);
		}
		finally
		{
			try
			{
				cssReader.close();
			}
			catch (IOException e)
			{
				logger.log(Level.SEVERE, "", e);
			}
		}
	}

	public void save()
	{
		BufferedWriter cssWriter = null;
		try
		{
			cssWriter = new BufferedWriter(new FileWriter(cssFile));
			for (String rule : rules)
			{
				cssWriter.write(rule + "\n");
			}
		}
		catch (IOException e)
		{
			logger.log(Level.SEVERE, "", e);
		}
		finally
		{
			try
			{
				cssWriter.flush();
				cssWriter.close();
			}
			catch (IOException e)
			{
				logger.log(Level.SEVERE, "", e);

			}
		}
	}

	public void remove(String prefix)
	{
		List<String> remove = new ArrayList<String>();
		for (String rule : rules)
		{
			if (rule.startsWith(prefix))
			{
				remove.add(rule);
			}
		}
		rules.removeAll(remove);
	}
}
