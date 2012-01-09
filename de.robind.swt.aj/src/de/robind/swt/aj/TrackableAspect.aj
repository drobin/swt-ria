package de.robind.swt.aj;

import org.aspectj.lang.Signature;
import org.eclipse.swt.SWTObject;
import org.eclipse.swt.server.Trackable;

/**
 * Aspect tracks fields annotated with the {@link Trackable}-annotation.
 * <p>
 * The new value is transferred to the client.
 *
 * @author Robin Doer
 */
public aspect TrackableAspect {
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

    System.err.println(
        "Updated: " + obj.getClass().getName() + "." + sig.getName() +
        " with " + value);
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
}
