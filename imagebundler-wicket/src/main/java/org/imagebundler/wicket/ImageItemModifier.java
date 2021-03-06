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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;

public class ImageItemModifier extends AbstractBehavior
{

	private static final long serialVersionUID = 1L;

	private final ImageItem imageItem;
	private final AttributeModifier styleModifier;

	public ImageItemModifier(ImageItem imageItem)
	{
		super();
		if (imageItem == null)
		{
			throw new IllegalArgumentException("Argument [imageItem] cannot be null");
		}
		this.imageItem = imageItem;
		this.styleModifier = new AttributeModifier("style", true, new ImageStyleModel(imageItem));
	}

	public final ImageItem getImageItem()
	{
		return imageItem;
	}

	@Override
	public void onComponentTag(final Component component, final ComponentTag tag)
	{
		if (isEnabled(component))
		{
			tag.getAttributes().put("src", imageItem.getSrc());
			styleModifier.onComponentTag(component, tag);
		}
	}

	@Override
	public void detach(Component component)
	{
		styleModifier.detach(component);
	}

}