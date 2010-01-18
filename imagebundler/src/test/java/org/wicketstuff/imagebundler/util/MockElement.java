package org.wicketstuff.imagebundler.util;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;

public class MockElement implements Element
{
	final String simpleName;

	public MockElement(String simpleName)
	{
		this.simpleName = simpleName;
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
		return ElementKind.METHOD;
	}

	@Override
	public Set<Modifier> getModifiers()
	{
		// TODO Auto-generated method stub
		return null;
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
