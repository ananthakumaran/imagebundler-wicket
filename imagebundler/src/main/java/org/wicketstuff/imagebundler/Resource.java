package org.wicketstuff.imagebundler;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Ananth
 */
@Target( { ElementType.METHOD })
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Resource {
	String value();
}
