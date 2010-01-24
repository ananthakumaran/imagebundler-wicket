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

package org.imagebundler.wicket.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;

/**
 * mock executable element
 * 
 * @author Ananth
 * 
 */
public class MockElement implements ExecutableElement
{
	final String simpleName;
	private Set<Modifier> modifier = new HashSet<Modifier>();
	private List<VariableElement> parameters = new ArrayList<VariableElement>();
	private String returnType = "";

	public MockElement(String simpleName)
	{
		this.simpleName = simpleName;
	}

	public void setParameters(List<VariableElement> parameters)
	{
		this.parameters = parameters;
	}


	@Override
	public AnnotationValue getDefaultValue()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<? extends VariableElement> getParameters()
	{
		return parameters;
	}

	public void setReturnType(String returnType)
	{
		this.returnType = returnType;
	}

	@Override
	public TypeMirror getReturnType()
	{
		return new TypeMirror()
		{

			@Override
			public TypeKind getKind()
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <R, P> R accept(TypeVisitor<R, P> v, P p)
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString()
			{

				return returnType;
			}
		};
	}


	@Override
	public List<? extends TypeMirror> getThrownTypes()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<? extends TypeParameterElement> getTypeParameters()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isVarArgs()
	{
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public <R, P> R accept(ElementVisitor<R, P> v, P p)
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public TypeMirror asType()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <A extends Annotation> A getAnnotation(Class<A> annotationType)
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<? extends AnnotationMirror> getAnnotationMirrors()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<? extends Element> getEnclosedElements()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Element getEnclosingElement()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ElementKind getKind()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Set<Modifier> getModifiers()
	{
		return modifier;
	}

	public void setModifier(Set<Modifier> modifier)
	{
		this.modifier = modifier;
	}

	@Override
	public Name getSimpleName()
	{

		return new Name()
		{

			@Override
			public CharSequence subSequence(int start, int end)
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int length()
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public char charAt(int index)
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public boolean contentEquals(CharSequence cs)
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String toString()
			{

				return simpleName;
			}
		};
	}


}
