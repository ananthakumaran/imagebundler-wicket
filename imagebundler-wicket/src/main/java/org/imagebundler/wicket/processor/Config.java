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

package org.imagebundler.wicket.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import javax.tools.FileObject;
import javax.tools.StandardLocation;
import javax.tools.JavaFileManager.Location;

import org.imagebundler.wicket.util.FileLogger;

/**
 * ImageBundler configuration.
 * 
 * This class encapsulates all code that is responsible for making decisions
 * that can be affected by configuration options.
 * 
 */
public class Config
{
	private final FileLogger logger = CurrentEnv.getLogger();
	private final Map<String, String> options = new HashMap<String, String>();

	public Config()
	{
		loadDefaultOptions();
		loadAptKeyValueOptions();
		loadImageBundlerDotProperties();
	}

	private void loadDefaultOptions()
	{
		// TODO may change in the future
		this.options.put("image.clear", "images/clear.gif");
		this.options.put("image.output", "");
		this.options.put("webapp", "");
		this.options.put("basedir", "");
	}

	private void loadImageBundlerDotProperties()
	{
		// Eclipse, ant, and maven all act a little differently here, so try
		// both source and class output
		File imageBundlerProperites = null;
		for (Location location : new Location[] { StandardLocation.SOURCE_OUTPUT,
				StandardLocation.CLASS_OUTPUT })
		{
			imageBundlerProperites = this.resolveImageBundlerPropertiesIfExists(location);
			if (imageBundlerProperites != null)
			{
				break;
			}
		}
		if (imageBundlerProperites != null)
		{
			Properties p = new Properties();
			try
			{
				p.load(new FileInputStream(imageBundlerProperites));
			}
			catch (Exception e)
			{
				logger.log(Level.SEVERE, "", e);
			}
			for (Map.Entry<Object, Object> entry : p.entrySet())
			{
				this.options.put((String)entry.getKey(), (String)entry.getValue());
			}

			// make an assumption that the properties file is in the base dir
			// and set some defaults if it is not already set
			setValueIfEmpty("basedir", imageBundlerProperites.getParentFile().getAbsolutePath());
			setValueIfEmpty("webapp", "src/main/webapp");

		}
	}

	private void loadAptKeyValueOptions()
	{
		for (Map.Entry<String, String> entry : CurrentEnv.get().getOptions().entrySet())
		{
			this.options.put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Finds a {@code imagebundler.properties} file.
	 * 
	 * This uses a heuristic because in Eclipse we will not know what our
	 * working directory is (it is wherever Eclipse was started from), so
	 * project/workspace-relative paths will not work.
	 * 
	 * As far as passing in a imagebundler.properties path as a {@code
	 * -Afile=path} setting, Eclipse also lacks any {@code $ basepath} -type
	 * interpolation in its APT key/value pairs (like Ant would be able to do).
	 * So only fixed values are accepted, meaning an absolute path, which would
	 * be too tied to any one developer's particular machine.
	 * 
	 * The one thing the APT API gives us is the CLASS_OUTPUT (e.g. bin/apt). So
	 * we start there and walk up parent directories looking for {@code
	 * imagebundler.properties} files.
	 */
	private File resolveImageBundlerPropertiesIfExists(Location location)
	{
		// Find a dummy /bin/apt/dummy.txt path to start at
		final String dummyPath;
		try
		{
			// We don't actually create this, we just want its URI
			FileObject dummyFileObject = CurrentEnv.getFiler().getResource(location, "",
					"dummy.txt");
			dummyPath = dummyFileObject.toUri().toString().replaceAll("file:", "");
		}
		catch (IOException e1)
		{
			return null;
		}

		// Walk up looking for a imagebundler.properties
		File current = new File(dummyPath).getParentFile();
		while (current != null)
		{
			File possible = new File(current, "imagebundler.properties");
			if (possible.exists())
			{
				return possible;
			}
			current = current.getParentFile();
		}

		// Before giving up, try just grabbing it from the current directory
		File possible = new File("imagebundler.properties");
		if (possible.exists())
		{
			return possible;
		}

		// No imagebundler.properties found
		return null;
	}

	public Map<String, String> getOptions()
	{
		return options;
	}

	private void setValueIfEmpty(String key, String value)
	{
		String originalValue = options.get(key);
		if (originalValue != null && originalValue.equals(""))
		{
			options.put(key, value);
		}
	}
}
