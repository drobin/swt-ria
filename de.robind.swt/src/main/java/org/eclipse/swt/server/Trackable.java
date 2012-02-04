package org.eclipse.swt.server;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import de.robind.swt.base.SWTObject;

/**
 * The {@link Field}, annotated with this type is tracked by AspectJ.
 * <p>
 * If the field is updated, the new value is transferred to the client.
 * <p>
 * <b>NOTE</b>: The encapsulated class needs to a a subclass of
 * {@link SWTObject}! If not, a {@link RuntimeException} is thrown.
 *
 * @author Robin Doer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Trackable {

}
