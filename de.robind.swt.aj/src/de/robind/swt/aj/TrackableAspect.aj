package de.robind.swt.aj;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.Signature;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.server.Trackable;

import de.robind.swt.base.SWTObject;

/**
 * Aspect tracks fields annotated with the {@link Trackable}-annotation.
 * <p>
 * The new value is transferred to the client.
 *
 * @author Robin Doer
 */
public aspect TrackableAspect {
  /**
   * The logger of the aspect
   */
  private static final Logger logger = Logger.getLogger(TrackableAspect.class);

  /**
   * The pointcut tracks fields annotated with the
   * {@link Trackable}-annotation.
   *
   * @param obj The object, where the field is located
   * @param value The new value
   */
  pointcut setTrackable(Object obj, Object value):
    (set(@Trackable * *)) && target(obj) && args(value);

  /**
   * Hook into after {@link Trackable}-annotated fields are updated.
   */
  after(Object target, Object value): setTrackable(target, value) {
    SWTObject obj = checkTarget(target);
    Signature sig = thisJoinPoint.getSignature();

    logger.debug("Attribute change tracked: " +
        obj.getClass().getName() + "." + sig.getName());

    try {
      callChangedMethod(obj, sig.getName());
    } catch (InvocationTargetException e) {
      // TODO Here we need a better error-code
      SWTException exc = new SWTException(SWT.ERROR_UNSPECIFIED);
      exc.throwable = e.getCause();
      throw exc;
    } catch (Exception e) {
      // TODO Here we need a better error-code
      SWTException exc = new SWTException(SWT.ERROR_UNSPECIFIED);
      exc.throwable = e;
      throw exc;
    }
  }

  /**
   * Checks the target-object, where the field was updated.
   * <p>
   * It must be a cubclass of {@link SWTObject}.
   *
   * @param target Target object to chek
   * @return The target {@link SWTObject}
   * @throws RuntimeException if <code>target</code> is not a subclass of
   *                          {@link SWTObject}
   */
  private static SWTObject checkTarget(Object target) throws RuntimeException {
    if (target instanceof SWTObject) {
      return ((SWTObject)target);
    }
    else {
      throw new RuntimeException(
          target.getClass().getName() + " must be a subclass of " +
          SWTObject.class.getName());
    }
  }

  /**
   * Invokes a method <code>attributeChanged</code> on the destination-object.
   *
   * @param obj The object, which is invoked
   * @param field The name of the field, which has changed
   * @throws NoSuchMethodException if no such method exists
   * @throws InvocationTargetException if the method has thrown an exception
   * @throws IllegalAccessException if the method is not accessible
   */
  private static void callChangedMethod(SWTObject obj, String field)
      throws NoSuchMethodException, InvocationTargetException,
             IllegalAccessException {

    Method m = obj.getClass().getMethod("attributeChanged", String.class);
    m.invoke(obj, field);
  }
}
