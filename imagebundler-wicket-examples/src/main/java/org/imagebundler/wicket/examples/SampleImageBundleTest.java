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

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.image.Image;

public  class SampleImageBundleTest // implements SampleImage
{
	public Image buttons(String id, String fileName)
	{
		Image image = new Image(id, "spacer.gif");		
		image.add(new SimpleAttributeModifier("style", "background-image: url(resources/org.imagebundler.wicket.examples.imagebundler.example.SampleImageBundle/SampleImageBundle.png); background-position:-0px -125px; width:260px; height:60px;"));
		return image;
	}

	public Image course(String id, String fileName)
	{
		Image image = new Image(id, "spacer.gif");
		image.add(new SimpleAttributeModifier("style", "background-image: url(resources/org.imagebundler.wicket.examples.imagebundler.example.SampleImageBundle/SampleImageBundle.png); background-position:-0px -0px; width:125px; height:125px;"));
		return image;
	}

}
