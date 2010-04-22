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

import java.util.Locale;

import org.apache.wicket.IClusterable;

/**
 * provides a way to access the style and source of a image
 * 
 * @author Ananth
 * 
 */
public interface ImageItem extends IClusterable
{
	/**
	 * gets the src of the image Note: this actually contains the src of the
	 * transparent gif. Default value <code>images/clear.gif</code>
	 * 
	 * @return src of the image (transparent image)
	 */
	String getSrc();

	/**
	 * gets the style of the image
	 * <p>
	 * eg
	 * <p>
	 * <code>
	 * 	background-image:url(resources/org.imagebundler.wicket.examples.EditorButtonBundle/EditorButtonBundle.png);
	 *  background-position:-148px -0px; width:16px; height:16px;  
	 * 	</code>
	 * 
	 * @return style of the image
	 */
	String getStyle(Locale locale);
}
