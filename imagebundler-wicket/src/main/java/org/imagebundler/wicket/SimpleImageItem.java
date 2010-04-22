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

package org.imagebundler.wicket;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class SimpleImageItem implements ImageItem
{

	private static final long serialVersionUID = 1L;

	private final String imageSrc;
	private final String defaultImageStyle;
	private Map<String, String> localizedImageStyles;

	public SimpleImageItem(String imageSrc, String defaultImageStyle)
	{
		this.imageSrc = imageSrc;
		this.defaultImageStyle = defaultImageStyle;
	}

	public void addLocalizedStyle(String localeStr, String imageStyle)
	{
		if (localizedImageStyles == null)
		{
			localizedImageStyles = new HashMap<String, String>();
		}
		localizedImageStyles.put(localeStr, imageStyle);
	}

	@Override
	public String getSrc()
	{
		return imageSrc;
	}

	@Override
	public String getStyle(Locale locale)
	{
		if ((locale == null) || (localizedImageStyles == null))
		{
			return defaultImageStyle;
		}
		String imageStyle = localizedImageStyles.get(locale.toString());
		// fallback to language style, eg.: when locale is "en_GB" but you
		// registered images only for language "en", without country specified
		if (imageStyle == null)
		{
			imageStyle = localizedImageStyles.get(locale.getLanguage());
			return imageStyle != null ? imageStyle : defaultImageStyle;
		}
		return imageStyle;
	}

}