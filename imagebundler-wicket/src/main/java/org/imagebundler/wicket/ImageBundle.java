package org.imagebundler.wicket;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Ananth
 */
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface ImageBundle {

}
